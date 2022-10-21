package cn.lili.modules.ddg.serviceimpl;

import cn.lili.common.utils.DateUtil;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgParentsAssignGoodsSkuVO;
import cn.lili.modules.ddg.service.DdgParentsAssignGoodsSkuService;
import cn.lili.modules.goods.entity.dos.GoodsSku;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
        searchParams.setChildId("123");
        IPage<GoodsSku> strings = ddgParentsAssignGoodsSkuService.getGoodsSkuByChildIdFormAssign(searchParams);
        System.out.println(strings.toString());
    }
}