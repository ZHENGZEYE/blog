package com.zeye.blog.service;

import com.zeye.blog.pojo.Type;

import java.util.List;

/** 分类模块业务层接口*/
public interface TypeService {


    /**
     * 新增分类
     * @param type  分类属性
     * @return  受影响的行数
     */
    int saveType(Type type);

    /**
     * 根据名称查询分类
     * @param name
     * @return
     */
    Type getTypeByName(String name);


    /**
     * 查询全部的Type类
     * @return
     */
    List<Type> getTypeList();


    /**
     * 根据id查询
     * @param id 数据id
     * @return 返回数据
     */
    Type getTypeById(Long id);

    /**
     * 修改分类
     * @param type 分类数据
     * @return 受影响的行数
     */
    Integer updateType(Type type);

    /**
     * 删除分类
     * @param id 删除的id
     */
    void  deleteType(Long id);


    /**
     * 查询所有分类根据博客id来
     * @return
     */
    List<Type> getAllTypeAndBlog();


}
