package com.martin.cpmbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.api.user.impl.LoginServiceImpl;
import com.martin.cpmbackend.service.api.user.impl.RegistrationServiceImpl;
import com.martin.cpmbackend.service.db.impl.RedisToolsImpl;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.result.Result;
import com.martin.cpmbackend.utils.result.ResultEnum;
import com.martin.cpmbackend.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关操作控制
 * @author sunyinuo
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {

    public final RedisToolsImpl redisTools;
    public final RegistrationServiceImpl registrationService;
    public final LoginServiceImpl loginService;
    public final UserServiceImpl userService;

    public UserController(RedisToolsImpl redisTools, RegistrationServiceImpl registrationService, LoginServiceImpl loginService, UserServiceImpl userService) {
        this.redisTools = redisTools;
        this.registrationService = registrationService;
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     * 登陆api,无权限控制
     * @param jsonParam json,包含输入的用户名与密码
     * @return 返回
     */
    @PostMapping("/login")
    public Result login (@RequestBody JSONObject jsonParam){
        return loginService.login((String) jsonParam.get("userName"),(String) jsonParam.get("password"));
    }

    /**
     * 注册
     * @param jsonParam json
     * @return 统一返回
     */
    @PostMapping("/register")
    public Result registration(@RequestBody JSONObject jsonParam){
        return registrationService.registration(jsonParam);
    }

    @PostMapping("/getLoginState")
    public boolean getLoginState(String token){
        return redisTools.getByKey(token) != null;
    }


    @GetMapping("/getPointByName")
    public int getPointByName( String name){
        User user = userService.getUserByName(name);
        if (user != null) {
            return user.getPoints();
        }else {
            return 0;
        }

    }

    @GetMapping("/getAdministratorByToken")
    public int getAdministratorByToken(String token){
        User user = JSONObject.parseObject((String) redisTools.getByKey(token), User.class);
        if (user != null) {
            return user.getAdministrator();
        }else {
            return -1;
        }
    }
}
