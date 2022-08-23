package com.zeye.blog.controller;


import com.zeye.blog.pojo.FriendLink;
import com.zeye.blog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * 友人帐页面控制器
 */
@Controller
public class FriendShowController {

    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/friends")
    public String friends(Model model){
        model.addAttribute("friends",friendLinkService.getAllFriendLink());
        return "friends";
    }

}
