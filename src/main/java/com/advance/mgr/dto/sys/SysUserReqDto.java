package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class SysUserReqDto{

    @ApiModelProperty(value = "账户", name = "账户")
    @NotEmpty(message = "账户不能为空")
    private String userName;

    @ApiModelProperty(value = "姓名", name = "姓名")
    @NotEmpty(message = "账户不能为空")
    private String name;

    @ApiModelProperty(value = "邮箱", name = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别", name = "性别")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty(value = "手机号", name = "手机号")
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "1[34578]\\d{9}")
    private String mobile;

    @ApiModelProperty(value = "是否管理员", name = "是否管理员")
    private Integer adminFlag;

    @ApiModelProperty(value = "角色ID", name = "角色ID")
    private List<Long> roleIds;

}