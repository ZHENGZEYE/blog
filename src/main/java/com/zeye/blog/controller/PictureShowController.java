package com.zeye.blog.controller;


import com.zeye.blog.pojo.Pictures;
import com.zeye.blog.service.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 相册页面控制器
 */
@Controller
public class PictureShowController {

    @Autowired
    private PicturesService picturesService;

    @GetMapping("/picture")
    public String picture(Model model){
        List<Pictures> list = picturesService.getAllPictures();
        model.addAttribute("pictures",list);
        return "picture";
    }
}
