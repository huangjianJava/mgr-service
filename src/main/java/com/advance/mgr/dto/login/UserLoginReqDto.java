package com.advance.mgr.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author huangj
 * @Description:
 * @date 2018/7/4
 */
@Data
public class UserLoginReqDto {

    @ApiModelProperty(value = "用户名", dataType="String")
    @NotEmpty(message = "用户名不能为空}")
    private String userName;

    @ApiModelProperty(value = "用户密码", dataType="String")
    @NotEmpty(message = "用户密码不能为空")
    private String userPwd;

//    /**
//     * 验证码
//     */
//    @ApiModelProperty(value = "验证码", dataType="String")
//    @NotBlank(message = "{v.captcha.not.empty}")
//    private String captcha;

//    /**
//     * 验证码
//     */
//    @ApiModelProperty(value = "uuid", dataType="uuid")
//    private String uuid;
}
