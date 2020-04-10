package top.xcck.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.xcck.admin.base.DataEntity;

/**
 *  @Description: 商品基本信息实体类
 *  @author YiYChao
 *  @Date: 2020/4/8 11:09
 *  @version V1.0
 */
@TableName("tb_product")
public class Product extends DataEntity<Product> {
    private static final long serialVersionUID = 1L;

    private String title;
    private String code;
    private String category;
    private String brand;
    @TableField(value = "pdt_status")
    private String pdtStatus;
    @TableField(value = "cont_status")
    private String contStatus;
    @TableField(value = "img_url")
    private String imgUrl;
    private Double price;
    private Integer parent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPdtStatus() {
        return pdtStatus;
    }

    public void setPdtStatus(String pdtStatus) {
        this.pdtStatus = pdtStatus;
    }

    public String getContStatus() {
        return contStatus;
    }

    public void setContStatus(String contStatus) {
        this.contStatus = contStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", pdtStatus='" + pdtStatus + '\'' +
                ", contStatus='" + contStatus + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", parent=" + parent +
                ", createId=" + createId +
                ", createDate=" + createDate +
                ", updateId=" + updateId +
                ", updateDate=" + updateDate +
                '}';
    }
}
