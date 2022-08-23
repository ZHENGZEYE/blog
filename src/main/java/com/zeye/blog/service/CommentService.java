package com.zeye.blog.service;

import com.zeye.blog.pojo.Comment;

import java.util.List;

/* 博客评论业务层接口  */
public interface CommentService {

    /**
     * 根据博客id查询评论信息
     * @param blogId 博客id
     * @return 返回所有评论信息
     */
    List<Comment> listCommentByBlogId(Long blogId);


    /**
     * 添加一个评论数据
     * @param comment 评论数据
     * @return 受影响的行数
     */
    int saveComment(Comment comment);

    /**
     * 管理员根据id删除评论
     * @param id 评论id
     * @param comment 评论信息
     */
    void deleteComment(Comment comment,Long id);



}
