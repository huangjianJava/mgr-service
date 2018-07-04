package com.advance.mgr.dto.login;

import com.advance.mgr.dto.sys.SysMenuResDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author huangj
 * @Description:
 * @date 2018/7/4
 */
@Data
@Builder
public class UserLoginResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", dataType="String")
    private Long id;

    @ApiModelProperty(value = "用户名", dataType="String")
    private String userName;

    @ApiModelProperty(value = "用户姓名", dataType="String")
    private String name;

    @ApiModelProperty(value = "授权码", dataType="String")
    private String token;

//    /**
//     * 登录日期
//     */
//    @ApiModelProperty(value = "登录日期", dataType="Date")
//    private Date loginDate;

    @ApiModelProperty(value = "菜单")
    private List<SysMenuResDto> menuList;

    @ApiModelProperty(value = "权限")
    private Set<String> roleList;

//    /**
//     * 是否第一次登陆
//     */
//    private Boolean firstLoginFlag;

    @ApiModelProperty(value = "是否为管理员")
    private Integer idAdmin;
}
