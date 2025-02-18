package com.martin.cpmbackend.service.db.impl;

import com.martin.cpmbackend.mode.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    public EventServiceImpl eventService;

    @Test
    void getEventsList() {
        System.out.println(eventService.getEventsList());
    }

    @Test
    void getEventById() {
        System.out.println(eventService.getEventById(6));
        System.out.println(eventService.getEventById(7));
    }

    @Test
    void getEventsByEventType() {
        System.out.println(eventService.getEventsByEventType(2));
        System.out.println(eventService.getEventsByEventType(3));
    }

    @Test
    void getEventByOperator() {
        System.out.println(eventService.getEventByOperator("曹燕琳"));
        System.out.println(eventService.getEventByOperator("班主任"));
    }

    @Test
    void getEventByTarget() {
        System.out.println(eventService.getEventByTarget("李思锐"));
        System.out.println(eventService.getEventByTarget("孙一诺"));
    }

    @Test
    void addEvent() {

        Event event = new Event();
        event.setId(0);
        event.setEventType(2);
        event.setTime("1111111");
        event.setOperator("曹燕琳");
        event.setTarget("李思锐");
        event.setEvent("point +1");
        event.setSubject("语文");
        event.setReason("666");


        eventService.addEvent(event);

        Event event2 = new Event();
        event2.setId(0);
        event2.setEventType(3);
        event2.setTime("11121111");
        event2.setOperator("班主任");
        event2.setTarget("孙一诺");
        event2.setEvent("point +1");
        event2.setSubject("数学");
        event2.setReason("666");
        eventService.addEvent(event2);
    }
}