package com.martin.cpmbackend.service.db;

import com.martin.cpmbackend.mode.Event;

import java.util.List;

/**
 * @author sunyinuo
 */
public interface EventService {
    /**
     * 获取所有事件
     * @return 事件list
     */
    List<Event> getEventsList();

    /**
     * 根据事件id获取任务
     * @param id id
     * @return 事件
     */
    Event getEventById(int id);

    /**
     * 根据事件类型获取事件
     * @param eventType 事件类型 1=用户相关事件 2=积分+ 3=积分- 4=reset 5=pass 6=Rejected
     * @return 事件list
     */
    List<Event> getEventsByEventType(int eventType);

    /**
     * 根据执行人获取事件
     * @param operator 执行人
     * @return 事件
     */
    List<Event> getEventByOperator(String operator);

    /**
     * 根据被执行人获取事件
     * @param target 被执行人
     * @return 事件
     */
    List<Event> getEventByTarget(String target);

    /**
     * 添加事件
     * @param event 事件
     * @return 影响
     */
    int addEvent(Event event);
}
