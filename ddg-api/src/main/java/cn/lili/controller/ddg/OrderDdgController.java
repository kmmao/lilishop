package cn.lili.controller.ddg;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.entity.vo.OrderStatusVO;
import cn.lili.modules.order.order.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Allen
 * @since 2022/10/20 6:03 下午
 */
@Slf4j
@RestController
@Api(tags = "嘟嘟罐端,订单接口")
@RequestMapping("/ddg/order")
public class OrderDdgController {

    /**
     * 订单
     */
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "通过家长id查询会员订单列表")
    @GetMapping(value = "/queryMineOrderByParentId/{parentId}")
    public ResultMessage<IPage<OrderSimpleVO>> queryMineOrderByParentId(
            @NotNull(message = "家长ID不能为空") @PathVariable("parentId") String parentId,OrderSearchParams orderSearchParams) {
        orderSearchParams.setMemberId(parentId);
        orderSearchParams.setChildId("0");
        return ResultUtil.data(orderService.queryByParams(orderSearchParams));
    }

    @ApiOperation(value = "通过儿童id查询会员订单列表")
    @GetMapping(value = "/queryMineOrderByChildId/{childId}")
    public ResultMessage<IPage<OrderSimpleVO>> queryMineOrderByChildId(
            @NotNull(message = "儿童ID不能为空") @PathVariable("childId") String childId,OrderSearchParams orderSearchParams) {
        orderSearchParams.setChildId(childId);
        return ResultUtil.data(orderService.queryByParams(orderSearchParams));
    }

    @ApiOperation(value = "通过家长id查询会员订单状态信息")
    @GetMapping(value = "/queryOrderStatusByParentId/{parentId}")
    public ResultMessage<OrderStatusVO> queryOrderStatusByParentId(
            @NotNull(message = "家长ID不能为空") @PathVariable("parentId") String parentId,OrderSearchParams orderSearchParams) {
        orderSearchParams.setMemberId(parentId);
        orderSearchParams.setChildId("0");
        return ResultUtil.data(orderService.queryOrderStatus(orderSearchParams));
    }

    @ApiOperation(value = "通过儿童id查询会员订单状态信息")
    @GetMapping(value = "/queryOrderStatusByChildIdId/{childId}")
    public ResultMessage<OrderStatusVO> queryOrderStatusByChildIdId(
            @NotNull(message = "儿童ID不能为空") @PathVariable("childId") String childId,OrderSearchParams orderSearchParams) {
        orderSearchParams.setChildId(childId);
        return ResultUtil.data(orderService.queryOrderStatus(orderSearchParams));
    }

}
