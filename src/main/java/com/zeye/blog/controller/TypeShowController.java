package com.zeye.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.pojo.Type;
import com.zeye.blog.service.BlogService;
import com.zeye.blog.service.TypeService;
import com.zeye.blog.vo.FirstPageBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 分类页面控制器
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum,
                        @PathVariable("id") Long id, Model model){

        List<Type> types = typeService.getAllTypeAndBlog();

        //id为-1表示从首页导航栏点击进入分类页面
        if (id == -1){
            id = types.get(0).getId();
        }
        model.addAttribute("types",types);
        List<FirstPageBlog> blogs = blogService.getByTypeId(id);

        PageHelper.startPage(pageNum,1000);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
    // 本次遇到的错误，就是 分号影响sql语句，执行从而产生异常
    /**
     * ### Error querying database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'LIMIT 1000' at line 2
     * ### The error may exist in file [E:\ID代码区\blog\target\classes\mapper\BlogMapper.xml]
     * ### The error may involve defaultParameterMap
     * ### The error occurred while setting parameters
     * ### SQL: select count(*) from t_blog;  LIMIT ?
     */

}
