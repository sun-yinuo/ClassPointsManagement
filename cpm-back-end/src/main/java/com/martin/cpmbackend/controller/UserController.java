package com.martin.cpmbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.service.api.user.impl.LoginServiceImpl;
import com.martin.cpmbackend.service.api.user.impl.RegistrationServiceImpl;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关操作控制
 * @author sunyinuo
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    public final RegistrationServiceImpl registrationService;
    public final LoginServiceImpl loginService;

    public UserController(RegistrationServiceImpl registrationService, LoginServiceImpl loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
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


}
