package cn.lili.seller.test.goods;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.lili.common.utils.StringUtils;
import cn.lili.modules.goods.entity.dto.GoodsOperationDTO;
import cn.lili.modules.goods.service.CategoryService;
import cn.lili.modules.goods.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;


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
    void readExecl(){
        ExcelReader reader = ExcelUtil.getReader("/Users/allen/Documents/GitHub/lilishop/seller-api/src/test/java/cn/lili/seller/test/goods/ipmort.xlsx");
        List<CategoryVo> all = reader.readAll(CategoryVo.class);
        System.out.println(JSONUtil.toJsonStr(all));
    }

    @Test
    void getOldGoodsImport() {
//        List<Category> categories = categoryService.firstCategory();
//        Category category = new Category();
//        category.setName("配对/认知玩具");
//        List<Category> byAllBySortOrder = categoryService.findByAllBySortOrder(category);
//        System.out.println(JSONUtil.toJsonStr(byAllBySortOrder));
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
        for (int i = 0; i < good_list.size(); i++) {
            GoodsOperationDTO goodsOperationDTO = new GoodsOperationDTO();
            JSONObject good_item = (JSONObject) good_list.get(i);
            Map paramMap = new HashMap();
            paramMap.put("goodsId", good_item.getStr("id"));
            System.out.println(good_item.getStr("id"));
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
                String aliderImage = sliderImageArray.get(j,String.class);
                if(aliderImage.contains("crmebimage")){
                    String stringBuilder = "<img src=\"https://buyer.zmshops.xycloud.info/c" +
                            aliderImage.substring(aliderImage.substring(0, aliderImage.indexOf("crmebimage")).length() + 1, aliderImage.length());
                    goodsGalleryList.add(stringBuilder);
                }else{
                    goodsGalleryList.add(aliderImage);
                }
            }
            goodsOperationDTO.setGoodsGalleryList(goodsGalleryList);
            List<Map<String, Object>> skuList = new ArrayList<>();
            for (int j = 0; j < skuValueList.length; j++) {
                String s = skuValueList[j];
//                System.out.println(s);
                Map<String, Object> sku = new HashMap<>();
                JSONObject productValue = (JSONObject) productValueObject.get(s);
                if(productValue==null){
                    continue;
                }
                if (StringUtils.isEmpty(productValue.getStr("barCode"))){
                    sku.put("sn",productInfoObject.getStr("supplier"));
                }else{
                    sku.put("sn",productValue.getStr("barCode"));
                }
                sku.put("price",productValue.getDouble("price"));
                sku.put("cost",productValue.getDouble("cost"));
                sku.put("weight",productValue.getDouble("weight"));
                sku.put("quantity",productValue.getInt("stock"));
                JSONArray imagesArray = new JSONArray();
                JSONObject imagesObject = new JSONObject();
                imagesObject.set("url",productValue.getStr("image"));
                imagesObject.set("name",productValue.getStr("barCode"));
                imagesObject.set("status","finished");
                imagesArray.add(imagesObject);
                for (int z = 0; z < sliderImageArray.size(); z++) {
                    JSONObject image_item = new JSONObject();
                    String imageStr = sliderImageArray.get(z,String.class);
                    if(imageStr.contains("crmebimage")){
                        String stringBuilder = "<img src=\"https://buyer.zmshops.xycloud.info/c" +
                                imageStr.substring(imageStr.substring(0, imageStr.indexOf("crmebimage")).length() + 1, imageStr.length());
                        image_item.set("url", stringBuilder);
                    }else{
                        image_item.set("url",imageStr);
                    }
                    image_item.set("name",productValue.getStr("barCode"));
                    image_item.set("status","finished");
                    imagesArray.add(image_item);
                }
                sku.put("images",imagesArray);
                String[] valueArray = s.split("/");
                for (int k = 0; k < skuAttrList.length; k++) {
                    sku.put(skuAttrList[k],valueArray[k]);
                }
                skuList.add(sku);
            }
            goodsOperationDTO.setBrandId("0");
            goodsOperationDTO.setPrice(good_item.getDouble("price"));
            goodsOperationDTO.setCategoryPath("1580122559395115010,1580122590302941186,1580122661421559809"); //分类路径
            goodsOperationDTO.setGoodsName(good_item.getStr("storeName"));
            JSONArray contentArray = JSONUtil.parseArray(productInfoObject.getStr("content"));
            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append("<P>");
            for (int j = 0; j < contentArray.size(); j++) {
                String imagesVal = contentArray.get(j,String.class);
                StringBuilder str1 = new StringBuilder();
                if(imagesVal.contains("crmebimage")){
                    str1.append("<img src=\"https://buyer.zmshops.xycloud.info/c");
                    str1.append(imagesVal.substring(imagesVal.substring(0, imagesVal.indexOf("crmebimage")).length()+1, imagesVal.length()));
                }else {
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
            goodsOperationDTO.setTemplateId("1579807987375271938");
            goodsOperationDTO.setSellingPoint(good_item.getStr("storeName"));  //卖点
            goodsOperationDTO.setSalesModel("RETAIL");
            goodsOperationDTO.setGoodsType("PHYSICAL_GOODS");
            goodsOperationDTO.setSkuList(skuList);

            // 存放新商品
            goodsService.addGoods(goodsOperationDTO);
        }
    }
}
