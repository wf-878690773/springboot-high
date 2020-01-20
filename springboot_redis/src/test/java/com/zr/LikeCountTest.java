package com.zr;


import com.zr.entity.User;
import com.zr.entity.UserLike;
import com.zr.service.LikedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootApplication
public class LikeCountTest {

    @Autowired
    LikedService likedService;

    @Test
    public void save(){
        UserLike like = new UserLike();
        like.setId(1);
        like.setLikedPostId("1001");
        like.setLikedUserId("2001");

        likedService.save(like);
    }
}
