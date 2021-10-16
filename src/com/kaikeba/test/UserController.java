package com.kaikeba.test;

import com.kaikeba.mvc.ResponseBody;
import com.kaikeba.mvc.ResponseView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 正准备把这个类当成servlet来用，来处理请求,就要把他放到配置文件中
 */
public class UserController {

    @ResponseBody("/login.do")

    // 在servlet请求中有给方法传递req 和 respon参数，这边要有接收，才能处理收发请求
    public String login(HttpServletRequest req, HttpServletResponse resp){
        return "login success";
    }

    @ResponseView("/reg.do")
    public String reg(HttpServletRequest req, HttpServletResponse resp){
        return "success.jsp";
    }
}
