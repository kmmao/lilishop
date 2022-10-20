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
 * 家长分配商品表
 *
 * @author Allen
 * @since 2022/10/20 7:30 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddg_parents_assign_goods_sku")
@ApiModel(value = "家长分配商品")
public class DdgParentsAssignGoodsSku extends BaseEntity {
    private static final long serialVersionUID = -8373810383346174466L;
    @ApiModelProperty(value = "状态(正常1，禁用0)")
    private Boolean status;
    @ApiModelProperty(value = "儿童id")
    private String childId;
    @ApiModelProperty(value = "家长id")
    private String parentId;
    @ApiModelProperty(value = "商品skuId")
    private String goodsSkuId;
    @ApiModelProperty(value = "分配时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String distributionTime;
    @ApiModelProperty(value = "是否需要金币购买（1是0否）")
    private Boolean isGoldPay;
    @ApiModelProperty(value = "备注")
    private String remark;
}