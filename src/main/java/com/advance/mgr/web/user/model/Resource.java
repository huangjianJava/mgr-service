package com.advance.mgr.web.user.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * 菜单资源前台返回实体
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class Resource {

    private String id;

    private String name;

    private String resourceValue;

    private String parentId;

    private String description;

    private Integer sequence;

    private String code;

    private String dataValues;

    private List<Resource> childResouces;
}