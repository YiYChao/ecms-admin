package top.xcck.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.controller.system.RoleController;
import top.xcck.admin.entity.Product;
import top.xcck.admin.util.LayerData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

/**
 *  @Description: 商品基本信息的前端控制器
 *  @author YiYChao
 *  @Date: 2020/4/8 11:32
 *  @version V1.0
 */
@Controller
@RequestMapping(value = "/admin/product/")
public class ProductController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @GetMapping(value = "/list")
    @SysLog(value = "跳转到商品列表页面")
    public String list(){
        return "/admin/product/list";
    }

    @RequiresPermissions("admin:product:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Product> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                   @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                   ServletRequest request){
        LayerData<Product> layerData = new LayerData<>();
        Page<Product> productPage = productService.selectPage(new Page<Product>(page, limit));
        layerData.setCount(productPage.getTotal());
        layerData.setData(productPage.getRecords());
        return layerData;
    }
}
