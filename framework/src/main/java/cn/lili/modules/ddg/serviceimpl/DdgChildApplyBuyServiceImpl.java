package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.mapper.DdgChildApplyBuyMapper;
import cn.lili.modules.ddg.mapper.DdgParentsAssignGoodsSkuMapper;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 嘟嘟罐儿童采购申请相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Service
public class DdgChildApplyBuyServiceImpl extends ServiceImpl<DdgChildApplyBuyMapper, DdgChildApplyBuy> implements DdgChildApplyBuyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO) {
        //校验关联申请订单是否重复
        List<DdgChildApplyBuy> ddgChildApplyBuys = this.baseMapper.selectList(new QueryWrapper<DdgChildApplyBuy>()
                .eq("order_no", ddgChildApplyBuyVO.getOrderNo())
        );
        if (!ddgChildApplyBuys.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_CHILD_APPLY_ORDER_REPEAT_ERROR);
        }
        //参数封装
        DdgChildApplyBuy ddgChildApplyBuy = new DdgChildApplyBuy();
        BeanUtil.copyProperties(ddgChildApplyBuyVO, ddgChildApplyBuy);
        // 设置采购时间
        ddgChildApplyBuy.setOrderTime(new Date());
        return this.baseMapper.insert(ddgChildApplyBuy) > 0;
    }

    @Override
    public IPage<DdgChildApplyBuy> getChildApplyBuyByParentId(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getChildApplyBuyByParentId(PageUtil.initPage(searchParams),searchParams.queryChildApplyBuyWrapper(),searchParams.getParentId());
    }
}
