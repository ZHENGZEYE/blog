package com.zeye.blog.service.impl;


import com.alibaba.fastjson.JSON;
import com.zeye.blog.controller.ex.NotFoundException;
import com.zeye.blog.mapper.PicturesMapper;
import com.zeye.blog.pojo.Pictures;
import com.zeye.blog.service.PicturesService;
import com.zeye.blog.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PicturesServiceImpl  implements PicturesService {

    private final static String PICTURES_KEY = "picturesList:picture";// 缓存的key

    private static final Logger LOGGER = LoggerFactory.getLogger(PicturesServiceImpl.class);

    @Autowired
    private PicturesMapper picturesMapper;

    /**
     * TODO 添加redis缓存
     */
    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    @Override
    public int savePictures(Pictures pictures) {
        if (redisUtil.hasKey(PICTURES_KEY)){
            redisUtil.del(PICTURES_KEY);
        }
        return picturesMapper.savePictures(pictures);
    }

    @Override
//    @Cacheable(value = "picturesList",key = "'pictures'") // redis缓存
    public List<Pictures> getAllPictures() {
        // 缓存存在
        if (redisUtil.hasKey(PICTURES_KEY)){
            List<Pictures> pictures = JSON.parseArray((String) redisUtil.get(PICTURES_KEY), Pictures.class);
            LOGGER.info("PicturesServiceImpl. getAllPictures() : 从缓存中获取了数据 >> " + pictures.toString());
            return pictures;
        }
        // 缓存不存在
        List<Pictures> list = picturesMapper.getAllPictures();
        if (list.size() == 0){
            throw new NotFoundException("数据不存在");
        }
        // 写入缓存
        redisUtil.set(PICTURES_KEY,JSON.toJSONString(list),1800);
        LOGGER.info("PicturesServiceImpl. getAllPictures() : 从缓存中插入了数据 >> " + list.toString());
        LOGGER.info("刚才加入redis的数据是： "+redisUtil.get(PICTURES_KEY));

        return picturesMapper.getAllPictures();
    }


    @Override
    public Pictures getPicturesById(Long id) {
        if (id == null){
            throw new NotFoundException("数据id不存在异常");
        }
        return picturesMapper.getPicturesById(id);
    }

    @Transactional
    @Override
    public int updatePictures(Pictures pictures) {
        Pictures id = picturesMapper.getPicturesById(pictures.getId());
        if (id == null){
            throw new NotFoundException("数据不存在异常");
        }
        //先更新
        int i = picturesMapper.updatePictures(pictures);
        //再删缓存
        redisUtil.del(PICTURES_KEY);
        return i;
    }

    @Transactional
    @Override
    public void deletePictures(Long id) {
        if (id == null){
            throw new NotFoundException("id不存在异常");
        }
        picturesMapper.deletePictures(id);
        redisUtil.del(PICTURES_KEY);
    }
}
