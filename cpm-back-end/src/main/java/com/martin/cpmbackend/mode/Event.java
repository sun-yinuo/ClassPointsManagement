package com.martin.cpmbackend.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件枚举类
 * @author sunyinuo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    /**事件id**/
    private int id;
    /**事件类型 1=用户相关事件 2=积分+ 3=积分- 4=reset 5=pass 6=Rejected**/
    /**
     * 1:用户登陆注册
     * 2:积分加
     * 3:积分减
     * 4:重制积分
     * 5:班主任审核通过
     * 6:班主任拒绝
     */
    private int eventType;
    /**事件时间**/
    private String time;
    /**执行人**/
    private String operator;
    /**被执行人**/
    private String target;
    /**事件描述**/
    private String event;
    /**学科**/
    private String subject;
    /**原因**/
    private String reason;
}
