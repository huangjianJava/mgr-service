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
public class SysRoleResDto {

    @ApiModelProperty(value = "id", name = "id")
    private Long id;

    @ApiModelProperty(value = "角色名称", name = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "备注", name = "备注")
    private String remark;

    @ApiModelProperty(value = "是否为管理员(1,是;0,否)", name = "是否为管理员(1,是;0,否)")
    private Integer isAdmin;

    @ApiModelProperty(value = "创建时间", name = "创建时间")
    private String createTime;

}