package com.martin.cpmbackend.service.db.impl;

import com.martin.cpmbackend.mapper.UserMapper;
import com.martin.cpmbackend.mode.User;
import com.martin.cpmbackend.service.db.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * user数据库service实现
 * @author sunyinuo
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    /**
     * 获取所有用户
     * @return 用户实体类List
     */
    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    /**
     * 根据id查询所有用户
     * @param id id
     * @return 用户实体类
     */
    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    /**
     * 根据学号查询所有用户
     * @param studentNumber 学号
     * @return 用户实体类
     */
    @Override
    public User getUserByStudentNumber(int studentNumber) {
        return userMapper.getUserByStudentNumber(studentNumber);
    }

    /**
     * 根据userName查询所有用户
     * @param userName 用户名
     * @return 用户实体类
     */
    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    /**
     * 添加用户
     * @param user 用户实体类
     * @return 影响
     */
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    /**
     * 根据是否管理获取用户
     * @param administrator 是否管理 1=是 0=否
     * @return 用户实体类
     */
    @Override
    public List<User> getUserByAdministrator(int administrator) {
        return userMapper.getUserByAdministrator(administrator);
    }

    /**
     * 更新
     * @param user 实体类
     * @return 影响
     */
    @Override
    public int update(User user) {
        return userMapper.update(user);
    }
}
