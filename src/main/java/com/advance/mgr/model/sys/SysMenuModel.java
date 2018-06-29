package com.advance.mgr.model.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author huangj
 * @Description:  菜单表
 * @date 2018/6/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_menu")
public class SysMenuModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

    /**
     * 父菜单ID，一级菜单为0
	 */
    @Column(name = "parent_id")
	private Long parentId;

    /**
     * 菜单名称
	 */
    @Column(name = "name")
	private String name;

    /**
     * 菜单URL
	 */
    @Column(name = "path")
	private String path;

    /**
     * 授权(多个用逗号分隔，如：sys:list,sys:create)
	 */
    @Column(name = "perms")
	private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
	 */
    @Column(name = "type")
	private Integer type;

    /**
     * 菜单图标
	 */
    @Column(name = "iconCls")
	private String iconCls;

    /**
     * 排序
	 */
    @Column(name = "order_num")
	private Integer orderNum;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

}