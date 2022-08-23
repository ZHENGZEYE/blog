package com.zeye.blog.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.Blog;
import com.zeye.blog.pojo.Type;
import com.zeye.blog.pojo.User;
import com.zeye.blog.service.BlogService;
import com.zeye.blog.service.TypeService;
import com.zeye.blog.vo.BlogQuery;
import com.zeye.blog.vo.SearchBlog;
import com.zeye.blog.vo.ShowBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;


    @RequestMapping("/blogs") // 顺带分页
    public String blogs(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        //按照排序字段 倒序 排序
        String orderBy = "update_time desc";
        PageHelper.startPage(pageNum,50,orderBy);// 此分页会把以下的方法调用的sql语句也会进行分页，如果你在
        List<BlogQuery> list = blogService.getAllBlogQuery();
        PageInfo<BlogQuery> pageInfo = new PageInfo<BlogQuery>(list);
        model.addAttribute("types",typeService.getTypeList());
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.getTypeList());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }


    @PostMapping("/blogs")
    public String post(Blog blog,RedirectAttributes attributes,HttpSession session){
        //新增的时候需要传递blog对象，blog对象需要user
        blog.setUser((User) session.getAttribute("user"));
        //设置blog的type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog中的typeId属性
        blog.setTypeId(blog.getType().getId());
        //设置用户id
        blog.setUserId(blog.getUser().getId());

        int i = blogService.saveBlog(blog);
        if (i == 1){
            attributes.addFlashAttribute("message","新增成功");
        }else {
            attributes.addFlashAttribute("message","新增失败");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        ShowBlog blogById = blogService.getBlogById(id);
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("blog",blogById);
        model.addAttribute("types",typeList);
        return "admin/blogs-input";
    }

    @PostMapping("/blogs/{id}")
    public String editPost(ShowBlog showBlog,RedirectAttributes attributes){
        int i = blogService.updateBlog(showBlog);
        if (i == 0){
            attributes.addFlashAttribute("message","修改失败");
        }else {
            attributes.addFlashAttribute("message","修改成功");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    //博客管理搜索列表
    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog,Model model,
                         @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        List<BlogQuery> list = blogService.searchByTitleAndType(searchBlog);
        PageHelper.startPage(pageNum,10);
        PageInfo<BlogQuery> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/blogs :: blogList";
        /**
         *  <div id="table-container"  th:fragment="blogList"> 这里很巧妙
         */
    }





}
