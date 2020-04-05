package top.xcck.ecms.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *  @Description: 商品的数据参数对象
 *  @author YiYChao
 *  @Date: 2020/4/4 9:59
 *  @version V1.0
 */
@Getter
@Setter
public class ProductDto {
    private String keyword;     // 搜索关键词
    private Integer page;      // 搜索起始页
    private Integer size;       // 每页显示的记录数
}
