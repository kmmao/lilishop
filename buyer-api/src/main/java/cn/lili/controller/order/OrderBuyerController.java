package cn.lili.controller.order;

import cn.hutool.json.JSONUtil;
import cn.lili.common.aop.annotation.PreventDuplicateSubmissions;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.enums.ResultUtil;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.security.AuthUser;
import cn.lili.common.security.OperationalJudgment;
import cn.lili.common.security.context.UserContext;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.order.order.entity.dos.OrderPackage;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.enums.OrderStatusEnum;
import cn.lili.modules.order.order.entity.vo.OrderDetailVO;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.service.OrderPackageService;
import cn.lili.modules.order.order.service.OrderService;
import cn.lili.modules.system.entity.dos.Setting;
import cn.lili.modules.system.entity.dto.OrderSetting;
import cn.lili.modules.system.entity.enums.SettingEnum;
import cn.lili.modules.system.service.LogisticsService;
import cn.lili.modules.system.service.SettingService;
import cn.lili.modules.system.entity.vo.Traces;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 买家端,订单接口
 *
 * @author Chopper
 * @since 2020/11/16 10:08 下午
 */
@RestController
@Api(tags = "买家端,订单接口")
@RequestMapping("/buyer/order/order")
public class OrderBuyerController {

    /**
     * 订单
     */
    @Autowired
    private OrderService orderService;

    @Autowired
    private LogisticsService logisticsService;

    /**
     * 设置
     */
    @Autowired
    private SettingService settingService;
    private OrderPackageService orderPackageService;

    @ApiOperation(value = "查询会员订单列表")
    @GetMapping
    public ResultMessage<IPage<OrderSimpleVO>> queryMineOrder(OrderSearchParams orderSearchParams) {
        AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
        orderSearchParams.setMemberId(currentUser.getId());
        return ResultUtil.data(orderService.queryByParams(orderSearchParams));
    }

    @ApiOperation(value = "订单明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, paramType = "path")
    })
    @GetMapping(value = "/{orderSn}")
    public ResultMessage<OrderDetailVO> detail(@NotNull(message = "订单编号不能为空") @PathVariable("orderSn") String orderSn) {
        OrderDetailVO orderDetailVO = orderService.queryDetail(orderSn);
        OperationalJudgment.judgment(orderDetailVO.getOrder());
        return ResultUtil.data(orderDetailVO);
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, paramType = "path")
    })
    @PostMapping(value = "/{orderSn}/receiving")
    public ResultMessage<Object> receiving(@NotNull(message = "订单编号不能为空") @PathVariable("orderSn") String orderSn) {
        Order order = orderService.getBySn(orderSn);
        if (order == null) {
            throw new ServiceException(ResultCode.ORDER_NOT_EXIST);
        }
        //判定是否是待收货状态
        if (!order.getOrderStatus().equals(OrderStatusEnum.DELIVERED.name())) {
            throw new ServiceException(ResultCode.ORDER_DELIVERED_ERROR);
        }
        orderService.complete(orderSn);
        return ResultUtil.success();
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "reason", value = "取消原因", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/{orderSn}/cancel")
    public ResultMessage<Object> cancel(@ApiIgnore @PathVariable String orderSn, @RequestParam String reason) {
        orderService.cancel(orderSn, reason);
        return ResultUtil.success();
    }

    @PreventDuplicateSubmissions
    @ApiOperation(value = "删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping(value = "/{orderSn}")
    public ResultMessage<Object> deleteOrder(@PathVariable String orderSn) {
        OperationalJudgment.judgment(orderService.getBySn(orderSn));
        orderService.deleteOrder(orderSn);
        return ResultUtil.success();
    }

    @ApiOperation(value = "查询物流踪迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/getTraces/{orderSn}")
    public ResultMessage<Object> getTraces(@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
        OperationalJudgment.judgment(orderService.getBySn(orderSn));
        return ResultUtil.data(orderService.getTraces(orderSn));
    }

//    @ApiOperation(value = "查询物流踪迹通过物流code及物流名称")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "logisticsCode", value = "物流公司编码", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "logisticsName", value = "物流公司名称", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "logisticsNo", value = "物流单号", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "customerName", value = "手机号", required = true, dataType = "String", paramType = "query")
//    })
//    @GetMapping(value = "/getTracesByCodeAndName")
//    public ResultMessage<Object> getTracesByCodeAndName(@RequestParam String logisticsCode, @RequestParam String logisticsName, @RequestParam String logisticsNo, @RequestParam String customerName) {
//        return ResultUtil.data(logisticsService.getLogisticByCodeAndName(logisticsCode,logisticsName,logisticsNo,customerName.substring(customerName.length() - 4)));
//    }

    @ApiOperation(value = "查询订单自动取消时间")
    @GetMapping(value = "/getOrderAutoCancel")
    public ResultMessage<Object> getOrderAutoCancel() {
        Setting setting = settingService.get(SettingEnum.ORDER_SETTING.name());
        OrderSetting orderSetting = JSONUtil.toBean(setting.getSettingValue(), OrderSetting.class);
        return ResultUtil.data(orderSetting.getAutoCancel());
    }

    @ApiOperation(value = "查询地图版物流踪迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/getMapTraces/{orderSn}")
    public ResultMessage<Object> getMapTraces(@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
        OperationalJudgment.judgment(orderService.getBySn(orderSn));
        return ResultUtil.data(orderService.getMapTraces(orderSn));
    }


    @PreventDuplicateSubmissions
    @ApiOperation(value = "开票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping(value = "/receipt/{orderSn}")
    public ResultMessage<Object> invoice(@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
        OperationalJudgment.judgment(orderService.getBySn(orderSn));
        return ResultUtil.data(orderService.invoice(orderSn));
    }

    @ApiOperation(value = "查询物流踪迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/getTracesList/{orderSn}")
    public ResultMessage<Object> getTracesList(@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
        return ResultUtil.data(orderPackageService.getOrderPackageVOList(orderSn));
    }

    @ApiOperation(value = "查看包裹列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderSn", value = "订单编号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/getPackage/{orderSn}")
    public ResultMessage<Object> getPackage(@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
        return ResultUtil.data(orderPackageService.getOrderPackageVOList(orderSn));
    }
}
