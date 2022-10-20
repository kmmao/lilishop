package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.mapper.DdgParentsAssignGoodsSkuMapper;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 嘟嘟罐商品相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Service
public class DdgParentsAssignGoodsSkuServiceImpl extends ServiceImpl<DdgParentsAssignGoodsSkuMapper, DdgParentsAssignGoodsSku> implements DdgParentsAssignGoodsSkuService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean assignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        //校验关联商品是否重复
        List<DdgParentsAssignGoodsSku> ddgParentsAssignGoodsList = this.baseMapper.selectList(new QueryWrapper<DdgParentsAssignGoodsSku>()
                .eq("child_id", ddgParentsAssignGoodsSkuVO.getChildId())
                .eq("parent_id", ddgParentsAssignGoodsSkuVO.getParentId())
                .eq("goods_sku_id", ddgParentsAssignGoodsSkuVO.getGoodsSkuId())
        );
        if (!ddgParentsAssignGoodsList.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_GOODS_REPEAT_ERROR);
        }
        //参数封装
        DdgParentsAssignGoodsSku ddgParentsAssignGoods = new DdgParentsAssignGoodsSku();
        BeanUtil.copyProperties(ddgParentsAssignGoodsSkuVO, ddgParentsAssignGoods);
        return this.baseMapper.insert(ddgParentsAssignGoods) > 0;
    }

    @Override
    public IPage<GoodsSku> goodsSkuPageByChildId(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.goodsSkuPageByChildId(PageUtil.initPage(searchParams),searchParams.queryGoodsSkuWrapper(),searchParams.getChildId());
    }
}
