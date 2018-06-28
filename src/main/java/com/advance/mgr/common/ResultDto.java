package com.advance.mgr.common;

import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author huangj
 * @Description:    接口统一格式 DTO
 * @date 2018/6/1
 */
public class ResultDto<T> {

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("错误的具体信息")
    private String message;

    @ApiModelProperty("错误代码")
    private ErrorCode errorCode;

    @ApiModelProperty("数据")
    private T data;

    public ResultDto(boolean success) {
        this.success = success;
    }

    public ResultDto() {
    }

    public static ResultDto fail(String msg){
        ResultDto dto = new ResultDto();
        dto.setMessage(msg);
        dto.setSuccess(false);
        return dto;
    }

    public static ResultDto fail(ErrorCode errorCode){
        ResultDto dto = new ResultDto();
        dto.setErrorCode(errorCode);
        dto.setMessage(errorCode.getDesc());
        dto.setSuccess(false);
        return dto;
    }

    public static ResultDto fail(ErrorCode errorCode,String error){
        ResultDto dto = new ResultDto();
        dto.setErrorCode(errorCode);
        dto.setMessage(errorCode.getDesc() + ":" + Strings.nullToEmpty(error));
        dto.setSuccess(false);
        return dto;
    }

    public static <T> ResultDto fail(ErrorCode errorCode,String error,T data){
        ResultDto dto = fail(errorCode, error);
        dto.setData(data);
        return dto;
    }

    public static <T> ResultDto fail(ErrorCode errorCode,T data){
        ResultDto dto = new ResultDto();
        dto.setErrorCode(errorCode);
        dto.setMessage(errorCode.getDesc());
        dto.setSuccess(false);
        dto.setData(data);
        return dto;
    }

    public static <T> ResultDto fail(String msg,T data){
        ResultDto dto = new ResultDto();
        dto.setMessage(msg);
        dto.setSuccess(false);
        dto.setData(data);
        return dto;
    }

    public static ResultDto success(){
        return new ResultDto(true);
    }

    public static <T> ResultDto<T> success(T data){
        ResultDto<T> dto = new ResultDto();
        dto.setSuccess(true);
        dto.setData(data);
        return dto;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
