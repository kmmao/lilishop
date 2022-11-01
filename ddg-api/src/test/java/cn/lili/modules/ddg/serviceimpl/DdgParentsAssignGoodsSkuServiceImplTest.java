package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.utils.DateUtil;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class DdgParentsAssignGoodsSkuServiceImplTest {

    @Autowired
    private DdgParentsAssignGoodsSkuService ddgParentsAssignGoodsSkuService;

    @Test
    void assignGoods() {
        DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO =new DdgParentsAssignGoodsSkuVO();
        ddgParentsAssignGoodsSkuVO.setGoodsSkuId("1580459374180007938");
        ddgParentsAssignGoodsSkuVO.setChildId("123");
        ddgParentsAssignGoodsSkuVO.setParentId("123");
        ddgParentsAssignGoodsSkuVO.setDistributionTime(DateUtil.getCurrentDateStr());
        ddgParentsAssignGoodsSkuVO.setIsGoldPay(true);
        ddgParentsAssignGoodsSkuVO.setStatus(true);
        ddgParentsAssignGoodsSkuService.addAssignGoodsSku(ddgParentsAssignGoodsSkuVO);
    }

    @Test
    void goodsSkuPageByChildId() {
        GoodsDdgSearchParams searchParams = new GoodsDdgSearchParams();
        searchParams.setChildId("1535195481096314881");
        IPage<GoodsSkuVO> strings = ddgParentsAssignGoodsSkuService.getGoodsSkuByChildIdFormAssign(searchParams);
        System.out.println(strings.toString());
    }

    @Test
    @Transactional
    @Rollback
    void cancelAssignGoodsSku() {
        DdgParentsAssignGoodsSkuVO ddgParentsAssignGoodsSkuVO =new DdgParentsAssignGoodsSkuVO();
        ddgParentsAssignGoodsSkuVO.setGoodsSkuId("1580471390475116545");
        ddgParentsAssignGoodsSkuVO.setChildId("1535195481096314881");
        ddgParentsAssignGoodsSkuVO.setParentId("1535182572286844929");
        Boolean o = (Boolean) ddgParentsAssignGoodsSkuService.cancelAssignGoodsSku(ddgParentsAssignGoodsSkuVO);
        System.out.println(o);
    }
}