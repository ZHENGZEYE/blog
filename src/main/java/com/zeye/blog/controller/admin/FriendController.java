package com.zeye.blog.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.FriendLink;
import com.zeye.blog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class FriendController {

    @Autowired
    private FriendLinkService friendLinkService;

    //友链页面
    @GetMapping("/friendlinks")
    public String friend(Model model, @RequestParam(defaultValue = "1",value = "pageNum")Integer pageNum){
        PageHelper.startPage(pageNum,50);
        List<FriendLink> listFriendLink = friendLinkService.getAllFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<>(listFriendLink);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/friendlinks";
    }

    //跳转到新增页面
    @GetMapping("/friendlinks/input")
    public String input(Model model){
        model.addAttribute("friendlink", new FriendLink());
        return "admin/friendlinks-input";
    }

    //友链新增
    @PostMapping("/friendlinks")
    public String post(FriendLink friendLink, RedirectAttributes attributes, BindingResult result){

        FriendLink link = friendLinkService.getFriendLinkByBlogAddress(friendLink.getBlogaddress());
        if (link != null){
            attributes.addFlashAttribute("message","不能添加相同的网址");
            return "redirect:/admin/friendlinks/input";
        }

        if (result.hasErrors()){
            return "admin/friendlinks-input";
        }
        int i = friendLinkService.saveFriendLink(friendLink);
        if (i == 0){
            attributes.addFlashAttribute("message","新增失败!");
        }else {
            attributes.addFlashAttribute("message","新增成功!");
        }

        return "redirect:/admin/friendlinks";

    }

    @GetMapping("/friendlinks/{id}/input")
    public String editInput(@PathVariable("id") Long id,Model model){
        model.addAttribute("friendlink",friendLinkService.getFriendById(id));
        return "admin/friendlinks-input";
    }

    @PostMapping("/friendlinks/{id}")
    public String editPost(FriendLink friendLink,RedirectAttributes attributes){
        int i = friendLinkService.updateFriendLink(friendLink);
        if (i == 0){
            attributes.addFlashAttribute("message","修改失败!");
        }else {
            attributes.addFlashAttribute("message","修改成功!");
        }
        return "redirect:/admin/friendlinks";
    }

    @GetMapping("/friendlinks/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        friendLinkService.deleteFriendLink(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/friendlinks";
    }

}
