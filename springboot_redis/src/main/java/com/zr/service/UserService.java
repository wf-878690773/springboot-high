package com.zr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zr.entity.User;

import java.util.List;


public interface UserService {

    List<User> findAll() throws JsonProcessingException;

    User findById(Long id);

    void updateInfo(User user);
}
