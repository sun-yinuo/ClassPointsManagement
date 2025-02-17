package com.martin.cpmbackend.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 由非班主任
 * @author sunyinuo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointEvent {

    private int eventId;

    /**
     * 1:用户登陆注册
     * 2:积分加
     * 3:积分减
     * 4:重制积分
     * 5:班主任审核通过
     * 6:班主任拒绝
     */
    /**只可能出现2 3 4**/
    private int eventType;
    /**事件时间**/
    private String time;
    /**执行人**/
    private String operator;
    /**被执行人**/
    private String target;
    /**事件描述**/
    private String event;
    /**原因**/
    private String reason;
    /**通过？**/
    private int pass;
}
