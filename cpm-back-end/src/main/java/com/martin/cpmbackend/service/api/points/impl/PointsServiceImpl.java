package com.martin.cpmbackend.service.api.points.impl;

import com.alibaba.fastjson.JSONObject;
import com.martin.cpmbackend.mode.Event;
import com.martin.cpmbackend.mode.PointEvent;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.api.points.PointsService;
import com.martin.cpmbackend.service.db.UserService;
import com.martin.cpmbackend.service.db.impl.EventServiceImpl;
import com.martin.cpmbackend.service.db.impl.RedisToolsImpl;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.result.Result;
import com.martin.cpmbackend.utils.result.ResultEnum;
import com.martin.cpmbackend.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunyinuo
 */
@Service
public class PointsServiceImpl implements PointsService {

    public final RedisToolsImpl redisTools;
    public final UserServiceImpl userService;
    public final EventServiceImpl eventService;


    public PointsServiceImpl(RedisToolsImpl redisTools, UserServiceImpl userService, EventServiceImpl eventService) {
        this.redisTools = redisTools;
        this.userService = userService;
        this.eventService = eventService;
    }


    /**
     * 积分添加操作
     * @param token     token
     * @param jsonParam json
     * @return 统一返回
     */
    @Override
    public Result AddPoints(String token, JSONObject jsonParam) {
        //获取学科
        String subject = jsonParam.getString("subject");
        //获取目标
        String target = jsonParam.getString("target");

        //获取值
        int value = jsonParam.getInteger("value");
        //获取原因
        String reason = jsonParam.getString("reason");

        //根据token查用户信息
        User user = JSONObject.parseObject((String) redisTools.getByKey(token), User.class);

        if (user == null) {
            return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "未登陆!");
        }

        //查权限
        int permission = user.getAdministrator();

        //班主任直接加分
        if (permission == 2){
            //获取原来用户信息
            User student = userService.getUserByName(target);

            if (student == null){
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "未注册!");
            }

            //加分
            student.setPoints(student.getPoints() + value);
            //更新
            int influence = userService.update(student);

            if (influence == 1){
                Event event = new Event();
                event.setId(0);
                event.setEventType(2);
                event.setTime(String.valueOf(System.currentTimeMillis()));
                event.setOperator("班主任");
                event.setTarget(target);
                event.setEvent("由班主任加" + value + "分" + "=>" + target);
                event.setSubject(subject);
                event.setReason(reason);

                //添加事件
                eventService.addEvent(event);

                return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "加分成功!");

            }else {
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "数据库加分处理错误,触发token:" + token
                        + ",加分数:" + value
                        + ",目标:" + target);
            }
        }

        //管理员加分
        if (permission == 1){
            Event event = new Event();
            //event.setId(0);
            event.setEventType(2);
            event.setTime(String.valueOf(System.currentTimeMillis()));
            event.setOperator(user.getUserName());
            event.setTarget(target);
            event.setEvent("由管理员" + user.getUserName() + "加" + value + "分" + "=>" + target +",等待审核");
            event.setSubject(subject);
            event.setReason(reason);

            //添加事件
            eventService.addEvent(event);

            //添加待审核积分事件至redis
            PointEvent pointEvent = new PointEvent();
            pointEvent.setEventId(event.getId());
            pointEvent.setEventType(event.getEventType());
            pointEvent.setTime(event.getTime());
            pointEvent.setOperator(event.getOperator());
            pointEvent.setTarget(event.getTarget());
            pointEvent.setEvent(event.getEvent());
            pointEvent.setSubject(event.getSubject());
            pointEvent.setReason(event.getReason());
            //-1表未审核
            pointEvent.setPass(-1);

            redisTools.insert("EVENT_"+event.getId(), JSONObject.toJSONString(pointEvent));


            return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "提交成功,等待审核");
        }



        return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "无权限!");
    }

    /**
     * 积分减操作
     * @param token     token
     * @param jsonParam json
     * @return 统一返回
     */
    @Override
    public Result SubtractPoints(String token, JSONObject jsonParam) {
        //获取学科
        String subject = jsonParam.getString("subject");
        //获取目标
        String target = jsonParam.getString("target");
        //获取值
        int value = jsonParam.getInteger("value");
        //获取原因
        String reason = jsonParam.getString("reason");

        //根据token查用户信息
        User user = JSONObject.parseObject((String) redisTools.getByKey(token), User.class);

        if (user == null) {
            return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "未登陆!");
        }

        //查权限
        int permission = user.getAdministrator();

        //班主任直接减分
        if (permission == 2){
            //获取原来用户信息
            User student = userService.getUserByName(target);

            if (student == null){
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "未注册!");
            }

            //减分
            student.setPoints(student.getPoints() - value);
            //更新
            int influence = userService.update(student);

            if (influence == 1){
                Event event = new Event();
                event.setId(0);
                event.setEventType(3);
                event.setTime(String.valueOf(System.currentTimeMillis()));
                event.setOperator("班主任");
                event.setTarget(target);
                event.setEvent("由班主任减" + value + "分" + "=>" + target);
                event.setSubject(subject);
                event.setReason(reason);

                //添加事件
                eventService.addEvent(event);

                return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "扣分成功!");

            }else {
                return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "数据库扣分处理错误,触发token:" + token
                        + ",扣分数:" + value
                        + ",目标:" + target);
            }
        }

        //管理员减分
        if (permission == 1){
            Event event = new Event();
            //event.setId(0);
            event.setEventType(3);
            event.setTime(String.valueOf(System.currentTimeMillis()));
            event.setOperator(user.getUserName());
            event.setTarget(target);
            event.setEvent("由管理员" + user.getUserName() + "减" + value + "分" + "=>" + target +",等待审核");
            event.setSubject(subject);
            event.setReason(reason);

            //添加事件
            eventService.addEvent(event);

            //添加待审核积分事件至redis
            PointEvent pointEvent = new PointEvent();
            pointEvent.setEventId(event.getId());
            pointEvent.setEventType(event.getEventType());
            pointEvent.setTime(event.getTime());
            pointEvent.setOperator(event.getOperator());
            pointEvent.setTarget(event.getTarget());
            pointEvent.setEvent(event.getEvent());
            pointEvent.setSubject(event.getSubject());
            pointEvent.setReason(event.getReason());
            //-1表未审核
            pointEvent.setPass(-1);

            redisTools.insert("EVENT_"+event.getId(), JSONObject.toJSONString(pointEvent));


            return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "提交成功,等待审核");
        }



        return ResultUtil.result(ResultEnum.SERVER_ERROR.getCode(), "无权限!");
    }
}
