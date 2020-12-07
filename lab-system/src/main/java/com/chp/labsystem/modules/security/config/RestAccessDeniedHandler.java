package com.chp.labsystem.modules.security.config;


import com.alibaba.fastjson.JSONObject;
import com.chp.labcommon.utils.Result;
import com.chp.labcommon.utils.enums.ResultStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 权限校验处理器
 * @author K. L. Mao
 * @create 2019/1/11
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSONObject.toJSONString(Result.failure(ResultStatus.BAD_REQUEST,"权限不足"));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
