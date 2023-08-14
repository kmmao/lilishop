package cn.lili.ddg.test.cart;

import cn.lili.common.enums.ResultUtil;
import cn.lili.common.security.AuthUser;
import cn.lili.common.security.context.UserContext;
import cn.lili.modules.order.order.entity.dto.OrderSearchParams;
import cn.lili.modules.order.order.entity.vo.OrderSimpleVO;
import cn.lili.modules.order.order.service.OrderService;
import cn.lili.modules.promotion.service.MemberCouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static cn.lili.common.enums.ResultCode.ORDER_ONLY_ONE;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author paulG
 * @since 2020/11/27
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberCouponTest {

    @Autowired
    private MemberCouponService memberCouponService;

    /**
     * 订单
     */
    @Autowired
    private OrderService orderService;

    @Test
    void receiveCoupon() {
        memberCouponService.receiveCoupon("1333318596239843328", "1326834797335306240", "1");
        assertTrue(true);
    }

    @Test
    void add(){
        String memberId = "1681129145397219328";
        String skuId = "1661268707559743490";
//        AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
        OrderSearchParams orderSearchParams = new OrderSearchParams();
        orderSearchParams.setMemberId(memberId);
        orderSearchParams.setPageSize(10000);
        orderSearchParams.setPayStatus("PAID");
        List<OrderSimpleVO> orderSimpleVOList = orderService.queryByParams(orderSearchParams).getRecords();
        AtomicBoolean isOderOnlyOne = new AtomicBoolean(false);
        if("1661268707559743490".equals(skuId)){
            if(orderSimpleVOList != null && orderSimpleVOList.size()>0){
                orderSimpleVOList.forEach(item->{
                    item.getOrderItems().forEach(orderItem->{
                        if(skuId.equals(orderItem.getSkuId())){
                            isOderOnlyOne.set(true);
                        }
                    });
                });
            }
        }
        System.out.println(isOderOnlyOne.get());
    }


}
