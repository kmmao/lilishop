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
}