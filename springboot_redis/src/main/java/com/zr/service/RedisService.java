package com.zr.service;

import com.zr.entity.LikedCountDTO;
import com.zr.entity.UserLike;

import java.util.List;

/**
 * 点赞数据在 Redis 中的存储格式
 * 用 Redis 存储两种数据，
 * 一种是记录点赞人、被点赞人、点赞状态的数据，
 * 另一种是每个用户被点赞了多少次，做个简单的计数。
 * 由于需要记录点赞人和被点赞人，还有点赞状态（点赞、取消点赞），
 * 还要固定时间间隔取出 Redis 中所有点赞数据，分析了下 Redis 数据格式中 Hash 最合适
 *
 *
 * Hash 里的数据都是存在一个键里，可以通过这个键很方便的把所有的点赞数据都取出。
 * 这个键里面的数据还可以存成键值对的形式，方便存入点赞人、被点赞人和点赞状态。
 *
 *
 */
public interface RedisService {
    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLiked2Redis(String likedUserId, String likedPostId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId);

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    void incrementLikedCount(String likedUserId);

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    void decrementLikedCount(String likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<UserLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

}
