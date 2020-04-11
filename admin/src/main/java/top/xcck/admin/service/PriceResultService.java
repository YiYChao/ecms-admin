package top.xcck.admin.service;

import com.baomidou.mybatisplus.service.IService;
import top.xcck.admin.entity.PriceResult;

/**
 * @Description: 定价结果相关操作业务层接口定义
 * @Author : YiYChao
 * @date : 2020/4/11 11:40
 * @version : V1.0
 */
public interface PriceResultService extends IService<PriceResult> {
    /**
     * 功能描述: 更具Excel文件的网络路径，查询资源文件的记录，对每一条记录进行解析
     * @param path 定价Excel文件的网络路径 
     * @return void
     * @author YiYChao
     * @date 2020/4/11 17:32
     */
    void getJDPrice(String path);
}
