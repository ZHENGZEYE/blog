package com.zeye.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 搜索博客管理列表
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SearchBlog {

    private String title;
    private Long typeId;
}
