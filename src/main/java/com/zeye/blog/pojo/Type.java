package com.zeye.blog.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类实体类
 * 分类属性：分类名称
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Type implements Serializable {

    private Long id;
    private String name;

    private List<Blog> blogs = new ArrayList<>();//包含多个blog

    public Type(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type(String name) {
        this.name = name;
    }

    public Type(String name, List<Blog> blogs) {
        this.name = name;
        this.blogs = blogs;
    }
}
