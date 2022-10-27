package cn.lili.modules.order.order.entity.vo;

import cn.hutool.core.text.CharSequenceUtil;
import cn.lili.common.enums.ClientTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单状态信息
 * 用于订单列表查看
 *
 * @author Chopper
 * @since 2020-08-17 20:28
 */
@Data
public class OrderStatusVO {
    public OrderStatusVO(Integer unPaidCount, Integer unDeliveredCount) {
        this.unPaidCount = unPaidCount;
        this.unDeliveredCount = unDeliveredCount;
    }

    /**
     * 未付款数量
     */
    @ApiModelProperty("unPaidCount")
    private Integer unPaidCount;

    /**
     * 待发货数量
     */
    @ApiModelProperty("unDeliveredCount")
    private Integer unDeliveredCount;

}
