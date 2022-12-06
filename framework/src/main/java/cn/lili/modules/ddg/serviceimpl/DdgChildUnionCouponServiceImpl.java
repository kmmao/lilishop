package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.common.utils.CurrencyUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildUnionCoupon;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.ddg.mapper.DdgChildUnionCouponMapper;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.service.GoodsSkuService;
import cn.lili.modules.member.entity.dos.Member;
import cn.lili.modules.member.service.MemberService;
import cn.lili.modules.order.cart.entity.dto.TradeDTO;
import cn.lili.modules.order.cart.entity.vo.CartSkuVO;
import cn.lili.modules.order.cart.entity.vo.PriceDetailVO;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.modules.promotion.entity.dos.MemberCoupon;
import cn.lili.modules.promotion.entity.enums.CouponTypeEnum;
import cn.lili.modules.promotion.entity.enums.MemberCouponStatusEnum;
import cn.lili.modules.promotion.entity.enums.PromotionsScopeTypeEnum;
import cn.lili.modules.promotion.entity.vos.MemberCouponVO;
import cn.lili.modules.promotion.service.CouponService;
import cn.lili.modules.promotion.service.MemberCouponService;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 嘟嘟罐儿童优惠券关系表相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Service
public class DdgChildUnionCouponServiceImpl extends ServiceImpl<DdgChildUnionCouponMapper, DdgChildUnionCoupon> implements DdgChildUnionCouponService {
    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GoodsSkuService goodsSkuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addChildUnionCoupon(DdgChildUnionCouponVO ddgChildUnionCouponVO) {
        String memberIdByDdgId = memberService.getMemberIdByDdgId(ddgChildUnionCouponVO.getParentId());
        Member member = memberService.getById(memberIdByDdgId);
        DdgChildUnionCoupon ddgChildUnionCoupon = new DdgChildUnionCoupon();
        BeanUtil.copyProperties(ddgChildUnionCouponVO, ddgChildUnionCoupon);
        memberCouponService.receiveBuyerCoupon(ddgChildUnionCouponVO.getCouponId(), member.getId(), member.getNickName(), ddgChildUnionCoupon);
        return StrUtil.isEmpty(ddgChildUnionCoupon.getId());
    }

    @Override
    public IPage<MemberCouponVO> getCouponByChildId(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getCouponByChildId(PageUtil.initPage(searchParams), searchParams.queryMemberCouponWrapper());
    }

    @Override
    public MemberCouponVO getBestCouponByDdg(GoodsDdgSearchParams searchParams) {
        String memberIdByDdgId = memberService.getMemberIdByDdgId(searchParams.getParentId());
        searchParams.setMemberCouponStatus(MemberCouponStatusEnum.NEW.name());
        // 判断最优解，然后返回
        List<MemberCouponVO> memberCouponVOList = this.baseMapper.getCouponByChildIdList(PageUtil.initPage(searchParams), searchParams.queryMemberCouponWrapper());

        //获取最新优惠券
        memberCouponVOList = memberCouponVOList.stream()
                .filter(item -> item.getStartTime().before(new Date()) && item.getEndTime().after(new Date()))
                .collect(Collectors.toList());

        if (memberCouponVOList.isEmpty()) {
            return null;
        }
        GoodsSku goodsSkuByIdFromCache = goodsSkuService.getGoodsSkuByIdFromCache(searchParams.getGoodsSkuId());
        TradeDTO tradeDTO = new TradeDTO();
        List<CartSkuVO> cartSkuVOS = new ArrayList<>();
        CartSkuVO cartSkuVO = new CartSkuVO();
        cartSkuVO.setChecked(true);
        cartSkuVO.setStoreId(goodsSkuByIdFromCache.getStoreId());
        cartSkuVO.setGoodsSku(goodsSkuByIdFromCache);
        cartSkuVOS.add(cartSkuVO);
        PriceDetailVO priceDetailVO = new PriceDetailVO();
        tradeDTO.setMemberId(memberIdByDdgId);
        tradeDTO.setSkuList(cartSkuVOS);
        priceDetailVO.setDiscountPrice(0D);
        tradeDTO.setPriceDetailVO(priceDetailVO);
        MemberCouponVO returnMemberCouponVO = new MemberCouponVO();
        memberCouponVOList.forEach(memberCouponVO -> available(tradeDTO, memberCouponVO, searchParams.getFinalePrice(), returnMemberCouponVO));
        return returnMemberCouponVO;
    }

    /**
     * 判定优惠券是否可用
     *
     * @param tradeDTO           交易dto
     * @param finalePrice        最终成交金额，未进行优惠券渲染
     * @param returnMemberCouponVO
     */
    private void available(TradeDTO tradeDTO, MemberCouponVO memberCouponVO, Double finalePrice, MemberCouponVO returnMemberCouponVO) {

        List<CartSkuVO> filterSku = filterSkuVo(tradeDTO.getSkuList(), memberCouponVO);
        if (filterSku == null || filterSku.isEmpty()) {
            return;
        }

        //满足条件判定
        if (finalePrice >= memberCouponVO.getConsumeThreshold()) {
            Double discountCouponPrice = 0D;
            if (memberCouponVO.getCouponType().equals(CouponTypeEnum.PRICE.name())) {
                discountCouponPrice = CurrencyUtil.sub(finalePrice, memberCouponVO.getPrice());
            } else {
                // 打折金额=商品金额*折扣/10
                discountCouponPrice = CurrencyUtil.mul(finalePrice,
                        CurrencyUtil.sub(1, CurrencyUtil.div(memberCouponVO.getDiscount(), 10, 3)));
            }

            if (tradeDTO.getPriceDetailVO().getDiscountPrice() == 0D) {
                tradeDTO.getPriceDetailVO().setDiscountPrice(discountCouponPrice);
                BeanUtil.copyProperties(memberCouponVO, returnMemberCouponVO);
            } else if (tradeDTO.getPriceDetailVO().getDiscountPrice() >= discountCouponPrice) {
                tradeDTO.getPriceDetailVO().setDiscountPrice(discountCouponPrice);
                BeanUtil.copyProperties(memberCouponVO, returnMemberCouponVO);
            }
        }else {
            returnMemberCouponVO = null;
        }

    }

    /**
     * 过滤购物车商品信息，按照优惠券的适用范围过滤
     *
     * @param cartSkuVOS   购物车中的产品列表
     * @param memberCouponVO 会员优惠券
     * @return 按照优惠券的适用范围过滤的购物车商品信息
     */
    private List<CartSkuVO> filterSkuVo(List<CartSkuVO> cartSkuVOS, MemberCouponVO memberCouponVO) {

        List<CartSkuVO> filterSku = Collections.emptyList();
        //平台店铺过滤
        if (Boolean.TRUE.equals(memberCouponVO.getPlatformFlag())) {
            filterSku = cartSkuVOS;
        }
        if (filterSku == null || filterSku.isEmpty()) {
            return Collections.emptyList();
        }
        //优惠券类型判定
        switch (PromotionsScopeTypeEnum.valueOf(memberCouponVO.getScopeType())) {
            case ALL:
                return filterSku;
            case PORTION_GOODS:
                //按照商品过滤
                filterSku = filterSku.stream().filter(cartSkuVO -> memberCouponVO.getScopeId().contains(cartSkuVO.getGoodsSku().getId())).collect(Collectors.toList());
                break;

            case PORTION_SHOP_CATEGORY:
                //按照店铺分类过滤
                filterSku = this.filterPromotionShopCategory(filterSku, memberCouponVO);
                break;

            case PORTION_GOODS_CATEGORY:

                //按照店铺分类过滤
                filterSku = filterSku.stream().filter(cartSkuVO -> {
                    //平台分类获取
                    String[] categoryPath = cartSkuVO.getGoodsSku().getCategoryPath().split(",");
                    //平台三级分类
                    String categoryId = categoryPath[categoryPath.length - 1];
                    return memberCouponVO.getScopeId().contains(categoryId);
                }).collect(Collectors.toList());
                break;
            default:
                return Collections.emptyList();
        }
        return filterSku;
    }

    /**
     * 优惠券按照店铺分类过滤
     *
     * @param filterSku    过滤的购物车商品信息
     * @param memberCouponVO 会员优惠
     * @return 优惠券按照店铺分类过滤的购物车商品信息
     */
    private List<CartSkuVO> filterPromotionShopCategory(List<CartSkuVO> filterSku, MemberCouponVO memberCouponVO) {
        return filterSku.stream().filter(cartSkuVO -> {
            if (CharSequenceUtil.isNotEmpty(cartSkuVO.getGoodsSku().getStoreCategoryPath())) {
                //获取店铺分类
                String[] storeCategoryPath = cartSkuVO.getGoodsSku().getStoreCategoryPath().split(",");
                for (String category : storeCategoryPath) {
                    //店铺分类只要有一项吻合，即可返回true
                    if (memberCouponVO.getScopeId().contains(category)) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
