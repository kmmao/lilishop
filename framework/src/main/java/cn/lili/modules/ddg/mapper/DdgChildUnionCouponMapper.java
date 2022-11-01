package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgChildUnionCoupon;
import cn.lili.modules.promotion.entity.dos.Coupon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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
     *
     * @return
     */
    @Select("SELECT\n" +
            "\tc.*\n" +
            "FROM\n" +
            "\tddg_child_union_coupon dcuc\n" +
            "\tLEFT JOIN li_member m ON m.ddg_id = dcuc.parent_id\n" +
            "\tLEFT JOIN li_member_coupon mc ON m.id = mc.member_id\n" +
            "\tLEFT JOIN li_coupon c ON c.id = mc.coupon_id\n" +
            "\tWHERE mc.member_coupon_status != 'USED' AND dcuc.child_id = ${childId}\n" +
            "\tGROUP BY c.id")
    IPage<Coupon> getCouponByChildId(Page<Coupon> initPage, @Param(Constants.WRAPPER) QueryWrapper<Coupon> queryCouponWrapper, String childId);
}