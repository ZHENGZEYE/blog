package com.zeye.blog.service.impl;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zeye.blog.controller.ex.NotFoundException;
import com.zeye.blog.mapper.BlogMapper;
import com.zeye.blog.pojo.Blog;
import com.zeye.blog.service.BlogService;
import com.zeye.blog.utils.MarkdownUtils;
import com.zeye.blog.utils.RedisUtil;
import com.zeye.blog.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class BlogServiceImpl implements BlogService {


    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public int saveBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        blog.setCreateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Override
    public List<BlogQuery> getAllBlogQuery() {
        return blogMapper.getAllBlogQuery();
    }

    @Override
    public ShowBlog getBlogById(Long id) {

        String key = "showblog_" + id;
        //ValueOperations<String,ShowBlog> operations = redisTemplate.opsForValue();

        // ????????????
        boolean b = redisUtil.hasKey(key);
        if (b){
            List<ShowBlog> showBlogs = JSON.parseArray((String) redisUtil.get(key), ShowBlog.class);
            ShowBlog showBlog = new ShowBlog();
            for (ShowBlog showBlog1 :showBlogs) {
                showBlog = showBlog1;
            }
            LOGGER.info("BlogServiceImpl.getBlogById() : ??????????????????????????? >> " + showBlog.toString());
            return showBlog;
        }
        //???????????????????????????
        ShowBlog blogById = blogMapper.getBlogById(id);
        // ????????????JSON.parseArray()???????????????????????????list ??????????????????????????? list??????
        List<ShowBlog> list = new ArrayList<>();
        list.add(blogById);
        //????????????
        redisUtil.set(key,JSON.toJSONString(list),1000);
        LOGGER.info("BlogServiceImpl.getBlogById() : ??????????????????????????? >> " + blogById.toString());
        LOGGER.info("????????????redis??????????????? "+redisUtil.get(key));

        return blogMapper.getBlogById(id);
    }

    @Transactional // ?????????????????? ???????????? ???????????????
    @Override
    public int updateBlog(ShowBlog showBlog) {
        Long id = showBlog.getId();
        if (id == null) {
            throw  new NotFoundException("??????????????????id????????????");
        }
        //1.??????????????????
        showBlog.setUpdateTime(new Date());
        int i = blogMapper.updateBlog(showBlog);
        //2.???????????????   ????????????????????? ?????????????????????MQ???????????? TCC
        redisUtil.del("showblog_" + showBlog.getId());

        return blogMapper.updateBlog(showBlog);
    }

    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public List<BlogQuery> searchByTitleAndType(SearchBlog searchBlog) {
        return blogMapper.searchByTitleAndType(searchBlog);
    }

    @Override
    //@Cacheable(value = "blogList",key = "'blog'") //redis??????
    public List<FirstPageBlog> getAllFirstPageBlog() {
       return blogMapper.getAllFirstPageBlog();
//        String key = "index_blog";
//        ListOperations<String,FirstPageBlog> operations = redisTemplate.opsForList();
//        /* ????????????*/
//        boolean b = redisUtil.hasKey(key);
//        if (b){
//            List<FirstPageBlog> blogs = operations.range("index_blog",0,-1);
//            System.out.println("??????????????????????????????" + blogs.toString());
//            return blogs;
//        }
//        List<FirstPageBlog> list = blogMapper.getAllFirstPageBlog();
//        /*????????????*/
//        for (FirstPageBlog lists : list) {
//            operations.rightPush(key,lists);
//        }
//        LOGGER.info("????????????????????????" + list.toString());
//        LOGGER.info("????????????redis??????????????? "+operations.range("index_blog",0,-1));
//        return list;
    }

    @Override
    //@Cacheable(value = "commentblogList",key = "'commentblog'")  // redis?????? key ??????????????????
    public List<RecommendBlog> getAllRecommendBlog() {
        List<RecommendBlog> blog = blogMapper.getAllRecommendBlog();
        return blog;
    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public Integer getBlogTotal() {
        return blogMapper.getBlogTotal();
    }

    @Override
    public Integer getBlogViewTotal() {
        return blogMapper.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return blogMapper.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return blogMapper.getBlogMessageTotal();
    }

    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        // ??????????????? ???????????????id?????????????????????????????????id?????????????????????????????????
        DetailedBlog detailedBlog = blogMapper.getDetailedBlog(id);

        if (detailedBlog == null){
            throw new NotFoundException("??????????????????");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //?????????????????????
        blogMapper.updateViews(id);
        //????????????????????????
        blogMapper.getCommentCountByIdAndUpdate(id);
        return detailedBlog;
    }

    @Override
    public List<FirstPageBlog> getByTypeId(Long typeId) {
        return blogMapper.getByTypeId(typeId);
    }


}
