package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xcck.admin.dao.PriceResultDao;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.entity.PriceResult;
import top.xcck.admin.entity.VO.PriceResultVO;
import top.xcck.admin.service.PriceResultService;
import top.xcck.admin.util.HttpClientUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 定价结果业务层操作接口实现
 * @Author : YiYChao
 * @date : 2020/4/11 11:42
 * @version : V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PriceResultServiceImpl extends ServiceImpl<PriceResultDao, PriceResult> implements PriceResultService {

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Override
    public List<PriceResource> getJDPrice(List<PriceResource> resourceList) throws UnsupportedEncodingException {
        String baseUrl = "http://search.jd.com/Search?enc=utf-8&keyword=";
        for (PriceResource resource : resourceList){
            String searchParams = resource.searchParams();      // 获得查询条件
            String encodeParams = URLEncoder.encode(searchParams, "UTF-8");     // 对查询条件进行编码
            String searchUrl = baseUrl + encodeParams + "&wq=" + encodeParams;      // 获得资源的网络搜索路径
            resource.setSearchUrl(searchUrl);       // 更新资源实体的搜索链接
            Set<PriceResult> resultList = crawerJd(searchUrl, 3);        // 抓取京东数据
            System.out.println("发送网络请求：" + searchUrl + "请求到自营数据条数：" + resourceList.size());
            for (PriceResult result : resultList){
                // 标题包含品牌和型号(都转换成大写进行比较)，则将数据新增到数据库
                if (result.getTitle().contains(resource.getBrand()) && result.getTitle().toUpperCase().contains(resource.getType().toUpperCase())){
                    result.setSourceId(resource.getId());      // 设置外键
                    baseMapper.insert(result);      // 新增入数据库
                }
            }
        }
        return resourceList;
    }


    @Override
    public Workbook exportExcel(List<Integer> ids, String webUrl) {
        List<PriceResultVO> resultVOS = null;
        if (ids != null && ids.size() > 0){     // id列表不为空，导出选中的
            resultVOS = baseMapper.chosenResult(ids);
        }else if (StringUtils.isNotBlank(webUrl)){  // 网络路径不为空，导出全部
            resultVOS = baseMapper.allResult(webUrl);
        }
        if (resultVOS != null && resultVOS.size() > 0){     // 查询到结果
            return getExcel(resultVOS);
        }
        return new HSSFWorkbook();
    }

    /**
     * @Description: 对指定的搜索连接进行指定页数的抓取解析
     * @param searchUrl 搜索连接
     * @param page  指定的搜索页数
     * @return Set<PriceResult> 实体集合
     * @author YiYChao
     * @date 2020/4/12 11:31
     */
    private Set<PriceResult> crawerJd(String searchUrl, int page) {
        Set<PriceResult> resultSet = new HashSet<>();       // 结果列表
        for (int i = 0; i < page; i++) {
            String html = httpClientUtils.doGetHtml(searchUrl + "&page=" + i);
            try {
                Thread.sleep(500);      // 暂停半分钟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Document document = Jsoup.parse(html);      // 解析搜索页结果
            Elements itemList = document.getElementsByClass("gl-item");
            for (Element item : itemList){  // 遍历商品结果列表
                String tagText = item.getElementsByClass("p-icons").text();     // 获得标签文本
                if (tagText.contains("自营")){
                    PriceResult result = new PriceResult();     // 新建结果实体
                    result.setRemarks(tagText);     // 设置商品的标签

                    String img = item.getElementsByClass("p-img").first()
                            .getElementsByTag("img").attr("source-data-lazy-img");
                    img = img.replaceAll("/n7/", "/n1/");       // 替换为440*440的主图
                    result.setImg(img.startsWith("http")? img : ("http:" + img));     // 设置图片

                    String href = item.getElementsByClass("p-img").first()
                            .getElementsByTag("a").first().attr("href");
                    result.setUrl(href.startsWith("http")? href : ("http:" + href));    // 设置商品链接

                    String price = item.getElementsByClass("p-price").first().getElementsByTag("i").text();
                    result.setPrice(price);     // 设置价格

                    String title = item.getElementsByClass("p-name p-name-type-2").first().getElementsByTag("em").text();
                    result.setTitle(title);     // 设置标题

                    resultSet.add(result);     // 加入集合
                }
            }
        }
        return resultSet;
    }

    /**
     * @Description: 获取构建的Excel对象
     * @param resultVOS 数据结果实体
     * @return Workbook表格对象
     * @author YiYChao
     * @date 2020/4/13 15:16
     */
    public Workbook getExcel(List<PriceResultVO> resultVOS) {
        // 创建Excel文件
        Workbook workbook = new HSSFWorkbook();
        // 创建Sheet
        Sheet sheet = workbook.createSheet();
        // 创建表格标题行
        CellRangeAddress title = new CellRangeAddress(0,0,0,14);
        Row first = sheet.createRow(0);
        Cell cell = first.createCell(0);
        cell.setCellValue("商品定价查询结果(" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+")");
        sheet.addMergedRegion(title);

        // 创建表格标题
        Row second = sheet.createRow(1);
        second.createCell(0).setCellValue("序号");
        second.createCell(1).setCellValue("品牌");
        second.createCell(2).setCellValue("型号");
        second.createCell(3).setCellValue("颜色");
        second.createCell(4).setCellValue("大小");
        second.createCell(5).setCellValue("形状");
        second.createCell(6).setCellValue("规格");
        second.createCell(7).setCellValue("其它");
        second.createCell(8).setCellValue("成本");
        second.createCell(9).setCellValue("底价");
        second.createCell(10).setCellValue("顶价");
        second.createCell(11).setCellValue("价格");
        second.createCell(12).setCellValue("标题");
        second.createCell(13).setCellValue("详情页链接");

        // 向表格中装载数据
        for (int i = 0; i < resultVOS.size(); i++) {
            Row item = sheet.createRow(i+2);    // 数据从第二行开始
            item.createCell(0).setCellValue(String.valueOf(i+1));                // 序号
            item.createCell(1).setCellValue(resultVOS.get(i).getBrand());        // 品牌
            item.createCell(2).setCellValue(resultVOS.get(i).getType());         // 型号
            item.createCell(3).setCellValue(resultVOS.get(i).getColor());        // 颜色
            item.createCell(4).setCellValue(resultVOS.get(i).getSize());         // 大小
            item.createCell(5).setCellValue(resultVOS.get(i).getShape());        // 形状
            item.createCell(6).setCellValue(resultVOS.get(i).getSpec());         // 规格
            item.createCell(7).setCellValue(resultVOS.get(i).getOthers());       // 其它
            item.createCell(8).setCellValue(resultVOS.get(i).getCost());         // 成本
            item.createCell(9).setCellValue(resultVOS.get(i).getEstimateMin());  // 底价
            item.createCell(10).setCellValue(resultVOS.get(i).getEstimateMax()); // 顶价
            item.createCell(11).setCellValue(resultVOS.get(i).getPrice());       // 价格
            item.createCell(12).setCellValue(resultVOS.get(i).getTitle());       // 标题
            item.createCell(13).setCellValue(resultVOS.get(i).getUrl());         // 详情页链接
        }
        return workbook;
    }
}
