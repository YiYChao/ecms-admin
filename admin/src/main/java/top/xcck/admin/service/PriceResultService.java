package top.xcck.admin.service;

import com.baomidou.mybatisplus.service.IService;
import org.apache.poi.ss.usermodel.Workbook;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.entity.PriceResult;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description: 定价结果相关操作业务层接口定义
 * @Author : YiYChao
 * @date : 2020/4/11 11:40
 * @version : V1.0
 */
public interface PriceResultService extends IService<PriceResult> {
    /**
     * 功能描述: 更具Excel文件的网络路径，查询资源文件的记录，对每一条记录进行解析
     * @param resourceList 资源文件实体列表
     * @return void
     * @author YiYChao
     * @date 2020/4/11 17:32
     */
    List<PriceResource> getJDPrice(List<PriceResource> resourceList) throws UnsupportedEncodingException;
    
    /**
     * @Description: 根据选中的id导出表格
     * @param ids id列表
     * @param webUrl 询价文件的网络地址
     * @return Workbook对象
     * @author YiYChao
     * @date 2020/4/13 11:24
     */
    Workbook exportExcel(List<Integer> ids, String webUrl);
}
