package cn.lili.modules.order.order.service;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderStatusVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void queryOrderStatus() {
        OrderSearchParams orderSearchParams = new OrderSearchParams();
        orderSearchParams.setMemberId("1579851144765788160");
        OrderStatusVO orderStatusVO = orderService.queryOrderStatus(orderSearchParams);
        System.out.println(JSONUtil.toJsonStr(orderStatusVO));
    }
}