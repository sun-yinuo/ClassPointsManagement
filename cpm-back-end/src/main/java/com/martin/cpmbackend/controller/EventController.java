package com.martin.cpmbackend.controller;

import com.martin.cpmbackend.mode.Event;
import com.martin.cpmbackend.service.db.EventService;
import com.martin.cpmbackend.service.db.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 事件相关操作
 * @author sunyinuo
 */
@RestController
@RequestMapping("/api/events/")
public class EventController {

    public final EventServiceImpl eventService;

    public EventController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }


    @GetMapping("/getEventsList")
    public List<Event> getEventsList(){
      return eventService.getEventsList();
    }

    @GetMapping("getEventById")
    public Event getEventById(int id){
        return eventService.getEventById(id);
    }

    @GetMapping("/getEventsByEventType")
    public List<Event> getEventsByEventType(int eventType){
        return eventService.getEventsByEventType(eventType);
    }

    @GetMapping("/getEventByOperator")
    public List<Event> getEventByOperator(String operator){
        return eventService.getEventByOperator(operator);
    }

    @GetMapping("/getEventBySubject")
    public List<Event> getEventBySubject(String subject){
        return eventService.getEventByEventSubject(subject);
    }

    @GetMapping("/getEventByTarget")
    public List<Event> getEventByTarget(String target){
        return eventService.getEventByTarget(target);
    }
}
