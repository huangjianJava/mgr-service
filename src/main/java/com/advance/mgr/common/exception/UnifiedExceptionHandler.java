package com.advance.mgr.common.exception;

import com.advance.mgr.common.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author huangj
 * @Description:    统一的异常处理器
 * @date 2018/6/1
 */
/*
@ControllerAdvice
public class UnifiedExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UnifiedExceptionHandler.class);

    @ExceptionHandler({CommonException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResultDto> handleCommonException(Exception ex){
        logger.error("handleCommonException",ex);
        return ResponseEntity.status(HttpStatus.OK).body(ResultDto.fail(ex.getMessage(),ex));
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResultDto> badRequestException(Exception ex){
        return ResponseEntity.badRequest().body(ResultDto.fail(ex.getMessage(),ex));
    }

}
*/
