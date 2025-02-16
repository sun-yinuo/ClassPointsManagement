package com.martin.cpmbackend.service.api.user;

import com.martin.cpmbackend.utils.result.Result;

/**
 * 登陆接口
 * @author sunyinuo
 */
public interface LoginService {

    /**
     * login
     * @param userName userName
     * @param password password
     * @return 统一返回
     */
    Result login(String userName, String password);
}
