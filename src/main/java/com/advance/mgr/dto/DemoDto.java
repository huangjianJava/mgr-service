package com.advance.mgr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangj
 * @Description: DTO demo
 * @date 2018/6/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DemoDto {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("仓库编号")
    private String storeNo;

    @ApiModelProperty("仓库名称")
    private String storeName;

}
