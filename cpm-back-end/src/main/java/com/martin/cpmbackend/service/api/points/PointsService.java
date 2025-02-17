package com.martin.cpmbackend.service.api.points;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.utils.result.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 添加积分
 * @author sunyinuo
 */
public interface PointsService {

    /**
     * 积分添加操作
     * @param token token
     * @param jsonParam json
     * @return 统一返回
     */
    Result AddPoints(@RequestHeader(value = "token") String token, @RequestBody JSONObject jsonParam);

    /**
     * 积分减操作
     * @param token token
     * @param jsonParam json
     * @return 统一返回
     */
    Result SubtractPoints(@RequestHeader(value = "token") String token,@RequestBody JSONObject jsonParam);
}
