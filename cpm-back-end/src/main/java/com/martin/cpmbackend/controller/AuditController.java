package com.martin.cpmbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martin.cpmbackend.mode.Event;
import com.martin.cpmbackend.mode.PointEvent;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.db.EventService;
import com.martin.cpmbackend.service.db.UserService;
import com.martin.cpmbackend.service.db.impl.RedisToolsImpl;
import com.martin.cpmbackend.service.db.impl.UserServiceImpl;
import com.martin.cpmbackend.utils.result.Result;
import com.martin.cpmbackend.utils.result.ResultEnum;
import com.martin.cpmbackend.utils.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunyinuo
 */
@RestController
@RequestMapping("/api/point-events/")
public class AuditController {


    public final UserServiceImpl userService;
    public final EventService eventService;
    public final RedisToolsImpl redisTools;


    public AuditController(UserServiceImpl userService, EventService eventService, RedisToolsImpl redisTools) {
        this.userService = userService;
        this.eventService = eventService;
        this.redisTools = redisTools;
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/getAuditEvents")
    public List<PointEvent> GetPointEventsList(){
        List<PointEvent> event = new ArrayList<>();
        try{
            for(String project:redisTools.getByPrex("EVENT_*")){
                event.add(objectMapper.readValue(project, PointEvent.class));
            }
        }catch (IOException e){

        }

        return event;
    }

    @PostMapping("/updateAuditEvent")
    public Result updateAuditEvent(@RequestBody JSONObject jsonParam){
        String eventId = jsonParam.getString("eventId");
        int pass = jsonParam.getInteger("pass");
        PointEvent event = JSONObject.parseObject((String) redisTools.getByKey("EVENT_" + eventId), PointEvent.class);

        event.setPass(pass);

        redisTools.insert("EVENT_" + event.getEventId(), JSONObject.toJSONString(event));

        if (pass == 0){
            Event refuseEvent = new Event();
            refuseEvent.setId(0);
            refuseEvent.setEventType(6);
            refuseEvent.setTime(String.valueOf(System.currentTimeMillis()));
            refuseEvent.setOperator("班主任");
            refuseEvent.setTarget(event.getTarget());
            refuseEvent.setEvent("班主任拒绝"+event.getOperator()+"给"+event.getTarget()+"的积分请求");
            refuseEvent.setSubject(event.getSubject());
            refuseEvent.setReason("");

            eventService.addEvent(refuseEvent);
        }else if (pass == 1){
            Event passEvent = new Event();
            passEvent.setId(0);
            passEvent.setEventType(5);
            passEvent.setTime(String.valueOf(System.currentTimeMillis()));
            passEvent.setOperator("班主任");
            passEvent.setTarget(event.getTarget());
            passEvent.setEvent("班主任通过"+event.getOperator()+"给"+event.getTarget()+"的积分请求");
            passEvent.setSubject(event.getSubject());
            passEvent.setReason("");

            eventService.addEvent(passEvent);

            Event pointEvent =  eventService.getEventById(event.getEventId());

            String eventString =  pointEvent.getEvent();

            String type =  String.valueOf( eventString.charAt(7));

            int value = Integer.valueOf(String.valueOf( eventString.charAt(8)));
            if (type.equals("加")){
                User user = userService.getUserByName(passEvent.getTarget());
                System.out.println(user.getPoints());



                user.setPoints(user.getPoints() + value);

                userService.update(user);
            }
            if (type.equals("减")){
                User user = userService.getUserByName(passEvent.getTarget());
                user.setPoints(user.getPoints() - value);

                userService.update(user);
            }

        }

        return ResultUtil.result(ResultEnum.SUCCESS.getCode(), "审核成功");
    }

}
