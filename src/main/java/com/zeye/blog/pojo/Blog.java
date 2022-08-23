package com.zeye.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 博客属性：标题、内容、首图、标记、浏览次数、赞赏开启、版权开启、评论开启、是否发布、创建时间、更新时间、描述


 * 留言属性：昵称、邮箱、头像、留言内容、创建时间、父留言id、管理员id
 * 友链属性：网址、名称、创建时间、图片地址
 * 相册属性：图片地址、图片描述、图片名称、拍摄时间地点
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Blog  implements Serializable {

    private Long id;//主键
    private String title;//标题
    private String content;//内容
    private String firstPicture;//首图
    private String flag;//标记
    private Integer views;//游览次数
    private Integer commentCount;//评论次数 commentCount
    private boolean appreciation;//赞赏开启
    private boolean shareStatement;//版权开启
    private boolean commentabled;//评论开启
    private boolean published;//发布
    private boolean recommend;// 是否推荐
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    private String description;// 博客描述

    /**
     * 分类（type）、用户（user）、评论集合（comments）以及分类id（typeId）、用户id（userId），用来实现Mybatis的多表查询和相关功能。
     */
    private Type type;// 只要一个type对象
    private User user;
    private Long typeId;
    private Long userId;
    private List<Comment> comments = new ArrayList<>();



}
