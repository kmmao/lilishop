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
 * 儿童优惠券关系表
 *
 * @author Allen
 * @since 2022/10/20 7:30 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddg_child_union_coupon")
@ApiModel(value = "儿童优惠券关系")
public class DdgChildUnionCoupon extends BaseEntity {

    private static final long serialVersionUID = 2149539685301812905L;
    @ApiModelProperty(value = "儿童id")
    private String childId;
    @ApiModelProperty(value = "家长id")
    private String parentId;
    @ApiModelProperty(value = "优惠券id")
    private String couponId;
    @ApiModelProperty(value = "用户领取优惠券id")
    private String memberCouponId;
    @ApiModelProperty(value = "1已使用；0未使用")
    private Boolean status;
}