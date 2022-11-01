package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgChildCollect;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildCollectVO;
import cn.lili.modules.ddg.mapper.DdgChildApplyBuyMapper;
import cn.lili.modules.ddg.mapper.DdgChildColectMapper;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgChildCollectService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 嘟嘟罐儿童采购申请相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Service
public class DdgChildCollectServiceImpl extends ServiceImpl<DdgChildColectMapper, DdgChildCollect> implements DdgChildCollectService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addChildCollect(DdgChildCollectVO ddgChildCollectVO) {
        //校验关联申请订单是否重复
        List<DdgChildCollect> ddgChildCollects = this.baseMapper.selectList(new QueryWrapper<DdgChildCollect>()
                .eq("child_id", ddgChildCollectVO.getChildId())
                .eq("goods_sku_id", ddgChildCollectVO.getGoodsSkuId())
        );
        if (!ddgChildCollects.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_CHILD_COLLECT_REPEAT_ERROR);
        }
        //参数封装
        DdgChildCollect ddgChildCollect = new DdgChildCollect();
        BeanUtil.copyProperties(ddgChildCollectVO, ddgChildCollect);
        return this.baseMapper.insert(ddgChildCollect) > 0;
    }

    @Override
    public IPage<GoodsSku> getGoodsSkuByChildIdFormCollect(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getGoodsSkuByChildIdFormCollect(PageUtil.initPage(searchParams),searchParams.queryGoodsSkuFromCollectWrapper(),searchParams.getChildId());
    }

    @Override
    public Boolean isHaveCollectByChildAndSkuId(DdgChildCollectVO ddgChildCollectVO) {
        //校验关联信息是否重复
        List<DdgChildCollect> ddgChildCollects = this.baseMapper.selectList(new QueryWrapper<DdgChildCollect>()
                .eq("child_id", ddgChildCollectVO.getChildId())
                .eq("goods_sku_id", ddgChildCollectVO.getGoodsSkuId())
        );
        if (!ddgChildCollects.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean cancelChildCollect(DdgChildCollectVO ddgChildCollectVO) {
        //校验关联信息是否存在
        List<DdgChildCollect> ddgChildCollects = this.baseMapper.selectList(new QueryWrapper<DdgChildCollect>()
                .eq("child_id", ddgChildCollectVO.getChildId())
                .eq("goods_sku_id", ddgChildCollectVO.getGoodsSkuId())
        );
        if (ddgChildCollects.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_CHILD_COLLECT_NULL_ERROR);
        }
        return this.removeById(ddgChildCollects.get(0));
    }
}
