package com.martin.cpmbackend.service.api.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.StudentEnum;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.api.user.RegistrationService;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.md5.Md5Salt;
import com.martin.cpmbackend.utils.md5.Salt;
import com.martin.cpmbackend.utils.result.Result;
import com.martin.cpmbackend.utils.result.ResultEnum;
import com.martin.cpmbackend.utils.result.ResultUtil;
import org.springframework.stereotype.Service;

/**
 * 注册service
 * @author sunyinuo
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {


    public final UserServiceImpl userService;

    public RegistrationServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * 注册
     * @param jsonParam json
     * @return 统一返回
     */
    @Override
    public Result registration(JSONObject jsonParam){
        //用户名
        String userName = (String) jsonParam.get("userName");
        //原始密码
        String originalPassword = (String) jsonParam.get("password");

        //判断输入的姓名是否存在
        if (StudentEnum.containsName(userName)) {

            //检查是否重复注册
            if (userService.getUserByName(userName) == null) {
                //加盐
                Integer salt = Salt.salt();
                //加盐后密码
                String password = Md5Salt.md5Salt(originalPassword,salt);

                User user = new User();
                user.setId(0);
                user.setStudentNumber(StudentEnum.getStudentIdByName(userName));
                user.setUserName(userName);
                user.setUserPassword(password);
                switch (userName){
                    case "班主任" -> user.setAdministrator(2);
                    case "曹燕琳" -> user.setAdministrator(1);
                    default -> user.setAdministrator(0);
                }
                user.setPoints(0);
                user.setSalt(salt);

                userService.addUser(user);

                return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "注册成功");

            }else {
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "此姓名已被注册!");
            }

        }else {
           return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "姓名不存在!");
        }
    }
}
