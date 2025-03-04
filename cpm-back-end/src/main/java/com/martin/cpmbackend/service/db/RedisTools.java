package com.martin.cpmbackend.service.db;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author sunyinuo
 */
public interface RedisTools {

    /**
     * 插入数据
     * @param key key
     * @param value value
     */
    void insert(String key,Object value,long timeout, TimeUnit unit);


    /**
     * 插入数据,无过期时间
     * @param key k
     * @param value v
     */
    void insert(String key,Object value);
    /**
     * 根据key查询信息
     * @param key kay
     * @return 返回
     */
    Object getByKey(String key);

    /**
     * 删除
     * @param key k
     */
    void delete (String key);

    /**
     * 模糊
     * @param prex key
     * @return result
     */
    List getByPrex(String prex);

}
