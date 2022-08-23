package com.zeye.blog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.Pictures;
import com.zeye.blog.service.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class PicturesController {

    @Autowired
    private PicturesService picturesService;

    @GetMapping("/pictures")
    public String pictures(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,50);
        List<Pictures> listPictures = picturesService.getAllPictures();
        PageInfo<Pictures> pageInfo = new PageInfo<>(listPictures);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/pictures";
    }
    // 跳转到新增相册
    @GetMapping("/pictures/input")
    public String picturesInput(Model model){
        model.addAttribute("pictures", new Pictures());
        return "admin/pictures-input";
    }

    // 新增相册
    @PostMapping("/pictures")
    public String post(Pictures pictures, RedirectAttributes attributes, BindingResult result){
        if (result.hasErrors()){
            return "admin/pictures-input";
        }
        int i = picturesService.savePictures(pictures);
        if (i == 0){
            attributes.addFlashAttribute("message","新增失败!");
        }else {
            attributes.addFlashAttribute("message","新增成功!");
        }
        return "redirect:/admin/pictures";
    }

    //跳转到修改页面
    @GetMapping("/pictures/{id}/input")
    public String editInput(@PathVariable("id") Long id,Model model){
        model.addAttribute("pictures",picturesService.getPicturesById(id));
        return "admin/pictures-input";
    }
    // 修改相册
    @PostMapping("/pictures/{id}")
    public String editPost(Pictures pictures,RedirectAttributes attributes){
        int i = picturesService.updatePictures(pictures);
        if (i == 0){
            attributes.addFlashAttribute("message","修改失败!");
        }else {
            attributes.addFlashAttribute("message","修改成功!");
        }
        return "redirect:/admin/pictures";
    }

    //删除相册
    @GetMapping("/pictures/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        picturesService.deletePictures(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/pictures";
    }



}
