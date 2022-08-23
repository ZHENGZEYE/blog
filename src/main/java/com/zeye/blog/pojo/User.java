package com.zeye.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *  * 用户属性：昵称、用户名、密码、邮箱、类型、头像、创建时间、更新时间
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User implements Serializable {

    private Long id;
    private String nickname;//昵称
    private String username;//用户名
    private String password;//密码
    private String email;//邮箱
    private Integer type;//类型
    private String avatar;//头像
    private Date createTime;//创建时间
    private Date updateTime;//更新时间


    public User(String nickname, String username, String password, String email, Integer type, String avatar, Date createTime, Date updateTime) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.avatar = avatar;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User(String nickname, String username, String password, String email, Integer type) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
    }
}
