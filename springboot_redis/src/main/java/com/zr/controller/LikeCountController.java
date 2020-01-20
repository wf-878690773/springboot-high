package com.zr.controller;


import com.zr.entity.LikedCountDTO;
import com.zr.entity.UserLike;
import com.zr.service.LikedService;
import com.zr.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("like")
public class LikeCountController {

    @Autowired
    private LikedService likedService;
    @Autowired
    private RedisService redisService;


    @GetMapping("test")
    public String test(){

        return "成功";
    }

    /**
     * 点赞
     * @return
     */
    @PostMapping("like")
    public Map<String, Object> like(@RequestBody UserLike userLike){

        log.info("@userLike request, UserId:" + userLike.getLikedUserId());
        log.info("@userLike request, PostId:" + userLike.getLikedPostId());

        Map<String,Object> map = new HashMap<>();

//        likedService.save(userLike);
          redisService.saveLiked2Redis(userLike.getLikedUserId(),userLike.getLikedPostId());

          redisService.incrementLikedCount(userLike.getLikedUserId());

       /*   likedService.transLikedFromRedis2DB();
          likedService.transLikedCountFromRedis2DB();*/

        map.put("msg","点赞成功");

        return map;
    }

    @PostMapping("unlike")
    public Map<String, Object> unlike(@RequestBody UserLike userLike){

        log.info("@userLike request, UserId:" + userLike.getLikedUserId());
        log.info("@userLike request, PostId:" + userLike.getLikedPostId());

        Map<String,Object> map = new HashMap<>();

//        likedService.save(userLike);
      /*  redisService.deleteLikedFromRedis(userLike.getLikedUserId(),userLike.getLikedPostId());

        redisService.decrementLikedCount(userLike.getLikedUserId());

        likedService.transLikedFromRedis2DB();
        likedService.transLikedCountFromRedis2DB();*/

        map.put("msg","取消点赞功能");

        return map;
    }

    @PostMapping("add")
    public Map<String, Object> add(@RequestBody UserLike userLike) {

        Map<String, Object> map = new HashMap<>();

        likedService.save(userLike);

        map.put("msg", "添加成功");

        return map;
    }

    @GetMapping("list")
    public List<LikedCountDTO> list(@RequestBody UserLike userLike) {


       likedService.transLikedCountFromRedis2DB();

        List<LikedCountDTO> list = redisService.getLikedCountFromRedis();
        for (LikedCountDTO dto : list) {

            System.out.println(dto.getCount());
            System.out.println(dto.getId());
        }
        List<UserLike> redis = redisService.getLikedDataFromRedis();
        for (UserLike redi : redis) {
            System.out.println(redi.getLikedPostId());
            System.out.println(redi.getLikedUserId());

        }


        return list;
    }

}
