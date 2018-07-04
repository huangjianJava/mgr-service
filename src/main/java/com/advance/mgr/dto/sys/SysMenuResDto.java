package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author huangj
 * @Description:
 * @date 2018/7/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuResDto {

    @ApiModelProperty(value = "id", name = "id")
	private Long id;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0", name = "父菜单ID，一级菜单为0")
	private Long parentId;

    @ApiModelProperty(value = "菜单名称", name = "菜单名称")
	private String name;

    @ApiModelProperty(value = "菜单URL", name = "菜单URL")
	private String path;

    @ApiModelProperty(value = "权限配置 -> 授权如：sys:user:view)", name = "权限配置 -> 授权如：sys:user:view)")
	private String perms;

    @ApiModelProperty(value = "菜单类型(1,目录;2,菜单;3,按钮)", name = "类型(1,目录;2,菜单;3,按钮)")
	private Integer type;

    @ApiModelProperty(value = "菜单类型文本", name = "菜单类型文本")
    private String typeText;

    @ApiModelProperty(value = "菜单图标", name = "菜单图标")
	private String iconCls;

    @ApiModelProperty(value = "排序", name = "排序")
	private Integer orderNum;

    @ApiModelProperty(value = "子菜单", name = "子菜单")
    private List<SysMenuResDto> children;

}