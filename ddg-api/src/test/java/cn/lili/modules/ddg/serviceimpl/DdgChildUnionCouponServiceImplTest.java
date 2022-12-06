package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.modules.promotion.entity.dos.MemberCoupon;
import cn.lili.modules.promotion.entity.enums.MemberCouponStatusEnum;
import cn.lili.modules.promotion.entity.vos.MemberCouponVO;
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
        searchParams.setParentId("1535182572286844929");
        searchParams.setMemberCouponStatus(MemberCouponStatusEnum.NEW.name());
        IPage<MemberCouponVO> couponByChildId = ddgChildUnionCouponService.getCouponByChildId(searchParams);
        System.out.println(JSONUtil.toJsonStr(couponByChildId));
    }

    @Test
    void getBestCouponByDdg() {
        GoodsDdgSearchParams goodsDdgSearchParams = new GoodsDdgSearchParams();
        goodsDdgSearchParams.setGoodsSkuId("1588758591231217667");
        goodsDdgSearchParams.setChildId("1595675783827861505");
        goodsDdgSearchParams.setParentId("1534070009595609089");
        goodsDdgSearchParams.setMemberCouponStatus(MemberCouponStatusEnum.NEW.name());
        goodsDdgSearchParams.setFinalePrice(30D);
        MemberCouponVO bestCouponByDdg = ddgChildUnionCouponService.getBestCouponByDdg(goodsDdgSearchParams);
        System.out.println(JSONUtil.toJsonStr(bestCouponByDdg));
    }

}