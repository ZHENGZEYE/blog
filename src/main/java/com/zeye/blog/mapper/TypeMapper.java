package com.zeye.blog.mapper;


import com.zeye.blog.pojo.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 分类持久层*/
public interface TypeMapper {


    /**
     * 新增分类
     * @param type 分类属性
     * @return 受影响的行数
     */
    int saveType(Type type);


    /**
     * 查询分类
     * @param name 分类的名称
     * @return 分类数据
     */
    Type getTypeByName(@Param("name") String name);

    /**
     * 查询全部的分类属性
     * @return 全部分类的信息
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
