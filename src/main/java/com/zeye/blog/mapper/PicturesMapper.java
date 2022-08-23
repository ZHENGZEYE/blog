package com.zeye.blog.mapper;

import com.zeye.blog.pojo.Pictures;

import java.util.List;

/**
 * 相册实体类持久层的接口
 */
public interface PicturesMapper {

    /**
     * 新增图片
     * @param pictures
     * @return 受影响的行数
     */
    int savePictures(Pictures pictures);

    /**
     * 查询全部图片
     * @return 返回全部图片的数据
     */
    List<Pictures> getAllPictures();

    /**
     * 根据id查询图片数据
     * @param id 图片id
     * @return 图片的数据
     */
    Pictures getPicturesById(Long id);

    /**
     * 修改相册
     * @param pictures 相册数据
     * @return 受影响的行数
     */
    int updatePictures(Pictures pictures);

    /**
     * 根据id删除相册
     * @param id 相册id
     */
    void deletePictures(Long id);



}
