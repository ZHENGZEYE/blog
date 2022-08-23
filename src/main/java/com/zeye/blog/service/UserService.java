package com.zeye.blog.service;

import com.zeye.blog.pojo.User;

/** 用户模块业务层接口 */
public interface UserService {


    /**
     * 检查用户
     * @param username 用户名
     * @param password 密码
     * @return  返回用户的数据
     */
    User checkUser(String username,String password);


}
