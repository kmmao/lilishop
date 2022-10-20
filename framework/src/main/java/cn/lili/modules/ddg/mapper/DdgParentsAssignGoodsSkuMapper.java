package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 嘟嘟罐商品相关联数据处理层
 *
 * @author Allen
 * @since 2022-10-20 18:25:16
 */
public interface DdgParentsAssignGoodsSkuMapper extends BaseMapper<DdgParentsAssignGoodsSku> {

    /**
     * 获取直播商品ID列表
     *
     * @return 直播商品ID列表
     */
    @Select("SELECT gs.* FROM li_goods_sku gs WHERE gs.id IN(SELECT goods_sku_id FROM ddg_parents_assign_goods_sku WHERE child_id=${childId}) ${ew.customSqlSegment}")
    IPage<GoodsSku> goodsSkuPageByChildId(IPage<GoodsSku> page, @Param(Constants.WRAPPER) Wrapper<GoodsSku> queryWrapper, String childId);
}