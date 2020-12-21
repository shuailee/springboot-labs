package mvcframework.v1servlet;

import mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 实现spring框架
 * 请求地址映射 改进版
 * */
public class DispatcherServletv2 extends HttpServlet {
    //存储aplication.properties的配置内容
    private Properties configContext = new Properties();

    //存储所有扫描到的类
    private List<String> classNames = new ArrayList<String>();
    /**
     *   IOC容器，保存所有实例化对象
     *   /注册式单例模式
     *   类名->对应实例
     * */
    private Map<String,Object> iocmap = new HashMap<String, Object>();

    //请求地址映射容器
    private List<HandlerMethod> handlerMethodMapping =new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 处理请求，查找url映射, 将请求转发到对应的方法上
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            //servlet初始化时扫描配置的bean包，将包内的对象加载到ioc容器中
            //1  加载配置文件，获取需要扫描的包名
            String scanPackageName = getPackagenNme(config);
            //2  执行bean扫描，收集需要初始化的类
            doScanner(scanPackageName);
            //3  初始化bean到ioc容器的包 IOC
            initIoc();
            //4 依赖注入DI
            doAutowired();
            //5 加载请求映射 将一个url对应一个method执行方法 MVC
            dohandlerMapping();

            System.out.println("shuai Spring framework is init over");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     *  处理请求
     *  1 根据请求url匹配处理方法的handler
     *  2 获取处理方法的形参顺序列表并初始化，name为参数名 value顺序值
     *  3 获取处理方法的所有形参类型列表，根据其长度定义处理方法调用参数中的parames数组长度
     *  4 获取请求中的实参列表，遍历实参列表，在形参顺序列表中查找位置索引，再根据找到的位置在形参类型列表中查找对应类型
     *  5 执行类型转换，构造请求参数，发起调用
     * */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        HandlerMethod handlerMethod = getHandler(req);
        if(handlerMethod == null){
            //如果没有匹配上，返回404错误
            resp.getWriter().write("404 Not Found");
            return;
        }


        //1 获取处理方法所有形参类型列表 Interger,String,Boolean
        Class<?>[] paramTypes= handlerMethod.getMethod().getParameterTypes();

        //2 构造请求参数值，mvc接收出可能是指定类型的，所以参数值放进来时要转型
        Object [] paramValues = new Object[paramTypes.length];

        //获取请求的中实参列表
        Map<String,String[]> params = req.getParameterMap();
        //遍历实参列表进行解析
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            //实参的值
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
            //如果在形参顺序列表中匹配到了当前实参的名字，则获取对应的形参位置和类型信息，并将实参的值转换成该类型，
            // 最终存储到 构造请求参数值 的对象中用于后面发起请求
            if(!handlerMethod.paramIndexMapping.containsKey(param.getKey())){continue;}
            HandlerMethod.Parameter parameter= handlerMethod.paramIndexMapping.get(param.getKey());
            //构造请求参数值:进行类型转换转成调用方法的形参需要的类型
            paramValues[parameter.getIndex()] = convert(parameter.getType(),value);
        }
        //设置方法中的request和response对象
        int reqIndex = handlerMethod.paramIndexMapping.get(HttpServletRequest.class.getName()).getIndex();
        paramValues[reqIndex] = req;
        int respIndex = handlerMethod.paramIndexMapping.get(HttpServletResponse.class.getName()).getIndex();
        paramValues[respIndex] = resp;

        //反射调用对应方法
        handlerMethod.method.invoke(handlerMethod.controller, paramValues);
    }

    //url传过来的参数都是String类型的，HTTP是基于字符串协议
    //只需要把String转换为任意类型就好
    private Object convert(Class<?> type,String value){
        if(Integer.class == type){
            return Integer.valueOf(value);
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }


    /**
     * 根据请求地址获取处理映射的handler
     * */
    private HandlerMethod getHandler(HttpServletRequest req) throws Exception{
        if(handlerMethodMapping.isEmpty()){ return null; }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");

        for (HandlerMethod handlerMethod : handlerMethodMapping) {
            try{
                Matcher matcher = handlerMethod.pattern.matcher(url);
                //如果没有匹配上继续下一个匹配
                if(!matcher.matches()){ continue; }

                return handlerMethod;
            }catch(Exception e){
                throw e;
            }
        }
        return null;
    }

    /**
     * 请求地址方法映射初始化
     * */
    private void dohandlerMapping() {
        /**
         * 遍历ioc容器中的对象，找出controller对象
         * 先获取controller对象上的requestmapping地址，在获取类中方法上的requestmapping地址,拼接
         * */
        if(iocmap.isEmpty()){return;}
        for(Map.Entry<String,Object> entry: iocmap.entrySet()){
            Class<?> clazz= entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(ShuaiController.class)){
                continue;
            }
            //1 获取controller上的requestmapping配置
            String baseurl="";
            if(clazz.isAnnotationPresent(ShuaiRequestMapping.class)){
                baseurl = clazz.getAnnotation(ShuaiRequestMapping.class).value();
            }
            //2 获取方法上的map配置
            Method [] methods= clazz.getMethods();
            for (Method m:methods) {
                //如果方法上没有mapping注解
                if(!m.isAnnotationPresent(ShuaiRequestMapping.class)){
                    continue;
                }
                //获取注解内容
                ShuaiRequestMapping mapping=m.getAnnotation(ShuaiRequestMapping.class);
                String uri=("/"+baseurl+"/"+mapping.value())
                        .replaceAll("/+","/");//去除重复的/
                Pattern pattern = Pattern.compile(uri);
                handlerMethodMapping.add(new HandlerMethod(entry.getValue(),m,pattern));
                System.out.println("mapping: " + pattern + "," + m);
            }
        }

    }

    /**
     * 执行依赖注入
     * */
    private void doAutowired()  {
        //遍历ioc容器中的对象，通过反射获取对象的所有字段
        //遍历字段，为包含autowired注解的字段赋值。执行注入动作
        // 如果字段包含autowired注解，则读取该注解的value，如果value为空则默认使用字段类型名作为字段名，否则使用value

        if(iocmap.isEmpty()){
            return;
        }
        for (Map.Entry<String,Object> entry:iocmap.entrySet()) {
            Field [] fields= entry.getValue().getClass().getDeclaredFields();
            for (Field field:fields) {
                if(!field.isAnnotationPresent(ShuaiAutowired.class)){
                    continue;
                }

                String beanname=field.getType().getSimpleName();
                //获取field上指定注解
                ShuaiAutowired shuaiAutowired=field.getAnnotation(ShuaiAutowired.class);
                if(!shuaiAutowired.value().isEmpty()){
                    beanname=shuaiAutowired.value();
                }

                //设置私有属性的访问权限
                field.setAccessible(true);
                //执行注入
                try {
                    //为对象的字段设置值 第一个参数为字段所属当前对象本身 第二个为字段值
                    field.set(entry.getValue(),iocmap.get(toLowerFirstCase(beanname)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }


            }

        }

    }

    /**
     * 初始化所有相关的类的实例，并且放入到IOC容器之中
     * 控制反转的过程  工厂模式
     * */
    private void initIoc() throws Exception {
        for (String className : classNames) {
            if (!className.contains(".")) {
                continue;
            }
            //将类的.class文件加载到jvm中，还会对类进行解释，执行类中的static块
            Class<?> clazz = Class.forName(className);

            if (clazz.isAnnotationPresent(ShuaiController.class)) {
                iocmap.put(toLowerFirstCase(clazz.getSimpleName()), clazz.newInstance());
            }
            else if(clazz.isAnnotationPresent(ShuaiService.class)){
                //默认命名
                String beanName=toLowerFirstCase(clazz.getSimpleName()) ;
                //自定义命名
                ShuaiService shuaiService= clazz.getAnnotation(ShuaiService.class);
                if(!"".equals(shuaiService.value())){
                    beanName=shuaiService.value();
                }
                iocmap.put(beanName,clazz.newInstance());

                /*//另外一种方式：
                //根据类型注入实现类（一般service会实现一个接口,获取接口所有实现类）
                for (Class<?> i:clazz.getInterfaces()) {
                    if(iocmap.containsKey(i.getName())){
                        throw new Exception("The beanName is exists!!");
                    }
                    iocmap.put(i.getName(),clazz.newInstance());
                }*/
            }
        }
    }

    /**
     * 获取要扫描的包名
     * */
    private String getPackagenNme(ServletConfig config){
        //获取配置文件中配置的包名
        String scanPagerName=config.getInitParameter("contextConfigLocation");
        //getClassLoader会将类加载到jvm但不会初始化static
        InputStream packageStream= this.getClass().getClassLoader().getResourceAsStream(scanPagerName.split(":")[1]);
        try {
            configContext.load(packageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scanPackageName= configContext.getProperty("scanPackage");
        return scanPackageName;
    }

    /**
     * 在项目启动时 执行bean扫描
     * */
    private void doScanner(String scanPackage) {
        //包的路径
        URL url=this.getClass().getClassLoader().getResource("/"+
                //将包名中的.替换成/
                scanPackage.replaceAll("\\.","/"));
        File classdir=new File(url.getFile());
        for (File file: classdir.listFiles()) {
            if(file.isDirectory()){
                //如果是文件夹在递归继续遍历
                doScanner(scanPackage+"."+file.getName());
            }else {
                if(!file.getName().endsWith(".class")){
                    continue;
                }
                //类的完全限定名 =包名+类名
                String clazzname=(scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(clazzname);
            }
        }
    }

    /**
     * 首字母小写
     * */
    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return  String.valueOf(chars);
    }
}
