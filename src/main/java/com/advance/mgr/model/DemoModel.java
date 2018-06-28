package com.advance.mgr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author huangj
 * @Description: table:t_store_info
 * @date 2018/6/5
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_store_info")
public class DemoModel {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 仓库编号
     */
    @Column(name = "store_no")
    private String storeNo;

    /**
     * 仓库名称
     */
    @Column(name = "store_name")
    private String storeName;

    /**
     * 仓库简称
     */
    @Column(name = "store_abbr")
    private String storeAbbr;

    /**
     * 仓库类型:(0:总仓;1:自营分仓)
     */
    @Column(name = "store_type")
    private Integer storeType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}
