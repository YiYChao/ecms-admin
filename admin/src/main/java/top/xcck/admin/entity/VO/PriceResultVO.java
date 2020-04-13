package top.xcck.admin.entity.VO;

import top.xcck.admin.entity.PriceResource;

/**
 *  @Description: 询价结果的VO实体
 *  @author YiYChao
 *  @Date: 2020/4/13 11:04
 *  @version V1.0
 */
public class PriceResultVO  extends PriceResource {
    private String price;       // 价格结果
    private String title;       // 商品标题
    private String url;         // 商品详情页

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
