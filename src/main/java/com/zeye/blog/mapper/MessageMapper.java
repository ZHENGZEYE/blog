package com.zeye.blog.mapper;

import com.zeye.blog.pojo.Comment;
import com.zeye.blog.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 留言持久层接口
 */
public interface MessageMapper {
    /**
     * 查询父级留言  根据id为“-1”查询出所有父留言（父级留言id为‘-1’）
     * @param parentId 父级留言id
     * @return 所用父级留言的数据
     */
    List<Message> findByParentIdNull(@Param("parentId") Long parentId);

    /**
     * 查询一级留言  根据父留言的id查询出一级子回复
     * @param id  留言id
     * @return 所有一级留言的数据
     */
    List<Message> findByParentIdNotNull(@Param("id") Long id);


    /**
     * 查询二级留言 根据子回复的id循环迭代查询出所有子集回复
     * @param childId 二级留言id
     * @return 返回所有二级留言数据
     */
    List<Message> findByReplayId(@Param("childId") Long childId);


    /**
     * 添加一个留言数据
     * @param message 留言数据
     * @return 受影响的行数
     */
    int saveMessage(Message message);

    /**
     * 管理员根据id删除留言
     * @param id 留言id
     */
    void deleteMessage(Long id);


    // 根据父评论id查询留言信息
    Message getEmailByParentId(Long parentId);
}
