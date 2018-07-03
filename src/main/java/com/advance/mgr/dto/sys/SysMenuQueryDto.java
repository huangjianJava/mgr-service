package com.advance.mgr.dto.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangj
 * @Description: 菜单筛选查询 DTO
 * @date 2018/6/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuQueryDto {

    @ApiModelProperty(value = "菜单名称", name = "菜单名称")
    private String name;

}