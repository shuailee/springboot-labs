package com.shuailee.cathelper;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatServletServerFilter implements Filter {
	
    private String[] urlPatterns = new String[0];

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String patterns = filterConfig.getInitParameter("CatHttpModuleUrlPatterns");
        if (patterns != null) {
            patterns = patterns.trim();
            urlPatterns = patterns.split(",");
            for (int i = 0; i < urlPatterns.length; i++) {
                urlPatterns[i] = urlPatterns[i].trim();
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURL().toString();
        for (String urlPattern : urlPatterns) {
            if (url.startsWith(urlPattern)) {
                url = urlPattern;
            }
        }

        CatContext catContext = new CatContext();
        catContext.addProperty(Cat.Context.ROOT, request.getHeader(CatHttpConstants.CAT_HTTP_HEADER_ROOT_MESSAGE_ID));
        catContext.addProperty(Cat.Context.PARENT, request.getHeader(CatHttpConstants.CAT_HTTP_HEADER_PARENT_MESSAGE_ID));
        catContext.addProperty(Cat.Context.CHILD, request.getHeader(CatHttpConstants.CAT_HTTP_HEADER_CHILD_MESSAGE_ID));
        Cat.logRemoteCallServer(catContext);
        
        Transaction t = Cat.newTransaction(CatConstants.TYPE_SERVICE, url);

        try {

            Cat.logEvent("Service.method", request.getMethod(), Message.SUCCESS, request.getRequestURL().toString());
            Cat.logEvent("Service.client", request.getRemoteHost());
            // filterChain.doFilter(servletRequest, servletResponse);

            // 从流中获取请求和响应信息，因为流中数据只能被读取一次所以需要引入包装类，数据读取完毕做完业务处理再重新赋值给流
            ResponseWrapper responseWrapper = new ResponseWrapper(response);
            RequestWrapper requestWrapper = new RequestWrapper(request);
            filterChain.doFilter(requestWrapper, responseWrapper);
            // 记录请求和响应报文
            Cat.logEvent(CatConstants.TYPE_REQUEST, getRequestBody(request, requestWrapper));
            Cat.logEvent(CatConstants.TYPE_RESPONSE, getResponseBody(response, responseWrapper));



            t.setStatus(Transaction.SUCCESS);
        } catch (Exception ex) {
            t.setStatus(ex);
            Cat.logError(ex);
            throw ex;
        } finally {
            t.complete();
        }
    }

    @Override
    public void destroy() {

    }

    private String getRequestBody(HttpServletRequest request, RequestWrapper requestWrapper) {
        try {
            if (request.getMethod().equalsIgnoreCase("GET")) {
                return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8")
                        .replaceAll("%22", "\"");
            } else {
                return requestWrapper.getBody();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getResponseBody(HttpServletResponse response, ResponseWrapper responseWrapper) throws IOException {
        byte[] content = responseWrapper.getContent();
        String responseBody = new String(content, "UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(content);
        out.flush();
        return responseBody;
    }
}
