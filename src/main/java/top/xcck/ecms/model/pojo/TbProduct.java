package top.xcck.ecms.model.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 商品基本信息表
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
@Document(indexName = "xcck", type = "product")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbProduct implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private Long id;
    /**
     * 商品名称
     */
    @Field(index = true, type = FieldType.Text, analyzer = "ik_smart", store = true)
    private String title;

    /**
     * 商品编码
     */
    @Field(index = true, type = FieldType.Text, store = true)
    private String code;

    /**
     * 商品分类
     */
    @Field(index = true, type = FieldType.Text, analyzer = "ik_smart")
    private String category;

    /**
     * 商品品牌
     */
    @Field(index = true, type = FieldType.Text, analyzer = "ik_smart")
    private String brand;

    /**
     * 商品发布日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date released;

    /**
     * 商品状态
     */
    private String pdtStatus;

    /**
     * 商品内容状态
     */
    private String cntStatus;

    /**
     * 商品主图连接地址
     */
    private String imgUrl;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品信息更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updated;

    /**
     * 父级编码
     */
    private Long parent;

    /**
     * 备注
     */
    private String note;

}
