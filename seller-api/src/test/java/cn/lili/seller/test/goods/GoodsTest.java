package cn.lili.seller.test.goods;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.lili.common.enums.ResultCode;
import cn.lili.common.exception.ServiceException;
import cn.lili.common.utils.StringUtils;
import cn.lili.modules.goods.entity.dos.Category;
import cn.lili.modules.goods.entity.dos.Goods;
import cn.lili.modules.goods.entity.dto.GoodsOperationDTO;
import cn.lili.modules.goods.entity.dto.GoodsSearchParams;
import cn.lili.modules.goods.service.CategoryService;
import cn.lili.modules.goods.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;


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

    @Autowired
    private CategoryService categoryService;

    String goods_url = "http://42.192.42.217:9999/api/ddg/font/service/getStoreProductList";
    String goods_detail_url = "http://42.192.42.217:9999/api/ddg/font/service/getStoreProductDetail";

    @Test
    void readExecl() {
        ExcelReader reader = ExcelUtil.getReader("/Users/allen/Documents/GitHub/lilishop/seller-api/src/test/java/cn/lili/seller/test/goods/import.xlsx");
        List<CategoryVo> all = reader.readAll(CategoryVo.class);
        Map<String, String> readerMap = all.stream().collect(Collectors.toMap(CategoryVo::getSku, CategoryVo::getLevel_third));
        System.out.println(readerMap.get("3896933133142"));
    }

    @Test
    void getGoods() {
        GoodsSearchParams goodsSearchParams = new GoodsSearchParams();
        goodsSearchParams.setGoodsName("儿童计数竹节跳儿园专用可调节计数跳绳软珠节初学者跳绳");
        List<Goods> goods = goodsService.queryListByParams(goodsSearchParams);
        System.out.println(JSONUtil.toJsonStr(goods));
    }

    @Test
    void getCategory() {
        Category category = new Category();
        category.setName("配对/认知玩具");
        List<Category> byAllBySortOrder = categoryService.findByAllBySortOrder(category);
        String level_third = byAllBySortOrder.get(0).getId();
        Category second_category = categoryService.getCategoryById(byAllBySortOrder.get(0).getParentId());
        String level_second = second_category.getId();
        Category first_category = categoryService.getCategoryById(byAllBySortOrder.get(0).getParentId());
        String level_first = first_category.getId();
        String path = level_first + "," + level_second + "," + level_third;
        Map<String, String> MapList = new HashMap<>();
        if (MapList.containsKey(category.getName())) {
            //直接取
        } else {
            MapList.put(category.getName(), path);
        }
        System.out.println(path);
    }

    @Test
    void getOldGoodsImport() {
        // 取得旧地址的商品对象，存到新数据库里面
        SortedMap<Object, Object> sortedMap = new TreeMap<Object, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("page", 1);
                put("limit", 200000);
            }
        };
        String result1 = HttpRequest.post(goods_url).body(JSONUtil.toJsonStr(sortedMap)).execute().body();
        System.out.println(result1);
        JSONObject goodObject = (JSONObject) JSONUtil.parseObj(result1).get("data");
        JSONArray good_list = goodObject.getJSONArray("list");

        ExcelReader reader = ExcelUtil.getReader("/Users/allen/Documents/GitHub/lilishop/seller-api/src/test/java/cn/lili/seller/test/goods/import.xlsx");
        List<CategoryVo> all = reader.readAll(CategoryVo.class);
        Map<String, String> readerMap = all.stream().collect(Collectors.toMap(CategoryVo::getGoodId, CategoryVo::getLevel_third, (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));

        for (int i = 0; i < good_list.size(); i++) {
            GoodsOperationDTO goodsOperationDTO = new GoodsOperationDTO();
            JSONObject good_item = (JSONObject) good_list.get(i);

            // 测试专用
//            if(!good_item.getStr("id").equals("1701"))
//                continue;

//            GoodsSearchParams goodsSearchParams = new GoodsSearchParams();
//            goodsSearchParams.setGoodsName(good_item.getStr("storeName"));
//            List<Goods> goods = goodsService.queryListByParams(goodsSearchParams);
//            if(goods.size() > 0)
//                continue;

            Map paramMap = new HashMap();
            paramMap.put("goodsId", good_item.getStr("id"));
//            System.out.println(good_item.getStr("id"));
            String good_detail = HttpRequest.get(goods_detail_url).form(paramMap).execute().body();
//            System.out.println(good_detail);
            JSONObject goodDetailObject = (JSONObject) JSONUtil.parseObj(good_detail).get("data");
            JSONArray productAttrObject = goodDetailObject.getJSONArray("productAttr");
            JSONObject productValueObject = goodDetailObject.getJSONObject("productValue");
            JSONObject productInfoObject = goodDetailObject.getJSONObject("productInfo");
            JSONObject paroductAttr = (JSONObject) productAttrObject.get(0);
            String[] skuValueList = paroductAttr.getStr("attrValues").split(",");
            String[] skuAttrList = paroductAttr.getStr("attrName").split("/");
            List<String> goodsGalleryList = new ArrayList<>();
            JSONArray sliderImageArray = JSONUtil.parseArray(productInfoObject.getStr("sliderImage"));
            for (int j = 0; j < sliderImageArray.size(); j++) {
                String aliderImage = sliderImageArray.get(j, String.class);
                if (aliderImage.contains("crmebimage")) {
                    String stringBuilder = "<img src=\"https://buyer.zmshops.xycloud.info/c" +
                            aliderImage.substring(aliderImage.substring(0, aliderImage.indexOf("crmebimage")).length() + 1, aliderImage.length());
                    goodsGalleryList.add(stringBuilder);
                } else {
                    goodsGalleryList.add(aliderImage);
                }
            }
            goodsOperationDTO.setGoodsGalleryList(goodsGalleryList);
            List<Map<String, Object>> skuList = new ArrayList<>();
            Map<String, String> MapList = new HashMap<>();
            for (int j = 0; j < skuValueList.length; j++) {
                String s = skuValueList[j];
//                System.out.println(s);
                Map<String, Object> sku = new HashMap<>();
                JSONObject productValue = (JSONObject) productValueObject.get(s);
                if (productValue == null) {
                    System.out.println("productValue空报错goodsId：" + good_item.getStr("id"));
                    continue;
                }
                if (StringUtils.isEmpty(productValue.getStr("barCode"))) {
                    sku.put("sn", productInfoObject.getStr("supplier"));
                } else {
                    sku.put("sn", productValue.getStr("barCode"));
                }
                sku.put("price", productValue.getDouble("price"));
                sku.put("cost", productValue.getDouble("cost"));
                sku.put("weight", productValue.getDouble("weight"));
                sku.put("quantity", productValue.getInt("stock"));
                JSONArray imagesArray = new JSONArray();
                JSONObject imagesObject = new JSONObject();
                imagesObject.set("url", productValue.getStr("image"));
                imagesObject.set("name", productValue.getStr("barCode"));
                imagesObject.set("status", "finished");
                imagesArray.add(imagesObject);
                for (int z = 0; z < sliderImageArray.size(); z++) {
                    JSONObject image_item = new JSONObject();
                    String imageStr = sliderImageArray.get(z, String.class);
                    if (imageStr.contains("crmebimage")) {
                        String stringBuilder = "<img src=\"https://buyer.zmshops.xycloud.info/c" +
                                imageStr.substring(imageStr.substring(0, imageStr.indexOf("crmebimage")).length() + 1, imageStr.length());
                        image_item.set("url", stringBuilder);
                    } else {
                        image_item.set("url", imageStr);
                    }
                    image_item.set("name", productValue.getStr("barCode"));
                    image_item.set("status", "finished");
                    imagesArray.add(image_item);
                }
                sku.put("images", imagesArray);
                String[] valueArray = s.split("/");
                if (valueArray.length != skuAttrList.length)
                {
                    System.out.println("valueArray与skuAttrList值不相等报错goodsId：" + good_item.getStr("id"));
                    continue;
                }
                for (int k = 0; k < skuAttrList.length; k++) {
                    sku.put(skuAttrList[k], valueArray[k]);
                }
                skuList.add(sku);
            }
            goodsOperationDTO.setBrandId("0");
            goodsOperationDTO.setPrice(good_item.getDouble("price"));

            String categoryName = readerMap.get(good_item.getStr("id"));
            if (StrUtil.isEmpty(categoryName))
            {
                System.out.println("categoryName空报错goodsId：" + good_item.getStr("id"));
                continue;
            }

            if (!MapList.containsKey(categoryName)) {
                Category category = new Category();
                category.setName(categoryName);
                List<Category> byAllBySortOrder = categoryService.findByAllBySortOrder(category);
                if (byAllBySortOrder == null || byAllBySortOrder.size() <= 0){
                    System.out.println("byAllBySortOrder空报错goodsId：" + good_item.getStr("id"));
                    continue;
                }
                String level_third = byAllBySortOrder.get(0).getId();
                Category second_category = categoryService.getCategoryById(byAllBySortOrder.get(0).getParentId());
                if (second_category == null){
                    System.out.println("second_category空报错goodsId：" + good_item.getStr("id"));
                    continue;
                }
                String level_second = second_category.getId();
                Category first_category = categoryService.getCategoryById(second_category.getParentId());
                if (first_category == null){
                    System.out.println("first_category空报错goodsId：" + good_item.getStr("id"));
                    continue;
                }
                String level_first = first_category.getId();
                String path = level_first + "," + level_second + "," + level_third;
                MapList.put(categoryName, path);
            }

//            goodsOperationDTO.setCategoryPath("1580122559395115010,1580122590302941186,1580122661421559809"); //分类路径
            goodsOperationDTO.setCategoryPath(MapList.get(categoryName));
            goodsOperationDTO.setGoodsName(good_item.getStr("storeName"));
            JSONArray contentArray = JSONUtil.parseArray(productInfoObject.getStr("content"));
            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append("<P>");
            for (int j = 0; j < contentArray.size(); j++) {
                String imagesVal = contentArray.get(j, String.class);
                StringBuilder str1 = new StringBuilder();
                if (imagesVal.contains("crmebimage")) {
                    str1.append("<img src=\"https://buyer.zmshops.xycloud.info/c");
                    str1.append(imagesVal.substring(imagesVal.substring(0, imagesVal.indexOf("crmebimage")).length() + 1, imagesVal.length()));
                } else {
                    str1.append("<img src=\"");
                    str1.append(imagesVal);
                }
                str1.append("\" />");
                contentBuilder.append(str1);
            }
            contentBuilder.append("</p>");
            goodsOperationDTO.setSn(productInfoObject.getStr("supplier"));
            goodsOperationDTO.setIntro(contentBuilder.toString()); // 详情
            goodsOperationDTO.setMobileIntro(contentBuilder.toString());
            goodsOperationDTO.setQuantity(good_item.getInt("stock"));
            goodsOperationDTO.setRelease(true);
            goodsOperationDTO.setRecommend(true);
            goodsOperationDTO.setGoodsUnit("件");
            goodsOperationDTO.setTemplateId("1582269498609881089");
            goodsOperationDTO.setSellingPoint(good_item.getStr("storeName"));  //卖点
            goodsOperationDTO.setSalesModel("RETAIL");
            goodsOperationDTO.setGoodsType("PHYSICAL_GOODS");
            goodsOperationDTO.setSkuList(skuList);
            if (goodsOperationDTO.getSkuList() == null || goodsOperationDTO.getSkuList().isEmpty()) {
                System.out.println("SKu列表不存在报错goodsId：" + good_item.getStr("id"));
                continue;
            }

            // 存放新商品
            goodsService.addGoods(goodsOperationDTO);
        }
    }

    /**
     * 处理商品分类ID的问题
     */
    @Test
    void fixGoodsSkuCategoryPath() {

    }

}
