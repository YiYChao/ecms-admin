package top.xcck.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import top.xcck.admin.base.DataEntity;

import java.util.HashMap;

/**
 * @Description: 定价商品结果实体类
 * @Author : YiYChao
 * @date : 2020/4/11 11:09
 * @version : V1.0
 */
@TableName(value = "tb_purchase_price_result")
public class PriceResult extends DataEntity<PriceResult> {
    private static final long serialVersionUID = 1L;

    private Long sourceId;
    private String img;
    private String title;
    private String price;
    private String url;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
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
                "source_id=" + sourceId +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }

    /**
     * @Description: 重写hashcode 方法，返回的hashCode不一样才再去比较每个属性的值，即equals
     * @author YiYChao
     * @date 2020/4/12 12:28
     */
    @Override
    public int hashCode() {
        return this.url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (obj instanceof PriceResult){
            PriceResult result = (PriceResult) obj;
            if (result.getUrl().equals(this.url)){  // 只根据URL链接进行判断
                return true;
            }
        }
        return false;
    }
}
