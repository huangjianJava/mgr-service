package com.advance.mgr.exception;

import java.io.Serializable;
import java.util.UUID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: rest响应对象封装
 * @date Date : 2018/7/31 16:29
 * @since JDK 1.8
 */
@ApiModel(description = "响应消息体")
@Data
@ToString
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "响应ID", required = true, dataType = "string")
    private String id = UUID.randomUUID().toString();

    @ApiModelProperty(value = "状态码(业务定义)", required = true, dataType = "string")
    private String code = Integer.toString(HttpStatus.OK.value());

    @ApiModelProperty(value = "状态码描述(业务定义)", required = true, dataType = "string")
    private String message = HttpStatus.OK.getReasonPhrase();

    @ApiModelProperty(value = "结果集(泛型)", required = true, dataType = "object")
    private T result = null;

    @ApiModelProperty(value = "错误", required = true, dataType = "object")
    private ErrorResult error = null;

    public RestResponse() {
        super();
    }

    public RestResponse(T result) {
        super();
        this.result = result;
    }

    /**
     *
     * @param httpStatus http状态
     * @param error 错误
     */
     public RestResponse(HttpStatus httpStatus, ErrorResult error) {
        super();
        this.code = Integer.toString(httpStatus.value());
        this.message = httpStatus.getReasonPhrase();
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param error   错误
     */
    public RestResponse(String code, String message, ErrorResult error) {
        super();
        this.code = code;
        this.message = message;
        this.error = error;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     */
    public RestResponse(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 描述 : 构造函数
     *
     * @param code    状态码(业务定义)
     * @param message 状态码描述(业务定义)
     * @param result  结果集(泛型)
     */
    public RestResponse(String code, String message, T result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }


}
