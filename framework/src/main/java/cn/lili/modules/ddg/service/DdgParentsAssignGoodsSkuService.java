package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.goods.entity.dos.GoodsSku;
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
    Boolean assignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO);


    /**
     * 通过儿童ID获取商品SKU列表
     * @return
     */
    IPage<GoodsSku> goodsSkuPageByChildId(GoodsDdgSearchParams searchParams);
}
