package net.xdclass.online_xdclass.controller;

import net.xdclass.online_xdclass.model.entity.User;
import net.xdclass.online_xdclass.model.request.LoginRequest;
import net.xdclass.online_xdclass.service.UserService;
import net.xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pri/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("register")
    public JsonData register(@RequestBody Map<String,String> userInfo){

        int rows = userService.save(userInfo);

        return rows == 1? JsonData.buildSuccess():JsonData.buildError("注册失败，请重试");

    }


    @RequestMapping ("login")
    public JsonData login(@RequestBody LoginRequest loginRequest){

        String token = userService.findByPhoneAndPwd(loginRequest.getPhone(),loginRequest.getPwd());

        return token == null ? JsonData.buildError("登录失败，账号或密码错误") : JsonData.buildSuccess(token);

    }


    @RequestMapping("find_by_token")
    public JsonData findUserInfoByToken(HttpServletRequest request){

        Integer userId = (Integer) request.getAttribute("user_id");
        if (userId == null){
            return JsonData.buildError("查询失败");
        }

        User user = userService.findByUserId(userId);
        return JsonData.buildSuccess(user);


    }

}
