package com.zeye.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.Message;
import com.zeye.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 留言功能页面控制器
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;


    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/message")
    public String message(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,15);
        List<Message> messages = messageService.listMessage();
        PageInfo<Message> pageInfo = new PageInfo<>(messages);
        model.addAttribute("messages",pageInfo);
        return "message";
    }
}
