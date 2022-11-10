package cn.lili.modules.order.order.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.entity.vo.OrderStatusVO;
import cn.lili.modules.order.order.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    @Test
    void queryByParams() {
        OrderSearchParams orderSearchParams = new OrderSearchParams();
        orderSearchParams.setChildId("0");
        orderSearchParams.setMemberId("1588878973916553216");
        orderSearchParams.setTag("WAIT_ROG");
        IPage<OrderSimpleVO> orderSimpleVOIPage = orderService.queryByParams(orderSearchParams);
        System.out.println(JSONUtil.toJsonStr(orderSimpleVOIPage));

    }
}