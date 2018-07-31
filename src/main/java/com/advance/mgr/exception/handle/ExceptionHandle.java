package com.advance.mgr.exception.handle;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import com.advance.mgr.controller.HelloController;
import com.advance.mgr.exception.ErrorResult;
import com.advance.mgr.exception.ExceptionCors;
import com.advance.mgr.exception.RestResponse;
import com.advance.mgr.exception.definedException.AuthException;
import com.advance.mgr.exception.definedException.ParameterValidException;
import com.advance.mgr.exception.definedException.PermissionException;
import com.advance.mgr.exception.definedException.SystemRuntimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 自定义异常处理类 (继承至ResponseEntityExceptionHandler类)
 * 优化异常配置：针对不同的错误需要输出对应的http状态
 * @date Date : 2018/7/31 17:06
 * @since JDK 1.8
 */
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExceptionCors exceptionCors;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
        exceptionCors.fixCors(httpServletResponse);
        //重写源码因为只有spring4.3.4版本支持异常不抛出
        HttpHeaders headers = new HttpHeaders();
        HttpStatus  status = HttpStatus.SERVICE_UNAVAILABLE;
        ErrorResult errorResult = buildError(ex);
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            return super.handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException)ex, headers, status, request);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return super.handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException)ex, headers, status, request);
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
            return super.handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException)ex, headers, status, request);
        } else if (ex instanceof MissingPathVariableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return super.handleMissingPathVariable((MissingPathVariableException)ex, headers, status, request);
        } else if (ex instanceof MissingServletRequestParameterException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleMissingServletRequestParameter((MissingServletRequestParameterException)ex, headers, status, request);
        } else if (ex instanceof ServletRequestBindingException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleServletRequestBindingException((ServletRequestBindingException)ex, headers, status, request);
        } else if (ex instanceof ConversionNotSupportedException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return super.handleConversionNotSupported((ConversionNotSupportedException)ex, headers, status, request);
        } else if (ex instanceof TypeMismatchException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleTypeMismatch((TypeMismatchException)ex, headers, status, request);
        } else if (ex instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleHttpMessageNotReadable((HttpMessageNotReadableException)ex, headers, status, request);
        } else if (ex instanceof HttpMessageNotWritableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return super.handleHttpMessageNotWritable((HttpMessageNotWritableException)ex, headers, status, request);
        }else if (ex instanceof MissingServletRequestPartException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleMissingServletRequestPart((MissingServletRequestPartException)ex, headers, status, request);
        } else if (ex instanceof BindException) {
            status = HttpStatus.BAD_REQUEST;
            return super.handleBindException((BindException)ex, headers, status, request);
        } else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
            return super.handleNoHandlerFoundException((NoHandlerFoundException)ex, headers, status, request);
        } else if (ex instanceof AsyncRequestTimeoutException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            return super.handleAsyncRequestTimeoutException((AsyncRequestTimeoutException)ex, headers, status, request);
        } else if (ex instanceof PermissionException) { //权限异常
            status = HttpStatus.FORBIDDEN;
        } else if (ex instanceof AuthException) { //认证异常
            status = HttpStatus.FORBIDDEN;
        } else if (ex instanceof ParameterValidException) { //参数校验异常
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof RestClientResponseException) { //rest请求异常
            try {
                RestClientResponseException restClientResponseException = (RestClientResponseException) ex;
                String data = restClientResponseException.getResponseBodyAsString();
                if (StringUtils.isNotBlank(data)) {
                    RestResponse<String> child = objectMapper.readValue(data, objectMapper.getTypeFactory().constructParametricType(RestResponse.class, String.class));
                    errorResult.setChild(child);
                }
            } catch (IOException e) {
                throw new SystemRuntimeException(e);
            }
        } else if (ex instanceof MethodArgumentNotValidException) { //参数校验异常
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            try {
                errorResult.setMessage(objectMapper.writeValueAsString(methodArgumentNotValidException.getBindingResult().getAllErrors()));
            } catch (JsonProcessingException e) {
                throw new SystemRuntimeException(e);
            }
        }
        RestResponse<String> restResponse = new RestResponse<>(status, errorResult);
        //logger.error(restResponse.getId(), ex);
        return super.handleExceptionInternal(ex, restResponse, headers, status, request);
    }



    /**
     * 构造错误响应对象
     * @param throwable
     * @return 错误响应对象
     */
    public static ErrorResult buildError(Throwable throwable) {
        ErrorResult error = new ErrorResult();
        error.setType(throwable.getClass().getName());
        error.setMessage(ExceptionUtils.getMessage(throwable));
        //error.setStackTrace(ExceptionUtils.getStackTrace(throwable));
        error.setDate(new Date());
        return error;
    }

}
