package top.xcck.ecms.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xcck.ecms.model.pojo.TbProduct;

import java.util.List;

/**
 *  @Description: 抓取水平价格以完善
 *  @author YiYChao
 *  @Date: 2020/4/1 23:54
 *  @version V1.0
 */
@Component
public class CompleatePrice {

    @Autowired
    private HttpClientUtils clientUtils;

    public List<TbProduct> getPrice(List<TbProduct> productList){
        // 遍历保存图片
        for (int index = 0; index < productList.size(); index++) {
            TbProduct pdt = productList.get(index);
            if (pdt.getCode() == null || !pdt.getCntStatus().equals("上架成功")){
                continue;
            }
            // 1.通过商品编码拼接商品的链接地址
            String url = "http://pas.suning.com/nspcsale_0_" + pdt.getCode() + "_" + pdt.getCode() + "_0000000000_140_791____.html";
            // 2.请求商品价格信息
            String detailPage = clientUtils.doGetHtml(url);
            String priceJson = detailPage.substring("pcData(".length(), detailPage.length() - 2);
            JSONObject saleInfo = JSONObject.parseObject(priceJson).getJSONObject("data").getJSONObject("price").getJSONArray("saleInfo").getJSONObject(0);
            String refPrice = (String)saleInfo.get("refPrice");

            // 抓取到价格
            if (refPrice != null && !"".equals(refPrice)){
                pdt.setPrice(Double.valueOf(refPrice));         // 更新价格
            }
        }
        return productList;
    }
}
