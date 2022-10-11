package cn.lili.seller.test.goods;

import cn.hutool.json.JSONUtil;
import cn.lili.modules.goods.entity.dto.GoodsOperationDTO;
import cn.lili.modules.goods.entity.vos.CategoryVO;
import cn.lili.modules.goods.service.CategoryService;
import cn.lili.modules.goods.service.GoodsService;
import cn.lili.modules.goods.service.GoodsSkuService;
import cn.lili.modules.order.cart.entity.dto.TradeDTO;
import cn.lili.modules.order.cart.entity.enums.CartTypeEnum;
import cn.lili.modules.order.cart.entity.vo.TradeParams;
import cn.lili.modules.order.cart.service.CartService;
import cn.lili.modules.payment.service.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author allen
 * @since 2022/10/11
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
class GoodsTest {

    /**
     * 商品
     */
    @Autowired
    private GoodsService goodsService;
    /**
     * 商品sku
     */
    @Autowired
    private GoodsSkuService goodsSkuService;


    @Test
    void getOldGoodsImport() {
        GoodsOperationDTO goodsOperationDTO = new GoodsOperationDTO();
        // 取得旧地址的商品对象，存到新数据库里面

        // 存放新商品
        goodsService.addGoods(goodsOperationDTO);
    }

}
