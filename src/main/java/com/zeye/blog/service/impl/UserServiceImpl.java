package com.zeye.blog.service.impl;

import com.zeye.blog.mapper.UserMapper;
import com.zeye.blog.pojo.User;
import com.zeye.blog.service.UserService;
import com.zeye.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/** 用户模块业务层的实现类*/
@Service //@Service注解：将当前类的对象交给spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User checkUser(String username, String password) {

        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
