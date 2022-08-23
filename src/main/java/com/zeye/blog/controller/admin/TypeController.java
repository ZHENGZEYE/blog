package com.zeye.blog.controller.admin;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.Type;
import com.zeye.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;


    @GetMapping("/types")
    public String types(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        String orderBy = "id desc";
        PageHelper.startPage(pageNum,100,orderBy);
        List<Type> lists = typeService.getTypeList();
        PageInfo<Type> pageInfo = new PageInfo<>(lists);
        System.out.println("分类：" + pageInfo);
        model.addAttribute("pageInfo",pageInfo); // 分页里面有结果集，为list
        model.addAttribute("lists",lists);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String saveTypes(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }


    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message","不能添加重复分类");
            return "redirect:/admin/types/input";
        }
        int i = typeService.saveType(type);
        if (i == 1){
            attributes.addFlashAttribute("message","新添成功");
        }else{
            attributes.addFlashAttribute("message","新添失败");
        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id,Model model){
        model.addAttribute("type",typeService.getTypeById(id));
        return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String updatePost(Type type,RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null){
            attributes.addFlashAttribute("message","不能修改重复分类");
            return "redirect:/admin/types" + "/" + type.getId() + "/input";
        }
        Integer i = typeService.updateType(type);
        if (i.equals(1)){
            attributes.addFlashAttribute("message","修改成功");
        }else{
            attributes.addFlashAttribute("message","修改失败");
        }
        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }








}
