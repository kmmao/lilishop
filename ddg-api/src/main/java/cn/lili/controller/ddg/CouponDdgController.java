package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildCollectVO;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgChildCollectService;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.promotion.entity.dos.Coupon;
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
    private DdgParentsAssignGoodsSkuService ddgParentsAssignGoodsSkuService;

    @Autowired
    private DdgChildApplyBuyService ddgChildApplyBuyService;

    @Autowired
    private DdgChildUnionCouponService ddgChildUnionCouponService;

    @Autowired
    private DdgChildCollectService ddgChildCollectService;

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

    @ApiOperation(value = "下单最优优惠券选择")
    @GetMapping("/getBestCouponByDdg")
    public ResultMessage<Coupon> getBestCouponByDdg(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildUnionCouponService.getBestCouponByDdg(searchParams));
    }

}
