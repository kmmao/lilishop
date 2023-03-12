package cn.lili.modules.ddg.entity.dto;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.lili.common.utils.StringUtils;
import cn.lili.common.vo.PageVO;
import cn.lili.modules.promotion.entity.enums.MemberCouponStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;


/**
 * 商品查询条件-嘟嘟罐
 *
 * @author Allen
 * @since 2022-10-20 19:27:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDdgSearchParams extends PageVO {


    private static final long serialVersionUID = -181962302282076937L;
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "儿童ID")
    private String childId;

    @ApiModelProperty(value = "家长ID")
    private String parentId;

    @ApiModelProperty(value = "商品SKUID")
    private String goodsSkuId;

    @ApiModelProperty(value = "最终成交金额，未进行优惠券渲染")
    private Double finalePrice;

    @ApiModelProperty(value = "状态(待处理0，已处理1)")
    private Boolean status;

    /**
     * @see MemberCouponStatusEnum
     */
    @ApiModelProperty(value = "会员优惠券状态")
    private String memberCouponStatus;

    @ApiModelProperty(value = "从哪个模版领取的优惠券")
    private String couponId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    public <T> QueryWrapper<T> queryGoodsSkuFromAssignWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(childId), "child_id", childId);
        queryWrapper.eq("status", true);
        queryWrapper.like(CharSequenceUtil.isNotEmpty(goodsName), "gs.goods_name", goodsName);
        return queryWrapper;
    }

    public <T> QueryWrapper<T> queryGoodsSkuFromCollectWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

    public <T> QueryWrapper<T> queryChildApplyBuyWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

    public <T> QueryWrapper<T> queryMemberCouponWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotEmpty(childId), "dcuc.child_id", childId);
        queryWrapper.eq(StrUtil.isNotEmpty(memberCouponStatus), "mc.member_coupon_status", memberCouponStatus);
        queryWrapper.eq(StrUtil.isNotEmpty(couponId), "mc.coupon_id", couponId);
        queryWrapper.eq(StrUtil.isNotEmpty(parentId), "dcuc.parent_id", parentId);
        // 增加当前时间为过滤时间节点，防止过期的优惠券也可以用,指针对领取的,结束时间要大于当前日期
        if(MemberCouponStatusEnum.NEW.name().equals(memberCouponStatus)){
            queryWrapper.gt("end_time", new Date());
        }
        return queryWrapper;
    }

    public <T> QueryWrapper<T> queryChildApplyBuyListWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(status, "status", status);
        queryWrapper.eq(StrUtil.isNotEmpty(childId), "child_id", childId);
        queryWrapper.eq(StrUtil.isNotEmpty(parentId), "parent_id", parentId);
        return queryWrapper;
    }

}
