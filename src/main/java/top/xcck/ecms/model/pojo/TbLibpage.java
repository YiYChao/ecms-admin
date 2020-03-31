package top.xcck.ecms.model.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品内容原始信息表
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbLibpage implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 网页院士内容
     */
    private String page;


}
