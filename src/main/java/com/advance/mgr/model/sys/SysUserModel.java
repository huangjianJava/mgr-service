package com.advance.mgr.model.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author huangj
 * @Description:  系统用户表
 * @date 2018/6/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_sys_user")
public class SysUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;

    /**
     * 用户名
	 */
    @Column(name = "name")
	private String name;

    /**
     * 账户
	 */
    @Column(name = "username")
	private String userName;

    /**
     * 密码
	 */
    @Column(name = "password")
	private String password;

    /**
     * 邮箱
	 */
    @Column(name = "email")
	private String email;

    /**
     * 性别  0：女   1：男
	 */
    @Column(name = "sex")
	private Integer sex;

    /**
     * 手机号
	 */
    @Column(name = "mobile")
	private String mobile;

    /**
     * 创建时间
	 */
    @Column(name = "create_time")
	private String createTime;

    /**
     * 是否管理员
     */
    @Column(name = "admin_flag")
    private Integer adminFlag;

}