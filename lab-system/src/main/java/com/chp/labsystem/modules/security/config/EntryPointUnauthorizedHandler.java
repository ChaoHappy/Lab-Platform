package com.chp.labsystem.modules.security.config;


import com.alibaba.fastjson.JSONObject;
import com.chp.labcommon.utils.Result;
import com.chp.labcommon.utils.enums.ResultStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 身份校验失败处理器，如 token 错误
 * @author K. L. Mao
 * @create 2019/1/11
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSONObject.toJSONString(Result.failure(ResultStatus.BAD_REQUEST,"身份认证失败"));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
