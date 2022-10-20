package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgChildUnionCoupon;
import cn.lili.modules.promotion.entity.dos.Coupon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;

/**
 * 嘟嘟罐儿童优惠券关系表相关联数据处理层
 *
 * @author Allen
 * @since 2022-10-20 18:25:16
 */
public interface DdgChildUnionCouponMapper extends BaseMapper<DdgChildUnionCoupon> {

    /**
     * 通过儿童id获取儿童已领取的优惠券分页列表
     * @return
     */
    @Select("SELECT c.* FROM li_coupon c WHERE c.id IN(SELECT coupon_id FROM ddg_child_union_coupon WHERE child_id=${childId}) ${ew.customSqlSegment}")
    IPage<Coupon> getCouponByChildId(Page<Coupon> initPage, QueryWrapper<Coupon> queryCouponWrapper, String childId);
}