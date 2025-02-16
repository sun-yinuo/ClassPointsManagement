package com.martin.cpmbackend.service.db;

import com.martin.cpmbackend.mode.User;

import java.util.List;

/**
 * user数据库service
 * @author sunyinuo
 */
public interface UserService {

    /**
     * 获取所有用户
     * @return 用户实体类List
     */
    List<User> getUserList();


    /**
     * 根据id查询所有用户
     * @param id id
     * @return 用户实体类
     */
    User getUserById(int id);


    /**
     * 根据学号查询所有用户
     * @param studentNumber 学号
     * @return 用户实体类
     */
    User getUserByStudentNumber(int studentNumber);

    /**
     * 根据userName查询所有用户
     * @param userName 用户名
     * @return 用户实体类
     */
    User getUserByName(String userName);

    /**
     * 添加用户
     * @param user 用户实体类
     * @return 影响
     */
    int addUser(User user);


    /**
     * 根据是否管理获取用户
     * @param administrator 是否管理 1=是 0=否
     * @return 用户实体类
     */
    List<User> getUserByAdministrator(int administrator);


    /**
     * 更新
     * @param user 实体类
     * @return 影响
     */
    int update(User user);

}
