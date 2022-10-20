package cn.lili.modules.ddg.entity.dos;

import cn.lili.mybatis.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 儿童收藏信息表
 *
 * @author Allen
 * @since 2022/10/20 7:30 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ddg_child_collect")
@ApiModel(value = "儿童收藏信息")
public class DdgChildCollect extends BaseEntity {


    private static final long serialVersionUID = -7911899083778971142L;
    @ApiModelProperty(value = "儿童id")
    private String childId;
    @ApiModelProperty(value = "商品id")
    private String goodsSkuId;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
}