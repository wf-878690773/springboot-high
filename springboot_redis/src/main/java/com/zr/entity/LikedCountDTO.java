package com.zr.entity;

public class LikedCountDTO {


    private Long id;

    private Integer count;

    private String key;

    private Integer value;

    public LikedCountDTO() {
    }

    public LikedCountDTO(Long id, Integer count) {
        this.id = id;
        this.count = count;
    }


    public LikedCountDTO(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
