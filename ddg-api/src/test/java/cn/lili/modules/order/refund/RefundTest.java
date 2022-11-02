package cn.lili.modules.order.refund;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.lili.common.event.TransactionCommitSendMQEvent;
import cn.lili.common.properties.RocketmqCustomProperties;
import cn.lili.modules.order.order.entity.dos.Order;
import cn.lili.modules.order.order.service.OrderService;
import cn.lili.rocketmq.tags.OrderTagsEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
public class RefundTest {

    /**
     * 联合登陆
     */
    @Autowired
    private OrderService orderService;
    /**
     * RocketMQ配置
     */
    @Autowired
    private RocketmqCustomProperties rocketmqCustomProperties;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 测试发送退款MQ
     */
    @Test
    public void testSendRefundMq(){
        // TODO 发送订单退款成功消息到嘟嘟罐MQ
        Order order = orderService.getById("1587762800125767682");
        if (ObjectUtil.isNotEmpty(order)) {
            applicationEventPublisher.publishEvent(new TransactionCommitSendMQEvent("发送订单退款成功消息到嘟嘟罐MQ", rocketmqCustomProperties.getOrderDdgRefundTopic(), OrderTagsEnum.STATUS_CHANGE.name(), JSONUtil.toJsonStr(order)));
            System.out.println("发送完成");
        }
    }

    /**
     * 测试发送支付成功MQ
     */
    @Test
    public void testSendPaySuccessMq(){
        // TODO 发送订单退款成功消息到嘟嘟罐MQ
        Order order = orderService.getById("1587762800125767682");
        if (ObjectUtil.isNotEmpty(order)) {
            // TODO 发送订单支付成功消息到嘟嘟罐MQ
            applicationEventPublisher.publishEvent(new TransactionCommitSendMQEvent("发送订单支付成功消息到嘟嘟罐MQ", rocketmqCustomProperties.getOrderDdgTopic(), OrderTagsEnum.STATUS_CHANGE.name(), JSONUtil.toJsonStr(order)));
            System.out.println("发送完成");
        }
    }
}
