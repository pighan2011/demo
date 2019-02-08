package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 门诊缴费单详情
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:01
 */
@Data
public class OrderDetailsDO implements Serializable {
    private static final long serialVersionUID = 1L;

            //id
        private Long id;
            //项目名称
        private String name;
            //项目价格
        private BigDecimal price;
            //项目数量
        private Double count;
            //小计
        private BigDecimal totalMoney;
            //付款单id
        private Long poId;

    }
