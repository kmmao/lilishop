package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.common.utils.StringUtils;
import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.mapper.DdgParentsAssignGoodsSkuMapper;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 嘟嘟罐商品相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Slf4j
@Service
public class DdgParentsAssignGoodsSkuServiceImpl extends ServiceImpl<DdgParentsAssignGoodsSkuMapper, DdgParentsAssignGoodsSku> implements DdgParentsAssignGoodsSkuService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        //校验关联商品是否重复
        List<DdgParentsAssignGoodsSku> ddgParentsAssignGoodsList = this.baseMapper.selectList(new QueryWrapper<DdgParentsAssignGoodsSku>()
                .eq("child_id", ddgParentsAssignGoodsSkuVO.getChildId())
                .eq("parent_id", ddgParentsAssignGoodsSkuVO.getParentId())
                .eq("goods_sku_id", ddgParentsAssignGoodsSkuVO.getGoodsSkuId())
                .eq("status", 1)
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
    public IPage<GoodsSkuVO> getGoodsSkuByChildIdFormAssign(GoodsDdgSearchParams searchParams) {
        //TODO lk 儿童商城列表商品列表增加固定广告位6个(4个展示一组)
        // 这里以promotionFlag来当广告商品标识
        IPage<GoodsSkuVO> goodsSkuByChildIdFormAssign = this.baseMapper.getGoodsSkuByChildIdFormAssign(PageUtil.initPage(searchParams),searchParams.queryGoodsSkuFromAssignWrapper());
        IPage<GoodsSkuVO> goodsSkuByPromotionFlag = new Page<>();
        log.info("getGoodsSkuByChildIdFormAssign,searchParams.getAppType: {}", searchParams.getAppType());
        if(searchParams.getAppType() != null && searchParams.getAppType().equals("child")){
            goodsSkuByPromotionFlag = this.baseMapper.getGoodsSkuByPromotionFlag(PageUtil.initPage(searchParams),searchParams.queryGoodsSkuByPromotionFlagWrapper());
        }
        return mergeGoodsSkuPages(goodsSkuByChildIdFormAssign,goodsSkuByPromotionFlag);
    }

    public IPage<GoodsSkuVO> mergeGoodsSkuPages(IPage<GoodsSkuVO> goodsSkuByChildIdFormAssign, IPage<GoodsSkuVO> goodsSkuByPromotionFlag) {
        List<GoodsSkuVO> goodsSkuByChildIdList = goodsSkuByChildIdFormAssign.getRecords();
        List<GoodsSkuVO> goodsSkuByPromotionList = goodsSkuByPromotionFlag.getRecords();

        // 去重：移除在 goodsSkuByChildIdList 中已经存在的 items
        List<GoodsSkuVO> finalGoodsSkuByChildIdList = goodsSkuByChildIdList;
        List<GoodsSkuVO> distinctPromotionList = goodsSkuByPromotionList.stream()
                .filter(promotionItem -> finalGoodsSkuByChildIdList.stream()
                        .noneMatch(childItem -> childItem.equals(promotionItem)))
                .collect(Collectors.toList());

        // 计算 positions 数组
        int[] positions = calculatePositions(distinctPromotionList.size());

        // 插入去重后的数据到指定位置
        for (int i = 0; i < distinctPromotionList.size(); i++) {
            int position = positions[i];
            // Adjust the position to fit within the list size
            if (position > goodsSkuByChildIdList.size()) {
                position = goodsSkuByChildIdList.size();
            }
            goodsSkuByChildIdList.add(position, distinctPromotionList.get(i));
        }

        // Ensure the final list size is within the page size limit
//        if (goodsSkuByChildIdList.size() > 20) {
//            goodsSkuByChildIdList = goodsSkuByChildIdList.subList(0, 20);
//        }

        IPage<GoodsSkuVO> resultPage = new Page<>(goodsSkuByChildIdFormAssign.getCurrent(), goodsSkuByChildIdFormAssign.getSize(), goodsSkuByChildIdFormAssign.getTotal());
        resultPage.setRecords(goodsSkuByChildIdList);

        return resultPage;
    }

    private int[] calculatePositions(int size) {
        int[] positions = new int[size];
        for (int i = 0; i < size; i++) {
            positions[i] = i * 4;
        }
        return positions;
    }

    @Override
    public IPage<GoodsSkuVO> getGoodsSkuByPromotionFlag(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getGoodsSkuByPromotionFlag(PageUtil.initPage(searchParams),searchParams.queryGoodsSkuByPromotionFlagWrapper());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        //校验关联商品是否存在
        List<DdgParentsAssignGoodsSku> ddgParentsAssignGoodsList = this.baseMapper.selectList(new QueryWrapper<DdgParentsAssignGoodsSku>()
                .eq("child_id", ddgParentsAssignGoodsSkuVO.getChildId())
                .eq("parent_id", ddgParentsAssignGoodsSkuVO.getParentId())
                .eq("goods_sku_id", ddgParentsAssignGoodsSkuVO.getGoodsSkuId())
        );
        if (ddgParentsAssignGoodsList.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_GOODS_NULL_ERROR);
        }
        return this.removeById(ddgParentsAssignGoodsList.get(0));
    }

    /**
     * 通过订单编号关联分配儿童的商品信息
     *
     * @param orderSn
     * @return 操作状态
     */
    @Override
    public DdgParentsAssignGoodsSku getAssignGoodsSkuByOrderSn(String orderSn) {
        return baseMapper.getAssignGoodsSkuByOrderSn(orderSn);
    }
}
