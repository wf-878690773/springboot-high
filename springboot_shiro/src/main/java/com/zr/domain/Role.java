package com.zr.domain;

import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = -227437593919820521L;
    private Integer id;
    private String name;
    private String memo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
