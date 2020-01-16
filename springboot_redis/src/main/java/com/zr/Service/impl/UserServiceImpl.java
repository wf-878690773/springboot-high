package com.zr.Service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zr.Service.IUserService;
import com.zr.entity.User;
import com.zr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<User> findAll() throws JsonProcessingException {

        List<User> userList;
        //进redis缓存中查询
        String user = redisTemplate.boundValueOps("user-findAll").get();

        if (user == null) {
            //进数据库中查询
            userList = repository.findAll();
            //将list集合转json字符串
            String value = mapper.writeValueAsString(userList);
            //将数据存到redis中
            redisTemplate.boundValueOps("user-findAll").set(value);

            System.out.println("===============从数据库中获得数据===============");
        } else {
            //类型转换
            JavaType javaType = getCollectionType(ArrayList.class, User.class);
            //将json字符串转化为list
            userList = mapper.readValue(user, javaType);
            System.out.println("===============从redis缓存中获得数据===============");
        }

        return userList;
    }


    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {

        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);


    }
}
