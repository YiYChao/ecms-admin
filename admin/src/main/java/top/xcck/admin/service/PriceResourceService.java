package top.xcck.admin.service;

import com.baomidou.mybatisplus.service.IService;
import top.xcck.admin.entity.PriceResource;

import java.util.List;

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
    void parseExcel(String path) throws Exception;

    /**
     * @Description: 根据询价文件的网络路径查找询价资源文件
     * @param path 询价文件的网络路径
     * @return List<PriceResource> 资源文件列表
     * @author YiYChao
     * @date 2020/4/12 10:03
     */
    List<PriceResource> findByWebUrl(String path);

    /**
     * @Description: 批量更新资源实体列表的搜索链接
     * @param resourceList 资源实体列表
     * @return void
     * @author YiYChao
     * @date 2020/4/12 11:55
     */
    void updateSearchUrl(List<PriceResource> resourceList);
}
