package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.modules.promotion.entity.dos.MemberCoupon;
import cn.lili.modules.promotion.entity.dto.search.CouponSearchParams;
import cn.lili.modules.promotion.entity.enums.CouponGetEnum;
import cn.lili.modules.promotion.entity.enums.PromotionsStatusEnum;
import cn.lili.modules.promotion.entity.vos.CouponVO;
import cn.lili.modules.promotion.service.CouponService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Allen
 * @since 2022/10/20 6:03 下午
 */
@Slf4j
@RestController
@Api(tags = "嘟嘟罐端,优惠券接口")
@RequestMapping("/ddg/coupon")
public class CouponDdgController {

    @Autowired
    private DdgChildUnionCouponService ddgChildUnionCouponService;

    @Autowired
    private CouponService couponService;

    @GetMapping("/getCouponListByPlatform")
    @ApiOperation(value = "获取可领取平台优惠券列表（嘟嘟罐）")
    public ResultMessage<IPage<CouponVO>> getCouponListByPlatform(CouponSearchParams queryParam, PageVO page) {
        queryParam.setPromotionStatus(PromotionsStatusEnum.START.name());
        queryParam.setGetType(CouponGetEnum.FREE.name());
        queryParam.setStoreId("0");
        IPage<CouponVO> canUseCoupons = couponService.pageVOFindAll(queryParam, page);
        return ResultUtil.data(canUseCoupons);
    }

    @ApiOperation(value = "儿童优惠券关系接口")
    @PostMapping("/addChildUnionCoupon")
    public ResultMessage<Object> addChildUnionCoupon(DdgChildUnionCouponVO ddgChildUnionCouponVO) {
        return ResultUtil.data(ddgChildUnionCouponService.addChildUnionCoupon(ddgChildUnionCouponVO));
    }

    @ApiOperation(value = "通过儿童id获取儿童已领取的优惠券分页列表")
    @GetMapping("/getCouponByChildId")
    public ResultMessage<IPage<Coupon>> getCouponByChildId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildUnionCouponService.getCouponByChildId(searchParams));
    }

    /**
     * 需要转childId，parentId，goodsSkuId，finalePrice
     * @param searchParams
     * @return
     */
    @ApiOperation(value = "下单最优优惠券选择")
    @GetMapping("/getBestCouponByDdg")
    public ResultMessage<MemberCoupon> getBestCouponByDdg(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildUnionCouponService.getBestCouponByDdg(searchParams));
    }

}
