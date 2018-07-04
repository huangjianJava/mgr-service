package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangj
 * @Description:
 * @date 2018/7/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserResDto {

    @ApiModelProperty(value = "id", name = "id")
    private Long id;

    @ApiModelProperty(value = "账户", name = "账户")
    private String userName;

    @ApiModelProperty(value = "密码", name = "密码")
    private String password;

    @ApiModelProperty(value = "姓名", name = "姓名")
    private String name;

    @ApiModelProperty(value = "邮箱", name = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别  0：女   1：男", name = "性别  0：女   1：男")
    private Integer sex;

    @ApiModelProperty(value = "手机号", name = "手机号")
    private String mobile;

    @ApiModelProperty(value = "是否管理员", name = "是否管理员")
    private Integer isAdmin;

    @ApiModelProperty(value = "创建时间", name = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "角色名称", name = "角色名称")
    private String roleName;

}