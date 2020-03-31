package top.xcck.ecms.model.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品基本信息表
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
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
    private String title;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品分类
     */
    private String category;

    /**
     * 商品品牌
     */
    private String brand;

    /**
     * 商品发布日期
     */
    private String released;

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
    private String price;

    /**
     * 商品信息更新时间
     */
    private String updated;

    /**
     * 父级编码
     */
    private Long parent;

    /**
     * 备注
     */
    private String note;

}
