package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
     * 通过儿童ID获取商品SKU列表
     *
     * @return 商品SKU列表
     */
    @Select("SELECT gs.*,dpags.is_gold_pay FROM ddg_parents_assign_goods_sku dpags LEFT JOIN  li_goods_sku gs ON gs.id = dpags.goods_sku_id ${ew.customSqlSegment}")
    IPage<GoodsSkuVO> getGoodsSkuByChildIdFormAssign(IPage<GoodsSkuVO> page, @Param(Constants.WRAPPER) Wrapper<GoodsSkuVO> queryWrapper);


    /**
     * 得到默认平台推荐的商品
     * @return
     */
    @Select("SELECT gs.* FROM li_goods_sku gs ${ew.customSqlSegment}")
    IPage<GoodsSkuVO> getGoodsSkuByPromotionFlag(IPage<GoodsSkuVO> page,  @Param(Constants.WRAPPER) Wrapper<GoodsSkuVO> queryWrapper);

    /**
     * 通过订单编号关联分配儿童的商品信息
     * @param orderSn
     * @return
     */
    @Select("SELECT s.* from ddg_child_apply_buy b INNER JOIN ddg_parents_assign_goods_sku s on s.goods_sku_id = b.goods_sku_id where b.order_no = #{orderSn} limit 1")
    DdgParentsAssignGoodsSku getAssignGoodsSkuByOrderSn(String orderSn);



}