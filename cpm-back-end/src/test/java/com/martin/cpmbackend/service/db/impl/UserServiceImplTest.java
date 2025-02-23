package com.martin.cpmbackend.service.db.impl;

import com.martin.cpmbackend.mode.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    public UserServiceImpl userService;

    @Test
    void getUserList() {
        System.out.println(userService.getUserList());
    }

    @Test
    void getUserById() {
        System.out.println(userService.getUserById(1));
    }

    @Test
    void getUserByStudentNumber() {
        System.out.println(userService.getUserByStudentNumber(37));
    }

    @Test
    void getUserByName() {
        System.out.println(userService.getUserByName("孙一诺"));
    }

    /*
    @Test
    void addUser() {
        User user = new User();
        user.setId(0);
        user.setStudentNumber(37);
        user.setUserName("孙一诺");
        user.setUserPassword("123456");
        user.setAdministrator(0);
        user.setPoints(0);
        user.setSalt(0);
        userService.addUser(user);
    }

     */

    @Test
    void getUserByAdministrator() {
        System.out.println(userService.getUserByAdministrator(0));
    }

    /*
    @Test
    void update() {
        User user = new User();
        user.setId(1);
        user.setStudentNumber(37);
        user.setUserName("孙一诺");
        user.setUserPassword("123456");
        user.setAdministrator(0);
        user.setPoints(100);
        user.setSalt(0);
        userService.update(user);
    }

     */
}