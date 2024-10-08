package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
import cn.lili.modules.goods.entity.vos.GoodsSkuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DdgChildApplyBuyServiceImplTest {
    @Autowired
    private DdgChildApplyBuyService ddgChildApplyBuyService;

    @Test
    void getChildApplyBuyByChildId() {
        GoodsDdgSearchParams goodsDdgSearchParams = new GoodsDdgSearchParams();
        goodsDdgSearchParams.setChildId("1535195481096314881");
        IPage<DdgChildApplyBuyVO> childApplyBuyByChildId = ddgChildApplyBuyService.getChildApplyBuyByChildId(goodsDdgSearchParams);
        System.out.println(JSONUtil.toJsonStr(childApplyBuyByChildId));
    }

    @Test
    void getChildApplyBuy() {
        GoodsDdgSearchParams goodsDdgSearchParams = new GoodsDdgSearchParams();
        goodsDdgSearchParams.setStatus(true);
        List<DdgChildApplyBuyVO> childApplyBuy = ddgChildApplyBuyService.getChildApplyBuy(goodsDdgSearchParams);
        System.out.println(JSONUtil.toJsonStr(childApplyBuy));

    }

    @Test
    @Rollback
    @Transactional
    void cancelChildApplyBuy() {
        DdgChildApplyBuyVO ddgChildApplyBuyVO = new DdgChildApplyBuyVO();
        ddgChildApplyBuyVO.setId("1584809732874424322");
        Boolean aBoolean = ddgChildApplyBuyService.cancelChildApplyBuy(ddgChildApplyBuyVO);
        System.out.println(aBoolean);
    }

    @Test
    @Rollback
    @Transactional
    void addChildApplyBuy() {
        DdgChildApplyBuyVO ddgChildApplyBuyVO = new DdgChildApplyBuyVO();
        ddgChildApplyBuyVO.setChildId("1535195481096314881");
        ddgChildApplyBuyVO.setParentId("1535182572286844929");
        ddgChildApplyBuyVO.setGoodsSkuId("1580459378168791041");
        ddgChildApplyBuyVO.setGoodsId("1580459377522868226");
        DdgChildApplyBuy ddgChildApplyBuy = ddgChildApplyBuyService.addChildApplyBuy(ddgChildApplyBuyVO);
        System.out.println(JSONUtil.toJsonStr(ddgChildApplyBuy));
    }

    @Test
    void getGoodsSkuByChildApplyBuy() {
        GoodsDdgSearchParams goodsDdgSearchParams = new GoodsDdgSearchParams();
        goodsDdgSearchParams.setId("1584810070377484290");
        GoodsSkuVO goodsSkuByChildApplyBuy = ddgChildApplyBuyService.getGoodsSkuByChildApplyBuy(goodsDdgSearchParams);
        System.out.println(JSONUtil.toJsonStr(goodsSkuByChildApplyBuy));
    }
}