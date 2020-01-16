package com.zr.domain;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码对象
 */
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;  //过期时间

    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 判断验证码是否已过期。
     * @return
     */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);


    }




    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
