package cn.lili.modules.order.order.service;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.ddg.entity.dos.DdgParentsAssignGoodsSku;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderStatusVO;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DdgParentsAssignGoodsSkuService ddgParentsAssignGoodsSkuService;

    @Test
    void queryOrderStatus() {
        OrderSearchParams orderSearchParams = new OrderSearchParams();
        orderSearchParams.setMemberId("1579851144765788160");
        OrderStatusVO orderStatusVO = orderService.queryOrderStatus(orderSearchParams);
        System.out.println(JSONUtil.toJsonStr(orderStatusVO));
    }

    @Test
    void getAssignGoodsSkuByOrderSn(){
        DdgParentsAssignGoodsSku sku = ddgParentsAssignGoodsSkuService.getAssignGoodsSkuByOrderSn("O202211091590233297833967617");
        System.out.println(JSONObject.toJSONString(sku));
    }
}