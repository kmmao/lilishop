package cn.lili.modules.ddg.serviceimpl;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.ddg.entity.dos.DdgChildApplyBuy;
import cn.lili.modules.ddg.entity.dto.GoodsDdgSearchParams;
import cn.lili.modules.ddg.entity.vo.DdgChildApplyBuyVO;
import cn.lili.modules.ddg.service.DdgChildApplyBuyService;
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
}