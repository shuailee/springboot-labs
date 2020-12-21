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
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * 实现spring框架简易demo
 * */
public class DispatcherServletv1 extends HttpServlet {
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

    //保存Contrller中所有Mapping的对应关系,请求地址->方法
    private Map<String,Method> handlerMapping=new HashMap<>();


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

            System.out.println("GP Spring framework is init.");
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
     * 处理请求，查找url映射, 将请求转发到对应的方法上
     * 1 获取请求地址
     * 2 根据请求地址去url映射容器中查找对应的执行方法，找不到返回404
     * 3 获取方法参数
     * 3 发起调用
     * */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        String url=req.getRequestURI();
        String contentpath=req.getContextPath();
        //替换相对路径
        url = url.replaceAll(contentpath,"").replaceAll("/+","/");
        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!");
            return;
        }

        Method method=handlerMapping.get(url);
        //保存请求的url参数列表
        Map<String,String[]> params = req.getParameterMap();

        //通过反射拿到method所在class，拿到class之后还是拿到class的名称
        //再调用toLowerFirstCase获得beanName
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        //反射调用方法：第一个参数是对象实例  第二个为参数列表
        method.invoke(iocmap.get(beanName),new Object[]{req,resp,params.get("name")[0]});

       // dymicinvoke(req,resp,method);
    }

    private void dymicinvoke(HttpServletRequest req, HttpServletResponse resp,Method method) throws InvocationTargetException, IllegalAccessException {

        //保存请求的url参数列表
        Map<String,String[]> params = req.getParameterMap();
        //通过反射拿到method所在class，拿到class之后还是拿到class的名称
        //再调用toLowerFirstCase获得beanName
        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        /**
         * 动态获取参数
         * */
        //获取方法的形参列表
        Class<?> [] parameterTypes = method.getParameterTypes();
        //保存赋值参数的位置
        Object [] paramValues = new Object[parameterTypes.length];
        //按根据参数位置动态赋值
        for (int i = 0; i < parameterTypes.length; i ++) {
            Class parameterType = parameterTypes[i];
            //形参中是servlet的请求响应
            if(parameterType == HttpServletRequest.class){
                paramValues[i] = req;
                continue;
            }else if(parameterType == HttpServletResponse.class){
                paramValues[i] = resp;
                continue;
            }
            //形参中是String
            else if(parameterType == String.class){
                //提取方法中加了注解的参数
                Annotation[] [] pa = method.getParameterAnnotations();
                for (int j = 0; j < pa.length ; j ++) {
                    for(Annotation a : pa[i]){

                        if(a instanceof ShuaiRequestParam){
                            String paramName = ((ShuaiRequestParam) a).value();
                            if(!"".equals(paramName.trim())){
                                String value = Arrays.toString(params.get(paramName))
                                        .replaceAll("\\[|\\]","")
                                        .replaceAll("\\s",",");
                                paramValues[i] = value;
                            }
                        }
                    }

                }
            }
        }
        method.invoke(iocmap.get(beanName),paramValues);
    }

    /**
     * 请求地址映射初始化
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
                handlerMapping.put(uri,m);
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
