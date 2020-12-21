package mvcframework.v1servlet;

import mvcframework.annotation.ShuaiRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Handler记录Controller中的RequestMapping和Method的对应关系
 * */
public class HandlerMethod {
    /**
     * 保存方法对应的实例bean
     * */
    protected Object controller;
    /**
     * 保存映射的方法
     * */
    protected Method method;
    /**
     * 映射地址模板
     * */
    protected Pattern pattern;

    /**
     * 映射方法的形参顺序集合
     * key为形参的名字（不可能重复），值为形参的对象（包含了类型和参数位置索引）
     * */
    protected Map<String,Parameter> paramIndexMapping;

    public HandlerMethod(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        this.paramIndexMapping = new HashMap<String,Parameter>();
        //获取所有形参的顺序
        putParamIndexMapping(method);
    }

    private void putParamIndexMapping(Method method){


        //1  形参类型列表
        Class<?>[] types=method.getParameterTypes();
        //2  形参注解列表
        //提取方法中每个参数的注解，如果参数没有注解则依然会有一个数组占位符，值为null，假设有3个参数，前两个没有注解，后面1个有注解，则二维数组的数据时3条，
        // 只有最后一个有注解的有值；意味着getParameterAnnotations和getParameterTypes方法数组元素个数是相同且的；参数位置也是一一对应的；
        //一个方法上可以有多个参数，每个参数可能有多个注解；所以注解是一个二维数组
        Annotation[][] params= method.getParameterAnnotations();
        //遍历二维数组,读取参数顺序
        for (int i=0;i<params.length;i++) {
            for (Annotation a:params[i]) {
                if(a instanceof ShuaiRequestParam){
                    String paraName=((ShuaiRequestParam)a).value();
                    if(paraName.isEmpty()){
                        //当注解值为null时，把参数名当key,实现字段名称自动匹配的效果

                       /** jdk1.8之前需要解析字节码的方式获取参数名（可以认为java不支持获取参数名），1.8之后可以通过反射获取参数名了
                         jdk1.8 通过反射获取方法的参数名,需要开启设置：
                         在.File->Settings->Build,Execution,Deployment->Compiler->Java Compiler下
                        Additional command line parameters: 后面填上 -parameters
                        否则获取到的是arg0,arg1...**/

                        java.lang.reflect.Parameter par=method.getParameters()[i];//获取对应索引位置的参数
                        //java.lang.reflect.Parameter对象是新的反射对象，param.isNamePresent()表示是否编译了参数名
                        //在jdk1.8为开启设置的情况下，此方法返回false，开启后返回true
                        if(par.isNamePresent()) {
                            paraName = par.getName();
                        }
                    }
                    //根据位置索引在 形参类型列表 中找到其类型
                    paramIndexMapping.put(paraName,new Parameter(types[i],i));

                }
            }
        }

        //2 提取方法中的request和response参数类型
        Class<?> [] paramsTypes = method.getParameterTypes();
        for (int i=0;i<paramsTypes.length;i++) {
            Class<?> patype = paramsTypes[i];
            if(patype==HttpServletRequest.class ||
                    patype == HttpServletResponse.class){
                //根据位置索引在 形参类型列表 中找到其类型
                paramIndexMapping.put(patype.getName(),new Parameter(types[i],i));
            }

        }


    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    class Parameter{
        /**
         * 参数类型
         * */
        private  Class<?> type;
        /**
        * 参数位置
        * */
        private  Integer index;


        public Parameter(Class<?> type, Integer index) {
            this.type = type;
            this.index = index;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }
    }

}
