package top.xcck.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.xcck.admin.entity.PriceResult;
import top.xcck.admin.entity.VO.PriceResultVO;

import java.util.List;

/**
 * @Description: 定价商品结果相关持久层操作接口定义
 * @Author : YiYChao
 * @date : 2020/4/11 11:26
 * @version : V1.0
 */
public interface PriceResultDao extends BaseMapper<PriceResult> {

    /**
     * @Description: 根据结果对象导出查询结果
     * @param ids 结果id主键列表
     * @return List<PriceResultVO> 结果对象的列表
     * @author YiYChao
     * @date 2020/4/13 14:53
     */
    List<PriceResultVO> chosenResult(List<Integer> ids);

    /**
     * @Description: 根据询价表格文件的网络地址导出全部的结果
     * @param webUrl 网络询价文件的地址
     * @return List<PriceResultVO> 结果对象的列表
     * @author YiYChao
     * @date 2020/4/13 14:55
     */
    List<PriceResultVO> allResult(String webUrl);
}
