package com.zeye.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: 推荐博客数据实体类
 * @Date: 2022/08/14
 * @Author: ZHENGZEYE
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendBlog {

    private Long id;
    private String title;
    private String firstPicture;
    private boolean recommend;
}
