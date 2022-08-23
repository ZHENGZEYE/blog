package com.zeye.blog.service.impl;

import com.zeye.blog.mapper.BlogMapper;
import com.zeye.blog.mapper.CommentMapper;
import com.zeye.blog.pojo.Comment;
import com.zeye.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* 博客评论业务层实现接口*/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplays = new ArrayList<>();

    /**
     * 查询评论
     * @param blogId 博客id
     * @return 评论消息
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查询出父节点
        List<Comment> comments = commentMapper.findByBlogParentIdNull(blogId, Long.parseLong("-1"));
        for(Comment comment : comments){
            Long id = comment.getId();
            String parentNickname1 = comment.getNickname();
            List<Comment> childComments = commentMapper.findByBlogParentIdNotNull(blogId,id);
            //查询出子评论
            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplays);
            tempReplays = new ArrayList<>();
        }
        return comments;
    }

    /**
     * 查询出子评论
     * @param blogId
     * @param childComments 所有子评论
     * @param parentNickname1 父评论姓名
     */
    private void combineChildren(Long blogId, List<Comment> childComments, String parentNickname1) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplays.add(childComment);
                Long childId = childComment.getId();
                //查询出子二级评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    /**
     * 循环迭代找出子集回复
     * @param blogId  博客id
     * @param childId 子评论id
     * @param parentNickname1 子评论姓名
     */
    private void recursively(Long blogId, Long childId, String parentNickname1) {
        //根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentMapper.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplays.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }


    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int comments = commentMapper.saveComment(comment);
        //文章评论计数
        blogMapper.getCommentCountByIdAndUpdate(comment.getBlogId());
        return comments;
    }

    @Override
    public void deleteComment(Comment comment, Long id) {
        commentMapper.deleteComment(id);
        //文章评论计数
        blogMapper.getCommentCountByIdAndUpdate(comment.getBlogId());
    }
}
