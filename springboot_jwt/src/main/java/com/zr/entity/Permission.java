package com.zr.entity;

import java.io.Serializable;

/**
 * 权限
 */
public class Permission implements Serializable {

    private String url;
    private String roleName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}