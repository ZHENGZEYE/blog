package com.zeye.blog.controller;

import com.zeye.blog.service.BlogService;
import com.zeye.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * 时间轴页面显示控制器
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/archives")
    public String archive(Model model){
        List<BlogQuery> blogs = blogService.getAllBlogQuery();
        model.addAttribute("blogs",blogs);
        return "archives";
    }
}
