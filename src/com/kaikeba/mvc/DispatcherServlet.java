package com.kaikeba.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class DispatcherServlet extends javax.servlet.http.HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化获取web.xml中的参数信息（根据键获取值），此处是得到配置文件的名称
        // 目的是为了加载一个输入流进来获取配置文件中的键值对
        String path = config.getInitParameter("contentConfigLocation");
        InputStream is = DispatcherServlet.class.getClassLoader().getResourceAsStream(path);
        HandlerMapping.load(is);
//        Properties ppt = new Properties();
//        // 这时候文件中所描述的所有类都被加载到ppt文件的map集合（包含了配置文件所有的键值对）中
//        try {
//            ppt.load(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // service本质上就是调用了doget或者dopost
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取用户请求的 uri /xx.do
        String uri = req.getRequestURI();
        // 从集合中获取对应请求地址的mapping对象
        HandlerMapping.MVCMapping mapping = HandlerMapping.get(uri);

        // 必须要做的非空判断
        if(mapping == null){
            resp.sendError(404,"自定义MVC：映射地址不存在"+uri);
            // 当不存在时直接结束这个方法
            return;
        }

        Object obj = mapping.getObj();
        Method method = mapping.getMethod();
        Object result = null;
        try {
            // 调用方法（指的是处理请求的方法，如登录、注册...）得到结果
            result = method.invoke(obj,req,resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        switch (mapping.getType()){
            case TEXT:
                // 向前台去回复处理的结果
                resp.getWriter().write((String)result);
                break;
            case VIEW:
                // 页面重定向
                resp.sendRedirect((String)result);
                break;
        }
    }
}
