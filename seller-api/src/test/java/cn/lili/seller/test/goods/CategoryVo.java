package cn.lili.seller.test.goods;

import lombok.Data;

/**
 * 主要用来对接旧数据迁移过来的execl文档对象
 */
@Data
public class CategoryVo {
    private String goodId;

    private String sku;

    private String spId;

    private String level_first;

    private String level_second;

    private String level_third;
}
