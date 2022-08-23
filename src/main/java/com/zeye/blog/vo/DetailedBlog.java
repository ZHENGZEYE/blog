package com.zeye.blog.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 博客详情实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailedBlog {

    //博客信息
    private Long id; // 博客id
    private String firstPicture; // 博客图片地址
    private String flag;// 是否是原创，或者转载
    private String title;// 博客标题
    private String content;// 博客内容
    private Integer views;// 博客访问数量
    private Integer commentCount;// 博客评论次数
    private Date updateTime;// 修改的时间
    private boolean commentabled;// 是否开启评论
    private boolean shareStatement;// 版权是否开启
    private boolean appreciation;// 是否开启赞赏
    private String nickname;// 博客姓名
    private String avatar;// 博客头像

    //分类名称
    private String typeName;

}
