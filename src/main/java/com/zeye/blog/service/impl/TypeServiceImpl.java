package com.zeye.blog.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeye.blog.controller.ex.NotFoundException;
import com.zeye.blog.mapper.TypeMapper;
import com.zeye.blog.pojo.Type;
import com.zeye.blog.service.TypeService;
import com.zeye.blog.utils.RedisUtil;
import com.zeye.blog.vo.ShowBlog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);


    @Autowired
    private RedisUtil redisUtil;


    @Transactional
    @Override
    public int saveType(Type type) {
        String key = "typeList:type"; //可以抽取出来
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    //@Cacheable(value = "typeList",key = "'type'") // redis缓存
    public List<Type> getTypeList() {

//        //return typeMapper.getTypeList();
        String key = "typeList:type"; // 分页查询功能缓存需要的

        //1.设置要缓存的类型 和 返回的类型
        //ValueOperations<String,List<Type>> operations = redisTemplate.opsForValue();
        //2.获取缓存
        boolean b = redisUtil.hasKey(key);

        //3.判断缓存是否存在
        if (b){
            //4.缓存存在,从redis中获取缓存
            List<Type> list = JSON.parseArray((String) redisUtil.get(key), Type.class);
            LOGGER.info("TypeServiceImpl.getTypeList() : 从缓存中获取了数据 >> " + list.toString());
            //返回给控制器
            return list;
        }


        //5.缓存不存在,从数据库中获取数据
        List<Type> types = typeMapper.getTypeList();
        // 6.判断是否为空，为空抛出异常
        if (types.size() == 0){
            throw new NotFoundException("数据不存在");
        }
        // 7.写入缓存  时间单位是秒
        redisUtil.set(key, JSON.toJSONString(types),300);

        LOGGER.info("TypeServiceImpl.getTypeList() : 从缓存中插入了数据 >> " + types.toString());
        LOGGER.info("刚才加入redis的数据是： "+redisUtil.get(key));


        return types;
    }

    @Transactional
    @Override
    public Type getTypeById(Long id) {
        String key = "Type:show:" + id;

        //1.判断缓存是否存在
        if (redisUtil.hasKey(key)){
            //2.存在 则从缓存中取
            List<Type> list = JSON.parseArray((String) redisUtil.get(key), Type.class);
            //遍历List<Type> 存放到Type中 因为只有一次所以时间复杂度是O(1)
            Type type = new Type();
            for (Type lists :list) {
                type = lists;
            }
            LOGGER.info("TypeServiceImpl.getTypeById() : 从缓存中获取了数据 >> " + type.toString());
            return type;
        }

        //3.不存在，则查询数据库，放到缓存
        Type type1 = typeMapper.getTypeById(id);
        // 3.1.因为使用JSON.parseArray()所以取出来的数据是list 存储的方式应该也以 list集合
        List<Type> list1 = new ArrayList<>();
        // 3.2 .将查出来的分类数据 放到集合中，然后存入缓存
        list1.add(type1);
        redisUtil.set(key,JSON.toJSONString(list1),100);
        LOGGER.info("TypeServiceImpl.getTypeById() : 从缓存中插入了数据 >> " + list1.toString());
        LOGGER.info("刚才加入redis的数据是： "+redisUtil.get(key));

        return type1;
    }

    @Transactional
    @Override
    public Integer updateType(Type type) {
        Type id = typeMapper.getTypeById(type.getId());
        // 获取id
        Long id2 = type.getId();
        String key = "Type:show:" + id2;
        String keys = "typeList:type";
        if (id == null){
            throw  new NotFoundException("更新数据不存在");
        }
        //1.先更新数据库
        Integer i = typeMapper.updateType(type);
        //2.在删除缓存,因为更新后，全部查询里面的缓存也跟数据库不一致，所以这里要再一次把原本的查询全部的缓存删掉
        redisUtil.del(key,keys);
        return i;
    }


    @Transactional
    @Override
    public void deleteType(Long id) {
        String key = "typeList:type";
        typeMapper.deleteType(id);
        redisUtil.del(key);
    }

    @Override
    public List<Type> getAllTypeAndBlog() {
        return typeMapper.getAllTypeAndBlog();
    }
}
