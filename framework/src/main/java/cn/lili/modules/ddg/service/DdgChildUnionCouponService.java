package cn.lili.modules.ddg.service;

import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgChildUnionCoupon;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.promotion.entity.dos.Coupon;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 嘟嘟罐儿童优惠券关系表相关联业务层
 *
 * @author Allen
 * @since 2022-10-20 18:15:43
 */
public interface DdgChildUnionCouponService extends IService<DdgChildUnionCoupon> {

    /**
     * 添加嘟嘟罐儿童优惠券关系表
     * @return 操作状态
     */
    Boolean childUnionCoupon(DdgChildUnionCouponVO ddgChildUnionCouponVO);

    /**
     * 通过儿童id获取儿童已领取的优惠券分页列表
     * @return
     */
    IPage<Coupon> getCouponByChildId(GoodsDdgSearchParams searchParams);
}
