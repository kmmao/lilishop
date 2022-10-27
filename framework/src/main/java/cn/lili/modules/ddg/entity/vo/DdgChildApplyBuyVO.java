package cn.lili.modules.ddg.entity.vo;

import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;

/**
 * 儿童申请采购表
 *
 * @author Allen
 * @since 2022/10/21 7:30 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DdgChildApplyBuyVO extends DdgChildApplyBuy {

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "大图路径")
    private String big;

    @Max(value = 99999999, message = "价格不能超过99999999")
    @ApiModelProperty(value = "商品价格")
    private Double price;

}