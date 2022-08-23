package com.zeye.blog.service;

import com.zeye.blog.pojo.FriendLink;

import java.util.List;

public interface FriendLinkService {

    /**
     * 新增友链
     * @param friendLink 友链数据
     * @return 返回受影响的行数
     */
    int saveFriendLink(FriendLink friendLink);

    /**
     * 查询全部的友链数据
     * @return 返回全部的友链数据
     */
    List<FriendLink> getAllFriendLink();

    /**
     * 根据网链地址查询网链信息
     * @param blogaddress 网链的地址
     * @return 返回该地址的数据
     */
    FriendLink getFriendLinkByBlogAddress(String blogaddress);


    /**
     * 根据id查询友链数据
     * @param id 友链id
     * @return 友链数据
     */
    FriendLink getFriendById(Long id);

    /**
     * 根据id修改友链数据
     * @param friendLink 友链的数据
     * @return 受影响的行数
     */
    int updateFriendLink(FriendLink friendLink);

    /**
     * 通过id删除友链数据
     * @param id 友链id
     */
    void deleteFriendLink(Long id);
}
