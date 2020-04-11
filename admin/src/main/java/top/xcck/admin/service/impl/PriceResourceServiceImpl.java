package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xcck.admin.dao.PriceResourceDao;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.service.PriceResourceService;

import java.io.InputStream;
import java.net.URL;

/**
 * @Description: 定价资源相关操作业务层接口实现
 * @author : YiYChao
 * @date : 2020/4/11 11:38
 * @version : V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PriceResourceServiceImpl extends ServiceImpl<PriceResourceDao, PriceResource> implements PriceResourceService {

    @Override
    public void parseExcel(String path) {
        // TODO
    }

    /**
     * 功能描述: 获得Excel对象
     * @param path 网络文件的路径
     * @return Workbook对象
     * @author YiYChao
     * @date 2020/4/11 17:53
     */
    private static Workbook getWorkbook(String path) throws Exception{
        String type = path.substring(path.lastIndexOf(".") + 1);
        Workbook wb;
        //根据文件后缀（xls/xlsx）进行判断
        InputStream input = new URL(path).openStream();
        if ("xls".equals(type)) {
            //文件流对象
            wb = new HSSFWorkbook(input);
        } else if ("xlsx".equals(type)) {
            wb = new XSSFWorkbook(input);
        } else {
            throw new Exception("文件 类型错误");
        }
        return wb;
    }
}
