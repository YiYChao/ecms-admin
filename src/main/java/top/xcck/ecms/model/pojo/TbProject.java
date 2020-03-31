package top.xcck.ecms.model.pojo;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目基本信息表
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbProject implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 折扣比例
     */
    private Integer rate;

    /**
     * 是否单一折扣
     */
    private Boolean singleFlag;

    /**
     * 项目开始日期
     */
    private String begin;

    /**
     * 项目结束日期
     */
    private String end;


}
