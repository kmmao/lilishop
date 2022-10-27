package cn.lili.modules.ddg.mapper;


import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 嘟嘟罐儿童申请采购相关联数据处理层
 *
 * @author Allen
 * @since 2022-10-20 18:25:16
 */
public interface DdgChildApplyBuyMapper extends BaseMapper<DdgChildApplyBuy> {
    @Select("SELECT cab.* FROM ddg_child_apply_buy cab WHERE cab.child_id=${childId} ${ew.customSqlSegment}")
    IPage<DdgChildApplyBuy> getChildApplyBuyByChildId(Page<Object> initPage, @Param(Constants.WRAPPER) QueryWrapper<Object> queryChildApplyBuyWrapper, String childId);
}