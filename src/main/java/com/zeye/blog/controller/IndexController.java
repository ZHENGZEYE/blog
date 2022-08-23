package com.zeye.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.service.BlogService;
import com.zeye.blog.vo.DetailedBlog;
import com.zeye.blog.vo.FirstPageBlog;
import com.zeye.blog.vo.RecommendBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;


    /**
     * 跳转到主页
     * @param model 存储信息
     * @param pageNum 分页信息
     * @param attributes 重定向信息
     * @return
     */
    @GetMapping({"/","index"})
    public String index(Model model,
                        @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        RedirectAttributes attributes){

        PageHelper.startPage(pageNum,10);


        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
//        allFirstPageBlog.forEach(System.out::println);
//        System.out.println("==================================");
        List<RecommendBlog> allRecommendBlog = blogService.getAllRecommendBlog();
//        allRecommendBlog.forEach(System.out::println);

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        System.out.println("pageInfo: " + pageInfo);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("recommendBlogs",allRecommendBlog);

        return "index";
    }

    // 搜索
    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                         @RequestParam(value = "query") String query){

        PageHelper.startPage(pageNum,1000);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);

        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);

        return "search";
    }


    // 博客信息统计
    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        int blogTotal = blogService.getBlogTotal();//博客总数
        int blogViewTotal = blogService.getBlogViewTotal();//博客访问数
        int blogCommentTotal = blogService.getBlogCommentTotal();//博客评论
        int blogMessageTotal = blogService.getBlogMessageTotal();// 博客留言

        model.addAttribute("blogTotal",blogTotal);
        model.addAttribute("blogViewTotal",blogViewTotal);
        model.addAttribute("blogCommentTotal",blogCommentTotal);
        model.addAttribute("blogMessageTotal",blogMessageTotal);

        return "index :: blogMessage";
    }

    //跳转到博客详情页面
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id,Model model){
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        model.addAttribute("blog",detailedBlog);
        return "blog";
    }



















}
