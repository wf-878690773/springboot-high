package com.zr.service.impl;

import com.zr.entity.LikedCountDTO;
import com.zr.entity.User;
import com.zr.entity.UserLike;
import com.zr.enums.LikedStatusEnum;
import com.zr.repository.UserLikeRepository;
import com.zr.service.LikedService;
import com.zr.service.RedisService;
import com.zr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
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

        System.out.println("+++++++++++");
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
        //通过被点赞人和点赞人id查询是否存在点赞记录
        return likeRepository.findByLikedUserIdAndLikedPostId(likedPostId,likedPostId);
    }

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    @Override
    @Transactional
    public void transLikedFromRedis2DB() {

        //将Redis里的点赞数据存入数据库中
        List<UserLike> list = redisService.getLikedDataFromRedis();


        for (UserLike like : list) {

            UserLike userLike = getByLikedUserIdAndLikedPostId(like.getLikedUserId(), like.getLikedPostId());

            if (userLike == null){
                //没有记录,直接存入
                save(like);


            }else {
                //有记录，需要更新
                userLike.setStatus(like.getStatus());
                save(like);
            }

        }

    }

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {

        //将Redis中的点赞数量数据存入数据库
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();


        for (LikedCountDTO countDTO : list) {

            System.out.println("点赞数量数据"+countDTO.getCount());

            User user = userService.findById(countDTO.getId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null){

                Integer likeNum = user.getLikeNum() + countDTO.getCount();
                user.setLikeNum(likeNum);

                //更新点赞数量
                userService.updateInfo(user);
            }

        }

    }
}
