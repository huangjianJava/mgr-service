package com.advance.mgr.model.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author huangj
 * @Description:  角色表
 * @date 2018/6/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_role")
public class SysRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

    /**
     * 角色名称
	 */
    @Column(name = "role_name")
	private String roleName;

    /**
     * 备注
	 */
    @Column(name = "remark")
	private String remark;

    /**
     * 创建时间
	 */
    @Column(name = "create_time")
	private String createTime;

}