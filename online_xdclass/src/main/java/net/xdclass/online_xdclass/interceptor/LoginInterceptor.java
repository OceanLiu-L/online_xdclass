package net.xdclass.online_xdclass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import net.xdclass.online_xdclass.utils.JWTUtils;
import net.xdclass.online_xdclass.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            String accessToken = request.getHeader("token");
            if (accessToken == null ){
                accessToken = request.getParameter("token");
            }
            if (StringUtils.isNotBlank(accessToken)){
                Claims claims = JWTUtils.checkJWT(accessToken);
                if (claims == null){
                    //登录过期
                    sendJsonMessage(response, JsonData.buildError("登录过期，重新登录"));
                    return false;
                }
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                String phone = (String) claims.get("phone");

                request.setAttribute("user_id",id);
                request.setAttribute("name",name);
                request.setAttribute("phone",phone);
                return true;
            }

        } catch (Exception e) { }

        sendJsonMessage(response, JsonData.buildError("请求失败，请重试"));

        return false;
    }

    public static void sendJsonMessage(HttpServletResponse response,Object obj){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json; charset=utf-8");

            PrintWriter writer = response.getWriter();

            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
            response.flushBuffer();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
