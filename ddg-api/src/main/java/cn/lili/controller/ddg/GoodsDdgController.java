package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildCollectVO;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgChildCollectService;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private DdgChildCollectService ddgChildCollectService;

    @ApiOperation(value = "商品分配接口")
    @PostMapping("/addAssignGoodsSku")
    public ResultMessage<Object> addAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.addAssignGoodsSku(ddgParentsAssignGoodsSkuVO));
    }

    @ApiOperation(value = "商品取消分配接口")
    @PostMapping("/cancelAssignGoodsSku")
    public ResultMessage<Object> cancelAssignGoodsSku(DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.cancelAssignGoodsSku(ddgParentsAssignGoodsSkuVO));
    }

    @ApiOperation(value = "通过订单编号关联分配儿童的商品信息")
    @GetMapping("/getAssignGoodsSkuByOrderSn")
    public ResultMessage<Object> getGoodsSkuByChildIdFormAssign(String orderSn) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.getAssignGoodsSkuByOrderSn(orderSn));
    }

    @ApiOperation(value = "通过儿童id获取商品分页列表")
    @GetMapping("/getGoodsSkuByChildIdFormAssign")
    public ResultMessage<IPage<GoodsSkuVO>> getGoodsSkuByChildIdFormAssign(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgParentsAssignGoodsSkuService.getGoodsSkuByChildIdFormAssign(searchParams));
    }

    @ApiOperation(value = "儿童申请采购接口")
    @PostMapping("/addChildApplyBuy")
    public ResultMessage<DdgChildApplyBuy> addChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO) {
        return ResultUtil.data(ddgChildApplyBuyService.addChildApplyBuy(ddgChildApplyBuyVO));
    }

    @ApiOperation(value = "通过儿童申请采购ID获取商品SKU信息接口")
    @PostMapping("/getGoodsSkuByChildApplyBuy")
    public ResultMessage<GoodsSkuVO> getGoodsSkuByChildApplyBuy(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildApplyBuyService.getGoodsSkuByChildApplyBuy(searchParams));
    }

    @ApiOperation(value = "儿童取消采购接口")
    @PostMapping("/cancelChildApplyBuy")
    public ResultMessage<Object> cancelChildApplyBuy(DdgChildApplyBuyVO ddgChildApplyBuyVO) {
        return ResultUtil.data(ddgChildApplyBuyService.cancelChildApplyBuy(ddgChildApplyBuyVO));
    }

    @ApiOperation(value = "通过儿童id获取儿童采购申请分页列表")
    @GetMapping("/getChildApplyBuyByChildId")
    public ResultMessage<IPage<DdgChildApplyBuyVO>> getChildApplyBuyByChildId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildApplyBuyService.getChildApplyBuyByChildId(searchParams));
    }

    @ApiOperation(value = "通过家长id获取儿童采购申请分页列表")
    @GetMapping("/getChildApplyBuyByParentId")
    public ResultMessage<IPage<DdgChildApplyBuyVO>> getChildApplyBuyByParentId(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildApplyBuyService.getChildApplyBuyByParentId(searchParams));
    }

    @ApiOperation(value = "通过家长id获取儿童采购申请分页列表")
    @GetMapping("/getChildApplyBuy")
    public ResultMessage<List<DdgChildApplyBuyVO>> getChildApplyBuy(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildApplyBuyService.getChildApplyBuy(searchParams));
    }

    @ApiOperation(value = "儿童收藏信息接口")
    @PostMapping("/addChildCollect")
    public ResultMessage<Object> addChildCollect(DdgChildCollectVO ddgChildCollectVO) {
        return ResultUtil.data(ddgChildCollectService.addChildCollect(ddgChildCollectVO));
    }

    @ApiOperation(value = "取消儿童收藏信息接口")
    @PostMapping("/cancelChildCollect")
    public ResultMessage<Object> cancelChildCollect(DdgChildCollectVO ddgChildCollectVO) {
        return ResultUtil.data(ddgChildCollectService.cancelChildCollect(ddgChildCollectVO));
    }

    @ApiOperation(value = "儿童是否收藏商品信息接口")
    @PostMapping("/isHaveCollectByChildAndSkuId")
    public ResultMessage<Object> isHaveCollectByChildAndSkuId(DdgChildCollectVO ddgChildCollectVO) {
        return ResultUtil.data(ddgChildCollectService.isHaveCollectByChildAndSkuId(ddgChildCollectVO));
    }

    @ApiOperation(value = "通过儿童id获取儿童收藏信息分页列表")
    @GetMapping("/getGoodsSkuByChildIdFormCollect")
    public ResultMessage<IPage<GoodsSku>> getGoodsSkuByChildIdFormCollect(GoodsDdgSearchParams searchParams) {
        return ResultUtil.data(ddgChildCollectService.getGoodsSkuByChildIdFormCollect(searchParams));
    }

}
