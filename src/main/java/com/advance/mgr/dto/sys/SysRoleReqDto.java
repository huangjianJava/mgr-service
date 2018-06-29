package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

/**
 * @author huangj
 * @Description:
 * @date 2018/6/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleReqDto{

    @ApiModelProperty(value = "角色名称", name = "角色名称")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    @ApiModelProperty(value = "备注", name = "备注")
    private String remark;

    /**
     * 角色拥有菜单id
     */
    private List<Integer> menuIds;

}