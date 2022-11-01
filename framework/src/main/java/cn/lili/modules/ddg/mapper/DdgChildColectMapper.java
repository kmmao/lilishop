package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgChildCollect;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 嘟嘟罐儿童申请采购相关联数据处理层
 *
 * @author Allen
 * @since 2022-10-20 18:25:16
 */
public interface DdgChildColectMapper extends BaseMapper<DdgChildCollect> {
    /**
     * 通过儿童id获取儿童收藏信息分页列表
     */
    @Select("SELECT gs.* FROM li_goods_sku gs WHERE gs.id IN(SELECT goods_sku_id FROM ddg_child_collect WHERE child_id=${childId}) ${ew.customSqlSegment}")
    IPage<GoodsSku> getGoodsSkuByChildIdFormCollect(Page<GoodsSku> initPage, @Param(Constants.WRAPPER) QueryWrapper<GoodsSku> queryChildApplyBuyWrapper, String childId);
}