package com.zr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zr.entity.User;

import java.util.List;


public interface IUserService {

    List<User> findAll() throws JsonProcessingException;

}
