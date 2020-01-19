package com.zr.service.impl;

import com.zr.entity.UserLike;
import com.zr.enums.LikedStatusEnum;
import com.zr.repository.UserLikeRepository;
import com.zr.service.LikedService;
import com.zr.service.RedisService;
import com.zr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LikedServiceImpl implements LikedService {


    @Autowired
    UserLikeRepository likeRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;



    @Override
    @Transactional
    public UserLike save(UserLike userLike) {

        return likeRepository.save(userLike);
    }

    @Override
    @Transactional
    public List<UserLike> saveAll(List<UserLike> list) {

        return likeRepository.saveAll(list);
    }


    @Override
    public Page<UserLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        //根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
        return likeRepository.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(),pageable);
    }

    @Override
    public Page<UserLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable) {
        //通过被点赞人和点赞人id查询是否存在点赞记录
        return likeRepository.findByLikedPostIdAndStatus(likedPostId,LikedStatusEnum.LIKE.getCode(),pageable);
    }

    @Override
    public UserLike getByLikedUserIdAndLikedPostId(String likedUserId, String likedPostId) {
        return null;
    }

    @Override
    public void transLikedFromRedis2DB() {

    }

    @Override
    public void transLikedCountFromRedis2DB() {

    }
}
