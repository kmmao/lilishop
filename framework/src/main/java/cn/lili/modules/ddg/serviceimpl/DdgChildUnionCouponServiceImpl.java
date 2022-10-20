package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.BeanUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dos.DdgChildUnionCoupon;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.entity.vo.DdgChildUnionCouponVO;
import cn.lili.modules.ddg.mapper.DdgChildApplyBuyMapper;
import cn.lili.modules.ddg.mapper.DdgChildUnionCouponMapper;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.ddg.service.DdgChildUnionCouponService;
import cn.lili.modules.promotion.entity.dos.Coupon;
import cn.lili.mybatis.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 嘟嘟罐儿童优惠券关系表相关联接口业务层实现
 *
 * @author Allen
 * @date 2022-10-20 18:23:43
 */
@Service
public class DdgChildUnionCouponServiceImpl extends ServiceImpl<DdgChildUnionCouponMapper, DdgChildUnionCoupon> implements DdgChildUnionCouponService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean childUnionCoupon(DdgChildUnionCouponVO ddgChildUnionCouponVO) {
        //校验关联优惠券是否重复
        List<DdgChildUnionCoupon> ddgChildUnionCoupons = this.baseMapper.selectList(new QueryWrapper<DdgChildUnionCoupon>()
                .eq("coupon_id", ddgChildUnionCouponVO.getCouponId())
                .eq("child_id", ddgChildUnionCouponVO.getChildId())
                .eq("parent_id", ddgChildUnionCouponVO.getParentId())
        );
        if (!ddgChildUnionCoupons.isEmpty()) {
            throw new ServiceException(ResultCode.DDG_CHILD_UNION_COUPON_REPEAT_ERROR);
        }
        //参数封装
        DdgChildUnionCoupon ddgChildUnionCoupon = new DdgChildUnionCoupon();
        BeanUtil.copyProperties(ddgChildUnionCouponVO, ddgChildUnionCoupon);
        return this.baseMapper.insert(ddgChildUnionCoupon) > 0;
    }

    @Override
    public IPage<Coupon> getCouponByChildId(GoodsDdgSearchParams searchParams) {
        return this.baseMapper.getCouponByChildId(PageUtil.initPage(searchParams),searchParams.queryCouponWrapper(),searchParams.getChildId());
    }
}
