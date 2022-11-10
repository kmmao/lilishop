package cn.lili.modules.order.aftersale.entity.vo;

import cn.lili.modules.order.aftersale.entity.dos.AfterSale;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 售后VO
 *
 * @author Chopper
 * @since 2021/3/12 10:32 上午
 */
@Data
public class AfterSaleVO extends AfterSale {

    /**
     * @see cn.lili.modules.order.order.entity.enums.OrderStatusEnum
     */
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    /**
     * 初始化自身状态
     */
    public AfterSaleAllowOperation getAfterSaleAllowOperationVO() {

        //设置订单的可操作状态
        return new AfterSaleAllowOperation(this);
    }
}
