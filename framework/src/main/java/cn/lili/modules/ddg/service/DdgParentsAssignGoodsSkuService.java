package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 嘟嘟罐商品相关联业务层
 *
 * @author Allen
 * @since 2022-10-20 18:15:43
 */
public interface DdgParentsAssignGoodsSkuService extends IService<DdgParentsAssignGoodsSku> {

    /**
     * 嘟嘟罐分配商品
     * @return 操作状态
     */
    Boolean addAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO);

    /**
     * 通过儿童ID获取商品SKU列表
     * @return
     */
    IPage<GoodsSkuVO> getGoodsSkuByChildIdFormAssign(GoodsDdgSearchParams searchParams);

    /**
     * 嘟嘟罐取消分配商品
     * @return 操作状态
     */
    Boolean cancelAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO);

    /**
     * 通过订单编号关联分配儿童的商品信息
     * @return 操作状态
     */
    DdgParentsAssignGoodsSku getAssignGoodsSkuByOrderSn(String orderSn);

    /**
     * 得到默认平台推荐的商品
     * @param searchParams
     * @return
     */
    IPage<GoodsSkuVO> getGoodsSkuByPromotionFlag(GoodsDdgSearchParams searchParams);
}
