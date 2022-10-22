package cn.lili.modules.ddg.entity.dto;

import cn.hutool.core.text.CharSequenceUtil;
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


}
