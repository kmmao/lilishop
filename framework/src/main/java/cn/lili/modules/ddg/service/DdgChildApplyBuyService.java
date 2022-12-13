package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 嘟嘟罐儿童采购申请相关联业务层
 *
 * @author Allen
 * @since 2022-10-20 18:15:43
 */
public interface DdgChildApplyBuyService extends IService<DdgChildApplyBuy> {

    /**
     * 添加嘟嘟罐儿童采购申请
     * @return 操作状态
     */
    DdgChildApplyBuy addChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO);

    /**
     * 儿童根据订单编号获取申请采购记录
     * @return 儿童申请采购记录
     */
    DdgChildApplyBuy getChildApplyBySN(String sn);

    /**
     * 通过儿童id获取儿童采购申请分页列表
     * @return
     */
    IPage<DdgChildApplyBuyVO> getChildApplyBuyByChildId(GoodsDdgSearchParams searchParams);

    /**
     * 通过家长id获取儿童采购申请分页列表
     * @param searchParams
     * @return
     */
    IPage<DdgChildApplyBuyVO> getChildApplyBuyByParentId(GoodsDdgSearchParams searchParams);

    /**
     * 获取儿童采购申请列表
     * @param searchParams
     * @return
     */
    List<DdgChildApplyBuyVO> getChildApplyBuy(GoodsDdgSearchParams searchParams);

    /**
     * 取消儿童采购申请
     * @param ddgChildApplyBuyVO
     * @return
     */
    Boolean cancelChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO);

    /**
     * 通过儿童申请采购ID获取商品SKU信息接口
     * @param searchParams
     * @return
     */
    GoodsSkuVO getGoodsSkuByChildApplyBuy(GoodsDdgSearchParams searchParams);
}
