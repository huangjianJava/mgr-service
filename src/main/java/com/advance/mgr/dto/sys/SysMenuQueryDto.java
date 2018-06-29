package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * TABLE_NAME:(t_sys_menu)
 *
 * @author liuzw
 */

@Data
@Builder
public class SysMenuQueryDto {


    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", name = "菜单名称")
    private String name;


    /**
     * 状态 1: 启用 0：停用
     */
    @ApiModelProperty(value = "状态 1: 启用 0：停用", name = "状态 1: 启用 0：停用")
    private Integer status;

}