package com.martin.cpmbackend.service.db.impl;

import com.martin.cpmbackend.mapper.EventMapper;
import com.martin.cpmbackend.mode.Event;
import com.martin.cpmbackend.service.db.EventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunyinuo
 */
@Service
public class EventServiceImpl implements EventService {

    @Resource
    public EventMapper eventMapper;

    /**
     * 获取所有事件
     *
     * @return 事件list
     */
    @Override
    public List<Event> getEventsList() {
        return eventMapper.getEventsList();
    }

    /**
     * 根据事件id获取任务
     *
     * @param id id
     * @return 事件
     */
    @Override
    public Event getEventById(int id) {
        return eventMapper.getEventById(id);
    }

    /**
     * 根据事件类型获取事件
     *
     * @param eventType 事件类型 1=用户相关事件 2=积分+ 3=积分- 4=reset 5=pass 6=Rejected
     * @return 事件list
     */
    @Override
    public List<Event> getEventsByEventType(int eventType) {
        return eventMapper.getEventsByEventType(eventType);
    }

    /**
     * 根据执行人获取事件
     *
     * @param operator 执行人
     * @return 事件
     */
    @Override
    public List<Event> getEventByOperator(String operator) {
        return eventMapper.getEventByOperator(operator);
    }

    /**
     * 根据被执行人获取事件
     *
     * @param target 被执行人
     * @return 事件
     */
    @Override
    public List<Event> getEventByTarget(String target) {
        return eventMapper.getEventByTarget(target);
    }

    /**
     * 根据学科获取事件
     * @param subject 学科
     * @return list
     */
    @Override
    public List<Event> getEventByEventSubject(String subject){
        return eventMapper.getEventByEventSubject(subject);
    }

    /**
     * 添加事件
     *
     * @param event 事件
     * @return 影响
     */
    @Override
    public int addEvent(Event event) {
        return eventMapper.addEvent(event);
    }
}
