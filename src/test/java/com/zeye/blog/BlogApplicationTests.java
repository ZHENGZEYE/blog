package com.zeye.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeye.blog.mapper.*;
import com.zeye.blog.pojo.*;
import com.zeye.blog.service.BlogService;
import com.zeye.blog.service.MessageService;
import com.zeye.blog.utils.RedisUtil;
import com.zeye.blog.vo.BlogQuery;
import com.zeye.blog.vo.FirstPageBlog;
import com.zeye.blog.vo.RecommendBlog;
import com.zeye.blog.vo.SearchBlog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.*;

@SpringBootTest
class BlogApplicationTests {



    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;






    @Test
    void context() throws JsonProcessingException {

//        // 获取连接
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.
        User user = new User();
        user.setId(1L);
        user.setNickname("张三");
//        String s = new ObjectMapper().writeValueAsString(user);序列化
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));

    }










    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Autowired
    private PicturesMapper picturesMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;



    @Test
    void test1(){
//        redisUtil.set("name","zeye");
//        System.out.println(redisUtil.get("name"));
        List<FirstPageBlog> blog = blogMapper.getAllFirstPageBlog();

        redisUtil.set("blog",blog);
        Object blog1 = redisUtil.get("blog");



    }


    @Test
    void messageTest(){

//        List<Message> list = messageService.listMessage(Long.parseLong("-1"));
//        list.forEach(System.out::println);
    }

    @Test
    void messageList(){
        List<Message> list = messageMapper.findByParentIdNull(Long.parseLong("-1"));
        list.forEach(System.out::println);
    }









    @Test
    void getDetailedBlogSelect(){

        System.out.println(blogMapper.getDetailedBlog(99L));
//        System.out.println(blogMapper.updateViews(99L));
//        System.out.println(blogMapper.getCommentCountByIdAndUpdate(99L));
    }




    @Test
    void firstPageBlogSelect(){
        List<FirstPageBlog> list = blogMapper.getAllFirstPageBlog();
        list.forEach(System.out::println);
        System.out.println("=====================");
        System.out.println("博客总数 = " + blogMapper.getBlogTotal());
        System.out.println("访问总数 = " + blogMapper.getBlogViewTotal());
        System.out.println("评论总数 = " + blogMapper.getBlogCommentTotal());
        System.out.println("留言总数 = " + blogMapper.getBlogMessageTotal());
    }

    @Test
    void recommendBlogSelect(){
        List<RecommendBlog> list = blogService.getAllRecommendBlog();
        list.forEach(System.out::println);
    }

    @Test
    void searchBlogSelect(){
        List<FirstPageBlog> list = blogMapper.getSearchBlog("如何");
        list.forEach(System.out::println);
    }


    @Transactional
    @Test
    void BlogInsert() {
        Blog blog = new Blog();
        blog.setAppreciation(true);
        blog.setCommentabled(true);
        blog.setPublished(false);
        blog.setRecommend(true);
        blog.setShareStatement(false);
        blog.setTitle("如何学习Java");
        blog.setContent("学不死，往死里学");
        blog.setFlag("0");
        blog.setViews(0);
        blog.setCommentCount(0);
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        System.out.println(blogMapper.saveBlog(blog));
    }

    @Test
    void blogQuerySelect() {
//        List<BlogQuery> list = blogMapper.getAllBlogQuery();
//        list.forEach(System.out::println);
        SearchBlog searchBlog = new SearchBlog("M",49L);
        List<BlogQuery> blogQueries = blogMapper.searchByTitleAndType(searchBlog);
        blogQueries.forEach(System.out::println);
    }

    @Test
    void friendLinkInsert(){
        FriendLink friendLink = new FriendLink();
        friendLink.setBlogaddress("222");
        friendLink.setBlogname("admin01");
        friendLink.setPictureaddress("333");
        friendLink.setCreateTime(new Date());

        friendLinkMapper.saveFriendLink(friendLink);

    }

    @Test
    void getAllFriendLink(){
//        List<FriendLink> list = friendLinkMapper.getAllFriendLink();
//        list.forEach(System.out::println);
//        System.out.println(friendLinkMapper.getFriendLinkByBlogAddress("https://blog.csdn.net/m0_57857614"));
//        System.out.println(friendLinkMapper.getFriendById(3L));
//        System.out.println("==========================");
//        FriendLink friendLink = new FriendLink();
//        friendLink.setId(3L);
//        friendLink.setBlogname("admin02");
//        friendLink.setPictureaddress("444");
//        friendLink.setBlogaddress("555");
//        System.out.println(friendLinkMapper.updateFriendLink(friendLink));
//        friendLinkMapper.deleteFriendLink(3L);
    }

    @Test
    void PicturesInsert(){
        Pictures pictures= new Pictures();
        pictures.setPictureaddress("111");
        pictures.setPicturename("测试案例");
        pictures.setPicturetime("测试");
        pictures.setPicturedescription("测试用的");
        picturesMapper.savePictures(pictures);

    }
    @Test
    void select(){
//        List<Pictures> list = picturesMapper.getAllPictures();
//        list.forEach(System.out::println);
//        System.out.println("==================");
//        System.out.println(picturesMapper.getPicturesById(1L));
//        Pictures pictures= new Pictures();
//        pictures.setId(3L);
//        pictures.setPicturename("修改案例");
//        pictures.setPicturedescription("修改");
//        System.out.println(picturesMapper.updatePictures(pictures));


    }























    @Transactional
    @Test
    void typeInsert() {

        Type type = new Type(2L,"推荐");

        int i = typeMapper.saveType(type);
        System.out.println(i);

    }

    @Test
    void typeSelect() {
        List<Type> list = typeMapper.getTypeList();

        list.forEach(System.out::println);

    }

    @Test
    void typeSelectById() {

        System.out.println(typeMapper.getTypeById(43L));

    }

    @Transactional
    @Test
    void typeUpdateById() {
        Type type = new Type();
        type.setId(46L);
        type.setName("炸土豆");

        System.out.println(typeMapper.updateType(type));

    }









    @Transactional
    @Test
    void loginInsert() {

        User user = new User(null,"张5","admin","123456","123@qq.com",3,"url:",new Date(),new Date());

        Integer i = userMapper.insert(user);

        System.out.println(i);
    }

    @Test
    void selectUsernameAndPassword() {

        User user = userMapper.findByUsernameAndPassword("admin", "123456");

        System.out.println(user);
    }


}
