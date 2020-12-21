package com.shuailee.cathelper;

import com.dianping.cat.Cat;
import com.dianping.cat.Cat.Context;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Transaction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CatRestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {


		/**
		 https://github.com/dianping/cat/blob/master/lib/java/README.zh-CN.md
		 *
		 在使用 Transaction API 时，你可能需要注意以下几点：
		 你可以调用 addData 多次，添加的数据会被 & 连接起来。
		 同时指定 duration 和 durationStart 是没有意义的，
		 不要忘记完成 transaction！否则你会得到一个毁坏的消息树以及内存泄漏！

		 * */

		Transaction t = Cat.newTransaction(CatConstants.TYPE_CALL, request.getURI().toString());

		try {
			HttpHeaders headers = request.getHeaders();

			// 保存和传递CAT调用链上下文
			Context ctx = new CatContext();
			Cat.logRemoteCallClient(ctx);
			headers.add(CatHttpConstants.CAT_HTTP_HEADER_ROOT_MESSAGE_ID, ctx.getProperty(Cat.Context.ROOT));
			headers.add(CatHttpConstants.CAT_HTTP_HEADER_PARENT_MESSAGE_ID, ctx.getProperty(Cat.Context.PARENT));
			headers.add(CatHttpConstants.CAT_HTTP_HEADER_CHILD_MESSAGE_ID, ctx.getProperty(Cat.Context.CHILD));

			// 保证请求继续被执行
			ClientHttpResponse response =  execution.execute(request, body);
			t.setStatus(Transaction.SUCCESS);
			return response;
		} catch (Exception e) {
			/**
			 Error 是一种特殊的事件，它的 type 取决于传入的 Throwable e.
				 如果 e 是一个 Error, type 会被设置为 Error。
				 如果 e 是一个 RuntimeException, type 会被设置为 RuntimeException。
				 其他情况下，type 会被设置为 Exception。
			 同时错误堆栈信息会被收集并写入 data 属性中
			 * */

			Cat.getProducer().logError(e);
			t.setStatus(e);
			throw e;
		} finally {
			t.complete();
		}
	}
}