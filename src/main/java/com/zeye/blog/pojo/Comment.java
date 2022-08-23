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
 * 评论实体类
 * 评论属性：昵称、邮箱、头像、评论内容、创建时间、博客id、父评论id、管理员id
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Comment implements Serializable {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    /**
     * 博客id（blogId）、父评论id（parentCommentId）、是否为管理员评论（adminComment）
     */
    private Long blogId;
    private Long parentCommentId;
    private boolean adminComment;


    //回复评论
    private List<Comment> replyComments = new ArrayList<>();
    private Comment parentComment;
    private String parentNickname;
}
