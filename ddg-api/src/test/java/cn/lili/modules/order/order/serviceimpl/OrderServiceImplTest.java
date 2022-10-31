package cn.lili.modules.order.order.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderStatusVO;
import cn.lili.modules.order.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    void queryOrderStatus() {
        OrderSearchParams orderSearchParams = new OrderSearchParams();
        orderSearchParams.setChildId("0");
        OrderStatusVO orderStatusVO = orderService.queryOrderStatus(orderSearchParams);
        System.out.println(JSONUtil.toJsonStr(orderStatusVO));
    }
}