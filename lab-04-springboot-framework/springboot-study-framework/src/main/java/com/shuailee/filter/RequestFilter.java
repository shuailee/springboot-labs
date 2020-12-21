package com.shuailee.filter;


import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-study
 * @description: RequestFilter
 * @author: shuai.li
 * @create: 2020-05-18 17:17
 **/
@Slf4j
public class RequestFilter implements javax.servlet.Filter {

    public static final String ISO_8859_1 = "iso-8859-1";
    public static final String UTF_8 = "utf-8";
    private static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data;";
    private Set<String> excludesPattern;
    protected AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // TODO 测试
        System.out.println("servlet filter 拦截了....");

        Stopwatch sp = Stopwatch.createStarted();

        if (!(request instanceof HttpServletRequest && response instanceof HttpServletResponse)) {
            throw new ServletException("HttpFilter can't handle an non-http request");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        if (!StringUtils.isEmpty(httpRequest.getQueryString())) {
            path += "?" + httpRequest.getQueryString();
        }
        try {
            if (isExclusion(httpRequest) || isFileUpload(httpRequest)) {
                chain.doFilter(httpRequest, httpResponse);
            } else {
                // 拦截后要执行的自定义操作
                RequestWrapper requestWrapper = new RequestWrapper(httpRequest);
                ResponseWrapper responseWrapper = new ResponseWrapper(httpResponse);

                chain.doFilter(requestWrapper, httpResponse);
                // TODO 测试
                System.out.println(getRequestBody(httpRequest,requestWrapper));
                System.out.println(getResponseBody(httpResponse,responseWrapper));
            }
        } finally {
            log.info("access url [{}], cost time [{}] ms )", path, sp.elapsed(TimeUnit.MILLISECONDS));
            MDC.clear();
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String exclusions = config.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (!StringUtils.isEmpty(exclusions)) {
            excludesPattern = Sets.newHashSet(Arrays.asList(exclusions.split("\\s*,\\s*")));
        }
    }

    @Override
    public void destroy() {
        //destory method
    }

    /**
     * 正则过滤不拦截的请求
     */
    private boolean isExclusion(HttpServletRequest request) {

        if (CollectionUtils.isEmpty(excludesPattern)) {
            return false;
        } else {
            String requestURI = request.getRequestURI();
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }

            Iterator iterator = this.excludesPattern.iterator();
            String pattern;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }

                pattern = (String) iterator.next();
            } while (!this.pathMatcher.match(pattern, requestURI));

            return true;

        }
    }

    /**
     * 过滤文件上传MIME媒体类型
     */
    private boolean isFileUpload(HttpServletRequest request) {
        if(request.getContentType()!=null) {
            return request.getContentType().indexOf(MULTIPART_FORM_DATA) > -1;
        }
        return false;
    }


    private String getRequestBody(HttpServletRequest request, RequestWrapper requestWrapper) {
        try {
            if (RequestMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
                if(StringUtils.isEmpty(request.getQueryString())){
                    return "";
                }
                return new String(request.getQueryString().getBytes(ISO_8859_1), UTF_8)
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
