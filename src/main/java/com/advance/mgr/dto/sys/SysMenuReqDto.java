package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author huangj
 * @Description:
 * @date 2018/6/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuReqDto {

    @ApiModelProperty(value = "父菜单ID，一级菜单为0", name = "父菜单ID，一级菜单为0")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称", name = "菜单名称")
    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单URL", name = "菜单URL")
    private String path;

    @ApiModelProperty(value = "授权如：sys:user:view)", name = "授权如：sys:user:view)")
    private String perms;

    @ApiModelProperty(value = "类型 (1,目录; 2,菜单;  3,按钮)", name = "类型 (1,目录; 2,菜单;  3,按钮)")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "菜单图标", name = "菜单图标")
    private String iconCls;

    @ApiModelProperty(value = "排序", name = "排序")
    private Integer orderNum;

}