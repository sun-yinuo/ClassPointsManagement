package com.martin.cpmbackend.service.api.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.StudentEnum;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.api.user.LoginService;
import com.martin.cpmbackend.service.db.impl.RedisToolsImpl;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.md5.Md5Salt;
import com.martin.cpmbackend.utils.result.Result;
import com.martin.cpmbackend.utils.result.ResultEnum;
import com.martin.cpmbackend.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author sunyinuo
 */
@Service
public class LoginServiceImpl implements LoginService {

    public final RedisToolsImpl redisTools;
    public final UserServiceImpl userService;

    public LoginServiceImpl(RedisToolsImpl redisTools, UserServiceImpl userService) {
        this.redisTools = redisTools;
        this.userService = userService;
    }

    /**
     * login
     * @param userName userName
     * @param password password
     * @return 统一返回
     */
    @Override
    public Result login(String userName, String password) {
        User userByName = userService.getUserByName(userName);
        if (userByName != null) {
            //将用户输入的密码根据数据库中slat进行加盐
            String slatPassword = Md5Salt.md5Salt(password,userByName.getSalt());
            if (slatPassword.equals(userByName.getUserPassword())){
                //用slat为盐对用户id加密,作为token
                String token = Md5Salt.md5Salt(String.valueOf(userByName.getId()),userByName.getSalt());

                //以用户id为key，生成的token为value，缓存到redis，20天过期
                redisTools.insert(String.valueOf(StudentEnum.getStudentIdByName(userName)),token);
                //以生成的token为key，用户信息为value，缓存到redis，20天过期
                redisTools.insert(token, JSONObject.toJSONString(userByName));
                //全部正确,存入redis,返回
                return ResultUtil.result(ResultEnum.SUCCESS.getCode(), token ,"登陆成功!");
            }else {
                //用户名正确(可以查询到),密码错误,result
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "用户名或密码错误!");
            }
        }else {
            //无法查询到用户名,result
            return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "用户名或密码错误!");
        }
    }
}
