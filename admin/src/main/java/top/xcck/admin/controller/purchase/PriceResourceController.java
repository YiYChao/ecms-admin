package top.xcck.admin.controller.purchase;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.base.MySysUser;
import top.xcck.admin.controller.system.RoleController;
import top.xcck.admin.entity.Resource;
import top.xcck.admin.util.LayerData;
import top.xcck.admin.util.RestResponse;

import javax.servlet.ServletRequest;

/**
 * @Description: 定价资源文件的相关前端控制器
 * @Author : YiYChao
 * @date : 2020/4/11 10:18
 * @version : V1.0
 */
@Controller
@RequestMapping(value = "/admin/purchase/price/resource")
public class PriceResourceController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/list")
    @SysLog("跳转定价资源文件列表")
    public String list(){
        return "admin/purchase/priceResource";
    }

    @RequiresPermissions("admin:purchase:price:resource")
    @PostMapping("/list")
    @ResponseBody
    public LayerData<Resource> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        LayerData<Resource> layerData = new LayerData<>();
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();        // 设置查询条件
        wrapper.eq("create_by", MySysUser.id());
        wrapper.like("file_type", ".xls");

        Page<Resource> productPage = resourceService.selectPage(new Page<Resource>(page, limit), wrapper);
        layerData.setCount(productPage.getTotal());
        layerData.setData(productPage.getRecords());
        return layerData;
    }

    @RequiresPermissions("admin:purchase:price:resource")
    @GetMapping("/parse")
    @ResponseBody
    public RestResponse list(@RequestParam(value = "path") String path){
        // TODO 解析网络文件
        // 将解析的数据加入资源文件
        priceResourceService.parseExcel(path);
        // 从资源文件读取数据，抓取京东自营价格，根据型号过滤sku，sku批量新增数据库
        priceResultService.getJDPrice(path);
        return RestResponse.success();
    }

}
