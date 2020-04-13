package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xcck.admin.dao.PriceResourceDao;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.service.PriceResourceService;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author : YiYChao
 * @version : V1.0
 * @Description: 定价资源相关操作业务层接口实现
 * @date : 2020/4/11 11:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PriceResourceServiceImpl extends ServiceImpl<PriceResourceDao, PriceResource> implements PriceResourceService {

    @Override
    public void parseExcel(String path) throws Exception {
        Workbook workbook = getWorkbook(path);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();
        for (int i = 2; i < lastRow; i++) {
            Row row = sheet.getRow(i);
            PriceResource resource = parseRow2Entity(row);      // 解析表格行
            if (resource != null){
                if (StringUtils.isNotBlank(resource.getBrand()) && StringUtils.isNotBlank(resource.getType())){
                    resource.setWebUrl(path);          // 补全数据
                    baseMapper.insert(resource);
                }
            }
        }
    }

    /**
     * @param path 询价文件的网络路径
     * @return List<PriceResource> 资源文件列表
     * @Description: 根据询价文件的网络路径查找询价资源文件
     * @author YiYChao
     * @date 2020/4/12 10:03
     */
    @Override
    public List<PriceResource> findByWebUrl(String path) {
        EntityWrapper<PriceResource> wrapper = new EntityWrapper<>();
        wrapper.eq("web_url", path);
        return baseMapper.selectList(wrapper);
    }

    /**
     * @param resourceList 资源实体列表
     * @return void
     * @Description: 批量更新资源实体列表的搜索链接
     * @author YiYChao
     * @date 2020/4/12 11:55
     */
    @Override
    public void updateSearchUrl(List<PriceResource> resourceList) {
        for (PriceResource resource : resourceList){
            baseMapper.updateById(resource);
        }
    }

    /**
     * 功能描述: 获得Excel对象
     *
     * @param path 网络文件的路径
     * @return Workbook对象
     * @author YiYChao
     * @date 2020/4/11 17:53
     */
    private static Workbook getWorkbook(String path) throws Exception {
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

    private PriceResource parseRow2Entity(Row row) {
        if (row == null){
            return null;
        }
        PriceResource resource = new PriceResource();
        short lastCellNum = row.getLastCellNum();
        if (lastCellNum > 11){      // 超出预设列数的不进行解析
            lastCellNum = 11;
        }
        switch (12 - lastCellNum) {
            case 1:
                resource.setEstimateMax(row.getCell(lastCellNum - 1).toString());    // 预估顶价
            case 2:
                resource.setEstimateMin(row.getCell(lastCellNum - 2).toString()); // 预估底价
            case 3:
                resource.setCost(row.getCell(lastCellNum - 3).toString());    // 成本
            case 4:
                resource.setOthers(row.getCell(lastCellNum - 4).toString());  // 其它
            case 5:
                resource.setSpec(row.getCell(lastCellNum - 5).toString());    // 规格
            case 6:
                resource.setShape(row.getCell(lastCellNum - 6).toString());   // 形状
            case 7:
                resource.setSize(row.getCell(lastCellNum - 7).toString());    // 大小
            case 8:
                resource.setColor(row.getCell(lastCellNum - 8).toString());   // 颜色
            case 9:
                resource.setType(row.getCell(lastCellNum - 9).toString());    // 型号
            case 10:
                resource.setBrand(row.getCell(lastCellNum - 10).toString());   // 品牌
            default:
                System.out.println("超出表格指定范围的无用内容，行号：" + row.getRowNum() + "列标：" + lastCellNum);
        }
        return resource;
    }
}
