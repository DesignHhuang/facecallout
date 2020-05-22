package com.face.callout.exception;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(BusinessException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult businessExceptionHandler(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        if (ex.getErrorCode() == null) {
            logger.warn("---------> business exception code:{}, message:{}",
                    ex.getCode(), ex.getMessage());
            return RestResultBuilder.builder().code(ex.getCode()).message(ex.getMessage()).build();
        }
        logger.warn("---------> business exception errorCode code:{}, message:{}",
                errorCode.getCode(), errorCode.getMessage());
        return RestResultBuilder.builder().errorCode(ex.getErrorCode()).build();
    }


}
