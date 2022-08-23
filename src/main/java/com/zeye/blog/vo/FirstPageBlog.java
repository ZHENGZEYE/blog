package com.zeye.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 首页博客信息实体类
 * @Date: 2022/08/14
 * @Author: ZHENGZEYE
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FirstPageBlog {

    //博客信息
    private Long id;
    private String title;
    private String firstPicture;
    private Integer views;
    private Integer commentCount;
    private Date updateTime;
    private Date createTime;
    private String description;

    //分类名称
    private String typeName;

    //用户名
    private String nickname;
    //用户头像
    private String avatar;

}
