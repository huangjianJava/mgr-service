package com.advance.mgr.exception;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 公共的错误信息封装
 * @date Date : 2018/7/31 16:32
 * @since JDK 1.8
 */
@Data
public class ErrorResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "异常时间", required = true, dataType = "date")
    private Date date;

    @ApiModelProperty(value = "异常类名", required = true, dataType = "string")
    private String type;

    @ApiModelProperty(value = "异常信息", required = true, dataType = "string")
    private String message;

    @ApiModelProperty(value = "异常堆栈", required = true, dataType = "string")
    private String stackTrace;

    @ApiModelProperty(value = "子异常", required = true, dataType = "object")
    private RestResponse<String> child;

}
