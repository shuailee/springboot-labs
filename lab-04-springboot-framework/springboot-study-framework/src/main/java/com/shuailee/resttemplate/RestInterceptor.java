package com.shuailee.resttemplate;

import com.shuailee.model.result.RestConst;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		request.getHeaders().set(RestConst.REQUEST_ID, MDC.get(RestConst.REQUEST_ID));
		System.out.println("HttpClient 调用 拦截了...");
		// 保证请求继续被执行
		ClientHttpResponse response =  execution.execute(request, body);
		return  response;
	}
}