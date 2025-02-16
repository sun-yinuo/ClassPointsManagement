package com.martin.cpmbackend.service.api.user;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.utils.result.Result;

/**
 * 注册接口
 * @author sunyinuo
 */
public interface RegistrationService {

    /**
     * 注册
     * @param jsonParam json
     * @return 统一返回
     */
    Result registration(JSONObject jsonParam);
}
