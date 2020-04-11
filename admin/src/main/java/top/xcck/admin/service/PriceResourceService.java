package top.xcck.admin.service;

import com.baomidou.mybatisplus.service.IService;
import top.xcck.admin.entity.PriceResource;

/**
 * @Description: 价格资源文件的业务层接口定义
 * @Author : YiYChao
 * @date : 2020/4/11 11:37
 * @version : V1.0
 */
public interface PriceResourceService extends IService<PriceResource> {
    /**
     * 功能描述: 解析上传的Excel定价文件
     * @param  path Excel资源文件的网络地址
     * @return void
     * @author YiYChao
     * @date 2020/4/11 17:30
     */
    void parseExcel(String path);
}
