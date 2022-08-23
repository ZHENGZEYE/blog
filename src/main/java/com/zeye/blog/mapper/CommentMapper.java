package com.zeye.blog.mapper;

import com.zeye.blog.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论持久层接口
 */
public interface CommentMapper {

    /**
     * 查询父级评论  根据id为“-1”和博客id查询出所有父评论（父级评论id为‘-1’）
     * @param blogId 评论博客id
     * @param blogParentId 父级评论id
     * @return 所用父级评论的数据
     */
    List<Comment> findByBlogParentIdNull(@Param("blogId") Long blogId,@Param("blogParentId") Long blogParentId);

    /**
     * 查询一级评论  根据父评论的id查询出一级子回复
     * @param blogId  评论博客id
     * @param id  评论id
     * @return 所有一级评论的数据
     */
    List<Comment> findByBlogParentIdNotNull(@Param("blogId") Long blogId,@Param("id") Long id);


    /**
     * 查询二级评论 根据子回复的id循环迭代查询出所有子集回复
     * @param blogId  评论博客id
     * @param childId 二级评论id
     * @return 返回所有二级评论数据
     */
    List<Comment> findByBlogIdAndReplayId(@Param("blogId") Long blogId,@Param("childId") Long childId);


    /**
     * 添加一个评论数据
     * @param comment 评论数据
     * @return 受影响的行数
     */
    int saveComment(Comment comment);

    /**
     * 管理员根据id删除评论
     * @param id 评论id
     */
    void deleteComment(Long id);

    // 根据父评论id查询留言信息
    Comment getEmailByParentId(Long parentId);



}
