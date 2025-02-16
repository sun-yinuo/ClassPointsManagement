package com.martin.cpmbackend.mode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user表实体类
 * @author sunyinuo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**id**/
    private int id;
    /**学号**/
    private int studentNumber;
    /**用户名**/
    private String userName;
    /**密码**/
    private String userPassword;
    /**是否管理**/
    private int administrator;
    /**积分**/
    private int points;
    /**密码salt**/
    private int salt;
}
