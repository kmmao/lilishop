package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.promotion.entity.dos.Coupon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Allen
 * @since 2022/10/20 6:03 下午
 */
@Slf4j
@RestController
@Api(tags = "嘟嘟罐端,商品接口")
@RequestMapping("/ddg/goods")
public class GoodsDdgController {

    @Autowired
    private DdgParentsAssignGoodsSkuService ddgParentsAssignGoodsSkuService;

    @Autowired
    private DdgChildApplyBuyService ddgChildApplyBuyService;

    @Autowired
    private DdgChildUnionCouponService ddgChildUnionCouponService;

    @ApiOperation(value = "商品分配接口")
    @PostMapping("/assignGoodsSku")
    public ResultMessage<Object> assignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.assignGoodsSku(ddgParentsAssignGoodsSkuVO));
    }

    @ApiOperation(value = "通过儿童id获取商品分页列表")
    @GetMapping("/getGoodsSkuByChildId")
    public ResultMessage<IPage<GoodsSku>> getGoodsSkuByChildId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.goodsSkuPageByChildId(searchParams));
    }

    @ApiOperation(value = "儿童申请采购接口")
    @PostMapping("/childApplyBuy")
    public ResultMessage<Object> childApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO) {
        return ResultUtil.data(ddgChildApplyBuyService.childApplyBuy(ddgChildApplyBuyVO));
    }

    @ApiOperation(value = "通过家长id获取儿童采购申请分页列表")
    @GetMapping("/getChildApplyBuyByParentId")
    public ResultMessage<IPage<DdgChildApplyBuy>> getChildApplyBuyByParentId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildApplyBuyService.getChildApplyBuyByParentId(searchParams));
    }

    @ApiOperation(value = "儿童优惠券关系接口")
    @PostMapping("/childUnionCoupon")
    public ResultMessage<Object> childUnionCoupon(DdgChildUnionCouponVO ddgChildUnionCouponVO) {
        return ResultUtil.data(ddgChildUnionCouponService.childUnionCoupon(ddgChildUnionCouponVO));
    }

    @ApiOperation(value = "通过儿童id获取儿童已领取的优惠券分页列表")
    @GetMapping("/getCouponByChildId")
    public ResultMessage<IPage<Coupon>> getCouponByChildId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildUnionCouponService.getCouponByChildId(searchParams));
    }

}
