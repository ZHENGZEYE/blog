package com.zeye.blog.service.impl;


import com.zeye.blog.mapper.FriendLinkMapper;
import com.zeye.blog.pojo.FriendLink;
import com.zeye.blog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Override
    public int saveFriendLink(FriendLink friendLink) {
        friendLink.setCreateTime(new Date());
        return friendLinkMapper.saveFriendLink(friendLink);
    }

    @Override
    //@Cacheable(value = "friendLinkList",key = "'friendLink'") // redis缓存
    public List<FriendLink> getAllFriendLink() {
        return friendLinkMapper.getAllFriendLink();
    }

    @Override
    public FriendLink getFriendLinkByBlogAddress(String blogaddress) {
        return friendLinkMapper.getFriendLinkByBlogAddress(blogaddress);
    }

    @Override
    public FriendLink getFriendById(Long id) {
        return friendLinkMapper.getFriendById(id);
    }

    @Override
    public int updateFriendLink(FriendLink friendLink) {
        return friendLinkMapper.updateFriendLink(friendLink);
    }

    @Override
    public void deleteFriendLink(Long id) {
        friendLinkMapper.deleteFriendLink(id);
    }
}
