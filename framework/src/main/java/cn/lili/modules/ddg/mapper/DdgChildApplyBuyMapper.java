package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 嘟嘟罐儿童申请采购相关联数据处理层
 *
 * @author Allen
 * @since 2022-10-20 18:25:16
 */
public interface DdgChildApplyBuyMapper extends BaseMapper<DdgChildApplyBuy> {
    @Select("SELECT cab.*,gs.big,gs.goods_name,gs.price FROM ddg_child_apply_buy cab LEFT JOIN li_goods_sku gs ON cab.goods_sku_id=gs.id WHERE cab.child_id=${childId} AND cab.status = 0 ${ew.customSqlSegment}")
    IPage<DdgChildApplyBuyVO> getChildApplyBuyByChildId(Page<Object> initPage, @Param(Constants.WRAPPER) QueryWrapper<Object> queryChildApplyBuyWrapper, String childId);

    @Select("SELECT cab.*,gs.big,gs.goods_name,gs.price FROM ddg_child_apply_buy cab LEFT JOIN li_goods_sku gs ON cab.goods_sku_id=gs.id WHERE cab.parent_id=${parentId} ${ew.customSqlSegment}")
    IPage<DdgChildApplyBuyVO> getChildApplyBuyByParentId(Page<Object> initPage,@Param(Constants.WRAPPER) QueryWrapper<Object> queryChildApplyBuyWrapper, String parentId);

    @Select("SELECT cab.* FROM ddg_child_apply_buy cab ${ew.customSqlSegment}")
    List<DdgChildApplyBuyVO> getChildApplyBuy(Page<Object> initPage, @Param(Constants.WRAPPER) QueryWrapper<Object> queryChildApplyBuyWrapper);

    @Select("SELECT cab.goods_nums,cab.total_prices,gs.* FROM ddg_child_apply_buy cab LEFT JOIN li_goods_sku gs ON cab.goods_sku_id=gs.id WHERE cab.id=${id}")
    GoodsSkuVO getGoodsSkuByChildApplyBuy(String id);
}