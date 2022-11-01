package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgChildCollect;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildCollectVO;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 嘟嘟罐儿童收藏信息表相关联业务层
 *
 * @author Allen
 * @since 2022-10-20 18:15:43
 */
public interface DdgChildCollectService extends IService<DdgChildCollect> {

    /**
     * 添加儿童收藏信息
     * @return 操作状态
     */
    Boolean addChildCollect(DdgChildCollectVO ddgChildCollectVO);

    /**
     * 通过家长id获取儿童采购申请分页列表
     * @return
     */
    IPage<GoodsSku> getGoodsSkuByChildIdFormCollect(GoodsDdgSearchParams searchParams);

    /**
     * 儿童是否收藏商品信息接口
     * @param ddgChildCollectVO
     * @return
     */
    Boolean isHaveCollectByChildAndSkuId(DdgChildCollectVO ddgChildCollectVO);

    /**
     * 取消儿童收藏商品信息接口
     * @param ddgChildCollectVO
     * @return
     */
    Boolean cancelChildCollect(DdgChildCollectVO ddgChildCollectVO);
}
