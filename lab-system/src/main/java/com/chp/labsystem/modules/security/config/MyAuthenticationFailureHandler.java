package com.chp.labsystem.modules.security.config;

import com.alibaba.fastjson.JSONObject;
import com.chp.labcommon.utils.Result;
import com.chp.labcommon.utils.enums.ResultStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 登录失败操作
 * @author K. L. Mao
 * @create 2019/1/15
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSONObject.toJSONString(Result.failure(ResultStatus.BAD_REQUEST, "登录失败"));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

}
