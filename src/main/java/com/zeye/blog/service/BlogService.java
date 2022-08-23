package com.zeye.blog.service;

import com.zeye.blog.pojo.Blog;
import com.zeye.blog.vo.*;

import java.util.List;

public interface BlogService {

    /**
     * 新增博客
     * @param blog 博客数据实体类
     * @return 受影响的行数
     */
    int saveBlog(Blog blog);

    /**
     * 查询文章的全部管理列表
     * @return 全部的文章管理列表
     */
    List<BlogQuery> getAllBlogQuery();


    /**
     * 根据id查询
     * @param id 文章id
     * @return 查询数据
     */
    ShowBlog getBlogById(Long id);


    /**
     * 修改文章
     * @param showBlog 修改的数据
     * @return 受影响的行数
     */
    int updateBlog(ShowBlog showBlog);

    /**
     * 根据id删除博客管理
     * @param id 博客id
     */
    void deleteBlog(Long id);

    /**
     * 搜索博客管理列表
     * @param searchBlog  搜索的数据
     * @return 返回数据列表
     */
    List<BlogQuery> searchByTitleAndType(SearchBlog searchBlog);



    /**
     * 查询首页最新博客列表信息
     * @return 最新博客列表的信息
     */
    List<FirstPageBlog> getAllFirstPageBlog();


    /**
     * 查询首页最新推荐信息
     * @return 返回最新推荐的信息
     */
    List<RecommendBlog> getAllRecommendBlog();

    /**
     * 搜索博客列表
     * @param query 搜索的条件
     * @return 搜索博客列表
     */
    List<FirstPageBlog> getSearchBlog(String query);

    /**
     * 统计博客总数
     * @return 受影响行数
     */
    Integer getBlogTotal();

    /**
     * 统计访问总数
     * @return 受影响行数
     */
    Integer getBlogViewTotal();

    /**
     * 统计评论总数
     * @return 受影响行数
     */
    Integer getBlogCommentTotal();

    /**
     * 统计留言总数
     * @return 受影响行数
     */
    Integer getBlogMessageTotal();


    /**
     * 根据id查询博客详细
     * @param id 博客id
     * @return 返回详细博客页的数据
     */
    DetailedBlog getDetailedBlog(Long id);


    /**
     * 根据TypeId查询博客列表，显示在分类页面
     * @param typeId 分类id
     * @return 返回分类的id查询的所有blog分页列表
     */
    List<FirstPageBlog> getByTypeId(Long typeId);

}
