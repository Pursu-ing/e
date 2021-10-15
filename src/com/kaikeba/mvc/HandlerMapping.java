package com.kaikeba.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 映射池（包含大量的网址与方法的对应关系）
 */
public class HandlerMapping {
    // 映射池中包含了大量的映射对象，就存在这个map集合中
    // 用户每次发起请求的时候，就去这个集合中寻找使用哪个方法处理
    private static Map<String,MVCMapping> data = new HashMap<>();

    // 从map集合中通过uri这个键获取MVCMapping这个值
    public static MVCMapping get(String uri){
        return data.get(uri);
    }

    // 目的是为了加载一个输入流进来获取配置文件中的键值对,此处为获取类
    public static void load(InputStream is){
        Properties ppt = new Properties();
        try {
            ppt.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 取出所有的值
        Collection<Object> values = ppt.values();
        // 对所有的值(类)进行遍历
        for (Object cla:values) {
            String className = (String)cla;
            // 接下来要通过反射的方式创建对象，再通过对象去获取里面的方法
            try {
                Class c = Class.forName(className);// 通过类名称获取一个类
                Object o = c.getConstructor().newInstance();// 通过无参的构造方法创建对象
                Method[] methods = c.getMethods();// 获取这个类的所有的共有方法
                for (Method m:methods) {
                    // 获取方法所有的注解
                    Annotation[] as = m.getAnnotations();
                    if(as != null){
                        // 判断方法的注解是什么
                        for (Annotation annotation:as) {
                            if(annotation instanceof ResponseBody){
                                // 说明此方法用于返回字符串给客户端
                                // 把这个方法存入集合中，前者是方法注解的值，后者是MVCMapping对象
                                //                               对象 方法 类型
                                MVCMapping mapping = new MVCMapping(o,m,ResponseType.TEXT);
                                // 此处是把注解.val当成键，即为把网址当成了键
                                Object obj = data.put(((ResponseBody)annotation).value(),mapping);
                                // 需要做的额外的判定，如果请求地址有重复怎么办？
                                // map集合中如果返回了一个非空的对象，说明有键重复，返回了被覆盖的内容
                                if(obj != null){
                                    // 存在了重复的请求地址
                                    throw new RuntimeException("请求地址重复："+((ResponseBody)annotation).value());
                                }
                            }else if(annotation instanceof ResponseView){
                                // 说明此方法用于返回界面给客户端
                                MVCMapping mapping = new MVCMapping(o,m,ResponseType.VIEW);

                                Object obj = data.put(((ResponseView)annotation).value(),mapping);
                                if(obj != null){
                                    // 存在了重复的请求地址
                                    throw new RuntimeException("请求地址重复："+((ResponseView)annotation).value());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 映射对象，每一个对象都封装了一个方法，用于处理请求
     */
    public static class MVCMapping{
        // 对象
        private Object obj;
        // 方法
        private Method method;
        // 枚举
        private ResponseType type;

        public MVCMapping(Object obj, Method method, ResponseType type) {
            this.obj = obj;
            this.method = method;
            this.type = type;
        }

        public MVCMapping() {
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public ResponseType getType() {
            return type;
        }

        public void setType(ResponseType type) {
            this.type = type;
        }
    }
}
