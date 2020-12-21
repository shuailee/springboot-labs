package com.shuailee.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @program: springboot-study
 * @description: RequestWrapper
 * @author: shuai.li
 * @create: 2020-06-28 13:56
 **/
public class RequestWrapper extends HttpServletRequestWrapper {
    /**
     * body字节变量用来保存读取的数据，以便于多次读取
     */
    private final String body;
    private static final int BUFFER_SIZE = 128;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        //从HttpServletRequest的流中解析数据，如果没有则继续从parameterMap中获取
        //如果是post得http请求且调用过getParameter方法，且请求类型是application/x-www-form-urlencoded则getInputStream获取不到信息，参数会被填充进ParameterMap中
        String requestBody = inputStream2String(request);
        //从ParameterMap获取参数，并保存body以便多次获取
        if (StringUtils.isEmpty(requestBody) && RequestMethod.POST.name().equals(request.getMethod())) {
            if (!CollectionUtils.isEmpty(request.getParameterMap())) {
                requestBody = JSONObject.toJSONString(request.getParameterMap());
            }
        }
        this.body = requestBody;
    }

    private String inputStream2String(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                // 每次循环从流中读取指定长度得数据直到读完为止
                char[] charBuffer = new char[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body.getBytes());

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 返回请求得参数
     */
    public String getBody() {
        return this.body;
    }
}
