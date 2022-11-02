package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.core.text.CharSequenceUtil;
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
import cn.lili.modules.promotion.entity.enums.PromotionsScopeTypeEnum;
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
        memberCouponService.receiveBuyerCoupon(ddgChildUnionCouponVO.getCouponId(), member.getId(), member.getNickName(),ddgChildUnionCouponVO.getChildId());
        //参数封装
        DdgChildUnionCoupon ddgChildUnionCoupon = new DdgChildUnionCoupon();
        BeanUtil.copyProperties(ddgChildUnionCouponVO, ddgChildUnionCoupon);
        return this.baseMapper.insert(ddgChildUnionCoupon) > 0;
    }

    @Override
    public IPage<Coupon> getCouponByChildId(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getCouponByChildId(PageUtil.initPage(searchParams), searchParams.queryCouponWrapper(), searchParams.getChildId());
    }

    @Override
    public MemberCoupon getBestCouponByDdg(GoodsDdgSearchParams searchParams) {
        String memberIdByDdgId = memberService.getMemberIdByDdgId(searchParams.getParentId());
        // 判断最优解，然后返回
        List<MemberCoupon> memberCouponList = memberCouponService.getMemberCoupons(memberIdByDdgId);

        //获取最新优惠券
        memberCouponList = memberCouponList.stream()
                .filter(item -> item.getStartTime().before(new Date()) && item.getEndTime().after(new Date()))
                .collect(Collectors.toList());

        if (memberCouponList.isEmpty()) {
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
        MemberCoupon returnMemberCoupon = new MemberCoupon();
        memberCouponList.forEach(memberCoupon -> available(tradeDTO, memberCoupon, searchParams.getFinalePrice(), returnMemberCoupon));
        return memberCouponList.get(0);
    }

    /**
     * 判定优惠券是否可用
     *
     * @param tradeDTO           交易dto
     * @param finalePrice        最终成交金额，未进行优惠券渲染
     * @param returnMemberCoupon
     */
    private void available(TradeDTO tradeDTO, MemberCoupon memberCoupon, Double finalePrice, MemberCoupon returnMemberCoupon) {

        List<CartSkuVO> filterSku = filterSkuVo(tradeDTO.getSkuList(), memberCoupon);
        if (filterSku == null || filterSku.isEmpty()) {
            return;
        }

        //满足条件判定
        if (finalePrice >= memberCoupon.getConsumeThreshold()) {
            Double discountCouponPrice = 0D;
            if (memberCoupon.getCouponType().equals(CouponTypeEnum.PRICE.name())) {
                discountCouponPrice = CurrencyUtil.sub(finalePrice, memberCoupon.getPrice());
            } else {
                // 打折金额=商品金额*折扣/10
                discountCouponPrice = CurrencyUtil.mul(finalePrice,
                        CurrencyUtil.sub(1, CurrencyUtil.div(memberCoupon.getDiscount(), 10, 3)));
            }

            if (tradeDTO.getPriceDetailVO().getDiscountPrice() == 0D) {
                tradeDTO.getPriceDetailVO().setDiscountPrice(discountCouponPrice);
                BeanUtil.copyProperties(memberCoupon, returnMemberCoupon);
            } else if (tradeDTO.getPriceDetailVO().getDiscountPrice() >= discountCouponPrice) {
                tradeDTO.getPriceDetailVO().setDiscountPrice(discountCouponPrice);
                BeanUtil.copyProperties(memberCoupon, returnMemberCoupon);
            }
        }

    }

    /**
     * 过滤购物车商品信息，按照优惠券的适用范围过滤
     *
     * @param cartSkuVOS   购物车中的产品列表
     * @param memberCoupon 会员优惠券
     * @return 按照优惠券的适用范围过滤的购物车商品信息
     */
    private List<CartSkuVO> filterSkuVo(List<CartSkuVO> cartSkuVOS, MemberCoupon memberCoupon) {

        List<CartSkuVO> filterSku = Collections.emptyList();
        //平台店铺过滤
        if (Boolean.TRUE.equals(memberCoupon.getPlatformFlag())) {
            filterSku = cartSkuVOS;
        }
        if (filterSku == null || filterSku.isEmpty()) {
            return Collections.emptyList();
        }
        //优惠券类型判定
        switch (PromotionsScopeTypeEnum.valueOf(memberCoupon.getScopeType())) {
            case ALL:
                return filterSku;
            case PORTION_GOODS:
                //按照商品过滤
                filterSku = filterSku.stream().filter(cartSkuVO -> memberCoupon.getScopeId().contains(cartSkuVO.getGoodsSku().getId())).collect(Collectors.toList());
                break;

            case PORTION_SHOP_CATEGORY:
                //按照店铺分类过滤
                filterSku = this.filterPromotionShopCategory(filterSku, memberCoupon);
                break;

            case PORTION_GOODS_CATEGORY:

                //按照店铺分类过滤
                filterSku = filterSku.stream().filter(cartSkuVO -> {
                    //平台分类获取
                    String[] categoryPath = cartSkuVO.getGoodsSku().getCategoryPath().split(",");
                    //平台三级分类
                    String categoryId = categoryPath[categoryPath.length - 1];
                    return memberCoupon.getScopeId().contains(categoryId);
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
     * @param memberCoupon 会员优惠
     * @return 优惠券按照店铺分类过滤的购物车商品信息
     */
    private List<CartSkuVO> filterPromotionShopCategory(List<CartSkuVO> filterSku, MemberCoupon memberCoupon) {
        return filterSku.stream().filter(cartSkuVO -> {
            if (CharSequenceUtil.isNotEmpty(cartSkuVO.getGoodsSku().getStoreCategoryPath())) {
                //获取店铺分类
                String[] storeCategoryPath = cartSkuVO.getGoodsSku().getStoreCategoryPath().split(",");
                for (String category : storeCategoryPath) {
                    //店铺分类只要有一项吻合，即可返回true
                    if (memberCoupon.getScopeId().contains(category)) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
}
