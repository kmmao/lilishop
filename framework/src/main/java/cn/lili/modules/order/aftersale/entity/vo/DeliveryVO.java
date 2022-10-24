package cn.lili.modules.order.aftersale.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 *退货物流信息
 */
@Data
public class DeliveryVO {
    /**
     * 售后编号
     */
    private String afterSaleSn;

    /**
     * 发货单号
     */
    private String logisticsNo;

    /**
     * 物流公司ID
     */
    private String logisticsId;

    /**
     * 发货时间(yyyy-MM-dd)
     */
    private String sendTime;

    /**
     * 发货时间(yyyy-MM-dd)
     */
    private Date mDeliverTime;
}
