package cn.lili.modules.ddg.entity.dto;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.lili.common.utils.StringUtils;
import cn.lili.common.vo.PageVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


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

    public <T> QueryWrapper<T> queryGoodsSkuFromAssignWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
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

    public <T> QueryWrapper<T> queryCouponWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
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
