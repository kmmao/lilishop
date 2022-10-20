package cn.lili.modules.ddg.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 儿童申请采购表
 *
 * @author Allen
 * @since 2022/10/20 7:30 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddg_child_apply_buy")
@ApiModel(value = "儿童申请采购")
public class DdgChildApplyBuy extends BaseEntity {

    private static final long serialVersionUID = -5640362461584762721L;

    @ApiModelProperty(value = "状态(正常1，禁用0)")
    private Boolean status;
    @ApiModelProperty(value = "儿童id")
    private String childId;
    @ApiModelProperty(value = "家长id")
    private String parentId;
    @ApiModelProperty(value = "商品id")
    private String goodsId;
    @ApiModelProperty(value = "商品skuId")
    private String goodsSkuId;
    @ApiModelProperty(value = "购买数量")
    private Integer goodsNums;
    @ApiModelProperty(value = "申请采购总价")
    private Double totalPrices;
    @ApiModelProperty(value = "下单时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    @ApiModelProperty(value = "优惠券ID(优惠券兑换记录表ID)")
    private String couponId;
    @ApiModelProperty(value = "备注")
    private String remark;
}