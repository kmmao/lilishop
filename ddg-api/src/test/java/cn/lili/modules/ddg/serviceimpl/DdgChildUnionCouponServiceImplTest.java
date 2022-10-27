package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.promotion.entity.dos.Coupon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DdgChildUnionCouponServiceImplTest {

    @Autowired
    private DdgChildUnionCouponService ddgChildUnionCouponService;

    @Test
    void getCouponByChildId() {
        GoodsDdgSearchParams searchParams = new GoodsDdgSearchParams();
        searchParams.setChildId("1535195481096314881");
        IPage<Coupon> couponByChildId = ddgChildUnionCouponService.getCouponByChildId(searchParams);
        System.out.println(JSONUtil.toJsonStr(couponByChildId));
    }
}