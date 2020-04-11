package top.xcck.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.xcck.admin.base.DataEntity;

/**
 * @Description: 定价商品结果实体类
 * @Author : YiYChao
 * @date : 2020/4/11 11:09
 * @version : V1.0
 */
@TableName(value = "tb_purchase_price_result")
public class PriceResult extends DataEntity<PriceResult> {
    private static final long serialVersionUID = 1L;

    private Long source_id;
    private String img;
    private String title;
    private String price;
    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSource_id() {
        return source_id;
    }

    public void setSource_id(Long source_id) {
        this.source_id = source_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PriceResult{" +
                "source_id=" + source_id +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }
}
