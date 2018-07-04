package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author huangj
 * @Description:
 * @date 2018/7/4
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserQueryDto {

    @ApiModelProperty(value = "账户", name = "账户")
    private String userName;

    @ApiModelProperty(value = "姓名", name = "姓名")
    private String name;

}