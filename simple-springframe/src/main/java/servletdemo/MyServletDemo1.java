package servletdemo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class MyServletDemo1 extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        System.out.println("run init");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型
        resp.setContentType("text/html");
        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        out.println("<h3>ServerName:" + req.getServerName() + "</h3>");
        out.println("<h3>QueryString:" + req.getQueryString() + "</h3>");
        out.println("<h3>Parameter-user:" + req.getParameter("user") + "</h3>");
        out.println("<h3>contentpath:" + req.getContextPath() + "</h3>");
        out.println("<h3>RequestURI:" + req.getRequestURI() + "</h3>");
        //第一个参数：方法所在的实例
        //第二个参数：调用时所需要的实参
        Map<String,String[]> params = req.getParameterMap();
        //保存请求的url参数列表
        Map<String,String[]> parameterMap = req.getParameterMap();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


}
