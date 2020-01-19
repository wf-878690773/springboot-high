package com.zr.service.impl;

import com.zr.entity.LikedCountDTO;
import com.zr.entity.UserLike;
import com.zr.enums.LikedStatusEnum;
import com.zr.service.LikedService;
import com.zr.service.RedisService;
import com.zr.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    LikedService likedService;


    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {

        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        //点赞
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key, LikedStatusEnum.LIKE.getCode());


    }


    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {

        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        //取消点赞
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());

    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        //从Redis中删除一条点赞数据
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);

    }

    @Override
    public void incrementLikedCount(String likedUserId) {
        //该用户的点赞数加1
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,1);

    }

    @Override
    public void decrementLikedCount(String likedUserId) {
        //该用户的点赞数减1
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,-1);

    }

    /**
     *  获取Redis中存储的所有点赞数据
     * @return
     */
    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while(scan.hasNext()){
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostrId = split[1];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            UserLike userLike = new UserLike(likedUserId,likedPostrId,value);
            list.add(userLike);

            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);

        }


        return list;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        return null;
    }
}
