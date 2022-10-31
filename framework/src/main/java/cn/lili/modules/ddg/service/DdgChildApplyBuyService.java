package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.goods.entity.dos.GoodsSku;
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
    Boolean addChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO);

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
     *
     * @param ddgChildApplyBuyVO
     * @return
     */
    Boolean cancelChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO);
}
