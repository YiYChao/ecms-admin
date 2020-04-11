package top.xcck.admin.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.lang3.StringUtils;
import top.xcck.admin.base.DataEntity;

/**
 * @Description: 定价商品资源实体类
 * @Author : YiYChao
 * @date : 2020/4/11 11:09
 * @version : V1.0
 */
@TableName(value = "tb_purchase_price_resource")
public class PriceResource extends DataEntity<PriceResource> {
    private static final long serialVersionUID = 1L;

    private String brand;   //品牌
    private String type;    //型号
    private String color;   //颜色
    private String size;   //大小
    private String shape; //形状
    private String spec; //规格
    private String others; //其他
    private String cost; //成本
    private String estimate_min; //预估底价
    private String estimate;//预估顶价
    private String web_url; //文件的网络资源地址
    private String search_url;  // 资源的网络搜索路径

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getEstimate_min() {
        return estimate_min;
    }

    public void setEstimate_min(String estimate_min) {
        this.estimate_min = estimate_min;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getSearch_url() {
        return search_url;
    }

    public void setSearch_url(String search_url) {
        this.search_url = search_url;
    }

    /**
     * 功能描述: 拼接查询参数
     * @return 拼接出来的查询参数
     * @author YiYChao
     * @date 2020/4/11 17:47
     */
    public String searmParams(){
        String params = this.brand + " " + this.type + " ";
        params += StringUtils.isNotBlank(this.color) ? (this.color + " ") : "";
        params += StringUtils.isNotBlank(this.size) ? (this.size + " ") : "";
        params += StringUtils.isNotBlank(this.shape) ? (this.shape + " ") : "";
        params += StringUtils.isNotBlank(this.spec) ? (this.spec + " ") : "";
        return params.trim();
    }


    @Override
    public String toString() {
        return "PriceResource{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", shape='" + shape + '\'' +
                ", spec='" + spec + '\'' +
                ", others='" + others + '\'' +
                ", cost='" + cost + '\'' +
                ", estimate_min='" + estimate_min + '\'' +
                ", estimate='" + estimate + '\'' +
                ", web_url='" + web_url + '\'' +
                ", id=" + id +
                '}';
    }
}
