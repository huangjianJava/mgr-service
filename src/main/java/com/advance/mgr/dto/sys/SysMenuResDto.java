package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TABLE_NAME:(t_sys_menu)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuResDto {

    /**
     * id
	 */
    @ApiModelProperty(value = "id", name = "id")
	private Long id;
    /**
     * 父菜单ID，一级菜单为0
	 */
    @ApiModelProperty(value = "父菜单ID，一级菜单为0", name = "父菜单ID，一级菜单为0")
	private Long parentId;
    /**
     * 菜单名称
	 */
    @ApiModelProperty(value = "菜单名称", name = "菜单名称")
	private String name;
    /**
     * 菜单URL
	 */
    @ApiModelProperty(value = "菜单URL", name = "菜单URL")
	private String path;
    /**
     * 授权如：sys:user:view)
	 */
    @ApiModelProperty(value = "授权如：sys:user:view)", name = "授权如：sys:user:view)")
	private String perms;
    /**
     * 类型 (1,目录; 2,菜单;  3,按钮)
	 */
    @ApiModelProperty(value = "类型 (1,目录; 2,菜单;  3,按钮)", name = "类型 (1,目录; 2,菜单;  3,按钮)")
	private Integer type;
    private String menuType;
    /**
     * 菜单图标
	 */
    @ApiModelProperty(value = "菜单图标", name = "菜单图标")
	private String iconCls;
    /**
     * 排序
	 */
    @ApiModelProperty(value = "排序", name = "排序")
	private Integer orderNum;
//    /**
//     * 是否停用 1: 启用 0：停用
//	 */
//    @ApiModelProperty(value = "是否停用 1: 启用 0：停用", name = "是否停用 1: 启用 0：停用")
//	private Integer status;
    //private String menuStatus;

    /**
     * 是否查询按钮
     */
    @ApiModelProperty(value = "是否查询按钮", name = "是否查询按钮")
    private Boolean isSelectButton;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单", name = "子菜单")
    private List<SysMenuResDto> children;

}