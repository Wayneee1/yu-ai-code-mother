package com.wayne.yuaicodemother.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    /**
     * 错误码
     */
    private final int Code;

    public BusinessException(int code, String message) {
        super(message);
        this.Code = code;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.Code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message){
        super(message);
        this.Code = errorCode.getCode();
    }
}
