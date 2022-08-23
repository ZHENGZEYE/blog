package com.zeye.blog.mapper;


import com.zeye.blog.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


public interface UserMapper {


    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);



    User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

}
