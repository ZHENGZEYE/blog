package com.zeye.blog.service.impl;

import com.zeye.blog.mapper.MessageMapper;
import com.zeye.blog.pojo.Message;
import com.zeye.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    //存放迭代找出的所有子代的集合
    private List<Message> tempReplys = new ArrayList<>();


    /**
     * 查询留言 此方法里面 调用 一级和二级的方法
     * @return
     */
    @Override
    public List<Message> listMessage() {
        //先查询出父节点
        List<Message> messages = messageMapper.findByParentIdNull(Long.parseLong("-1"));
        for (Message message: messages){
            Long id = message.getId();
            String parentNickname1 = message.getNickname();
            List<Message> childMessages = messageMapper.findByParentIdNotNull(id);

            //查询出子留言
            combineChildren(childMessages,parentNickname1);
            message.setReplyMessages(tempReplys);
            tempReplys = new ArrayList<>();
        }

        return messages;
    }

    /**
     * 查询出子留言
     * @param childMessages 所有子留言
     * @param parentNickname1 父留言的姓名
     */
    private void combineChildren(List<Message> childMessages, String parentNickname1) {
        // 判断是否有一级留言
        if (childMessages.size() > 0){
            //循环找出子留言中的id
            for (Message childMessage:childMessages){
                String parentNickname = childMessage.getNickname();
                childMessage.setParentNickname(parentNickname1);//设置要回复留言的名称，我要回复谁？
                tempReplys.add(childMessage);//把他加入集合
                Long childId = childMessage.getId();
                //查询二级以及所有子集回复
                recursively(childId,parentNickname);
            }
        }

    }

    /**
     * 循环迭代找出子集回复
     * @param childId 子留言的id
     * @param parentNickname1 子留言的姓名
     */
    private void recursively(Long childId, String parentNickname1) {
        //根据子一级留言的id找到子二级留言
        List<Message> replayMessages = messageMapper.findByReplayId(childId);

        if (replayMessages.size() > 0){
            for (Message replayMessage:replayMessages){
                String parentNickname = replayMessage.getNickname();
                replayMessage.setParentNickname(parentNickname1);
                Long replayId = replayMessage.getId();
                tempReplys.add(replayMessage);
                // 循环迭代找出子回复
                recursively(replayId,parentNickname);
            }
        }

    }

    //存储留言信息
    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());

        return messageMapper.saveMessage(message);
    }

    @Override   //删除
    public void deleteMessage(Long id) {
        messageMapper.deleteMessage(id);
    }

    @Override
    public Message getEmailByParentId(Long parentId) {
        return null;
    }
}
