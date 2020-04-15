package top.xcck.admin.controller.purchase;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.controller.system.RoleController;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.entity.PriceResult;
import top.xcck.admin.entity.Resource;
import top.xcck.admin.util.LayerData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 定价结果相关的前端控制器
 * @Author : YiYChao
 * @date : 2020/4/11 10:28
 * @version : V1.0
 */
@Controller
@RequestMapping(value = "/admin/purchase/price/result")
public class PriceResultController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/list")
    @SysLog("跳转定价结果列表")
    public String list(){
        return "admin/purchase/priceResult";
    }

    @PostMapping("/list")
    @ResponseBody
    @SysLog("搜索定价结果")
    public LayerData<PriceResource> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                         @RequestParam(value = "limit",defaultValue = "1")Integer limit,
                                         Integer sid, String brand, String type){
        LayerData<PriceResource> layerData = new LayerData<>();
        if((sid == null || sid == 0) && StringUtils.isBlank(brand) && StringUtils.isBlank(type)){
            return layerData;
        }
        String webUrl = "";
        if (sid != null && sid > 0){
            Resource resource = resourceService.selectById(sid);        // 通过资源文件的Id查询资源的全路径
            if (resource.getDelFlag()){ // 资源文件已删除
                return layerData;
            }
            webUrl = resource.getWebUrl();
        }
        // 创建资源的实体的查询条件对象
        EntityWrapper<PriceResource> resourceWrapper = new EntityWrapper<>();
        // 通过资源的全路径查询定价文件的实体内容
        if (!"".equals(webUrl)){
            resourceWrapper.eq("web_url",webUrl);
        }
        if (StringUtils.isNotBlank(brand)) {        // 品牌
            resourceWrapper.eq("brand", brand);
        }
        if (StringUtils.isNotBlank(type)){          // 型号，模糊查询
            resourceWrapper.like("type", type);
        }
        Page<PriceResource> priceResourcePage = priceResourceService.selectPage(new Page<PriceResource>(page, limit), resourceWrapper);
        List<PriceResource> priceResources = priceResourcePage.getRecords();

        // 通过定价实体的id查询定价实体结果列表
        if (priceResourcePage.getTotal() == 0){
            return layerData;       // 没有找到资源实体数据
        }
        EntityWrapper<PriceResult> resultWrapper = new EntityWrapper<>();
        resultWrapper.eq("source_id", priceResources.get(0).getId());
        List<PriceResult> priceResults = priceResultService.selectList(resultWrapper);

        priceResources.get(0).setResultList(priceResults);      // 将定价结果列表封装进定价实体

        layerData.setCount(priceResourcePage.getTotal());
        layerData.setData(priceResources);

        return layerData;   // 返回查询结果
    }

    @ResponseBody
    @PostMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,Integer[] ids, Integer sid) throws IOException {
        // 设置MIME类型
        response.setHeader("content-type", "application/vbn.ms-excel");
        // 设置返回内容以附近的方式下载，并且指定文件名
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("定价结果"+timeStamp+".xls", "utf-8"));

        String webUrl = "";
        Set idSet = new HashSet();
        if (sid != null && sid > 0){
            Resource resource = resourceService.selectById(sid);        // 通过资源文件的Id查询资源的全路径
            webUrl = resource.getWebUrl();
        }else if (ids != null && ids.length > 0){
            idSet = new HashSet<Integer>(Arrays.asList(ids));
        }
        Workbook workbook = priceResultService.exportExcel(new ArrayList<>(idSet), webUrl);       // 进行查询
        if (workbook == null){
            return;
        }
        // 将Excel内存对象写入OutputStream
        workbook.write(response.getOutputStream());
        // 刷新缓存
        response.getOutputStream().flush();
    }
}
