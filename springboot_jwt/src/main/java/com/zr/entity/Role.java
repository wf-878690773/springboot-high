package com.zr.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 角色
 */
public class Role implements GrantedAuthority, Serializable {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}