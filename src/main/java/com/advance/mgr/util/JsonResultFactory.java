package com.advance.mgr.util;

import java.util.Date;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResultFactory {

	private static Logger logger = LoggerFactory.getLogger(JsonResultFactory.class);

	private static final ObjectMapper mapper = new ObjectMapper();
    
	
	
	public static <T> String toJson(ResponseEntity<T> responseEntity){
		
		SimpleModule model =new SimpleModule();
		model.addSerializer(Long.class, ToStringSerializer.instance);
		model.addSerializer(Long.TYPE, ToStringSerializer.instance);
		mapper.registerModule(model);
		
		if(responseEntity.getStatusCode()==HttpStatus.OK){
			return toJson(responseEntity.getBody());
		}
		 
        return toJson(true,responseEntity.getStatusCode().value(),"服务请求失败，请重试！");
	}
	
	public static <T> String toJson(T data) {
		
		SimpleModule model =new SimpleModule();
		model.addSerializer(Long.class, ToStringSerializer.instance);
		model.addSerializer(Long.TYPE, ToStringSerializer.instance);
		mapper.registerModule(model);
		

		JsonResult result = getJsonResult(data);
		try {
			return mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException-->{}", e);
			return null;
		}
	}

	public static String toJson(boolean success, int statusCode, String message) {

		JsonResult result = getJsonResult(success, statusCode, message);
		try {
			return mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException-->{}", e);
			return null;
		}
	}

	public static String toJson(boolean success) {

		JsonResult result = getJsonResult(success, 200, null);
	
		try {
			return mapper.writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException-->{}", e);
			return null;
		}
	}

	private static JsonResult getJsonResult(boolean success, int statusCode, String message) {

		JsonResult jsonResult = new JsonResult();
		jsonResult.setMessage(message);
		jsonResult.setStatusCode(statusCode);
		jsonResult.setSuccess(success ? 1 : 0);
		jsonResult.setTimestamp(new Date());
		return jsonResult;

	}

	private static <T> JsonResult getJsonResult(T data) {

		JsonResult jsonResult = new JsonResult();
		jsonResult.setData(data);
		jsonResult.setMessage("success");
		jsonResult.setStatusCode(200);
		jsonResult.setSuccess(1);
		jsonResult.setTimestamp(new Date());
		return jsonResult;
	}

}
