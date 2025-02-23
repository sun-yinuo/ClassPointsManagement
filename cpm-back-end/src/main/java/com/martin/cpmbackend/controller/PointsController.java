package com.martin.cpmbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.service.api.points.PointsService;
import com.martin.cpmbackend.service.api.points.impl.PointsServiceImpl;
import com.martin.cpmbackend.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 积分相关操作
 * @author sunyinuo
 */
@RestController
@RequestMapping("/api/points/")
public class PointsController {


    public final PointsServiceImpl pointsService;

    public PointsController(PointsServiceImpl pointsService) {
        this.pointsService = pointsService;
    }

    /**
     * 积分添加操作
     * @param token token
     * @param jsonParam json
     * @return 统一返回
     */
    @PostMapping("/addition")
    public Result AddPoints(@RequestHeader(value = "token") String token,@RequestBody JSONObject jsonParam){
        return pointsService.AddPoints(token, jsonParam);
    }

    /**
     * 积分减操作
     * @param token token
     * @param jsonParam json
     * @return 统一返回
     */
    @PostMapping("/subtraction")
    public Result SubtractPoints(@RequestHeader(value = "token") String token,@RequestBody JSONObject jsonParam){
        return pointsService.SubtractPoints(token, jsonParam);
    }


}
