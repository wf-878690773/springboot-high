package com.zr.exception;

import javax.naming.AuthenticationException;
/**
 * 认证流程添加验证码校验
 */
public class ValidateCodeException extends AuthenticationException {


    private static final long serialVersionUID = 5022575393500654458L;

    public ValidateCodeException(String message){
        super(message);
    }
}
