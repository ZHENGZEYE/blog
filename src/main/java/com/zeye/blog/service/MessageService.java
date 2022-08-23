package com.zeye.blog.service;

import com.zeye.blog.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 留言功能业务层接口
 */
public interface MessageService {
    /**
     * 查询父级留言  根据id为“-1”查询出所有父留言（父级留言id为‘-1’）
     * @return 所用父级留言的数据
     */
    List<Message> listMessage();


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
