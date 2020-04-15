package top.xcck.admin.controller.purchase;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.base.MySysUser;
import top.xcck.admin.controller.system.RoleController;
import top.xcck.admin.entity.PriceResource;
import top.xcck.admin.entity.Resource;
import top.xcck.admin.util.LayerData;
import top.xcck.admin.util.RestResponse;

import javax.servlet.ServletRequest;
import java.util.List;

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
                                    ServletRequest request, String remarks){
        LayerData<Resource> layerData = new LayerData<>();
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();        // 设置查询条件
        wrapper.eq("create_by", MySysUser.id());    // 自己上传的文件
        wrapper.eq("del_flag", 0);      // 未删除的文件
        wrapper.like("file_type", ".xls");
        if (StringUtils.isNotBlank(remarks)){
            wrapper.like("remarks", remarks);   // 设置文件名称的模糊查询
        }
        Page<Resource> productPage = resourceService.selectPage(new Page<Resource>(page, limit), wrapper);
        layerData.setCount(productPage.getTotal());
        layerData.setData(productPage.getRecords());
        return layerData;
    }

    @RequiresPermissions("admin:purchase:price:resource")
    @GetMapping("/parse")
    @SysLog("解析定价文件")
    @ResponseBody
    public RestResponse list(@RequestParam(value = "path") String path){
        // TODO 解析网络文件
        try {
            // 将解析的数据加入资源文件
            priceResourceService.parseExcel(path);
            // 从数据库查找出来上面表格解析到数据库的记录
            List<PriceResource> resourceList =  priceResourceService.findByWebUrl(path);
            // 从资源文件读取数据，抓取京东自营价格，根据型号过滤sku，sku批量新增数据库
            resourceList = priceResultService.getJDPrice(resourceList);
            // 更新资源实体的搜索链接
            priceResourceService.updateSearchUrl(resourceList);
            return RestResponse.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.failure("解析失败，系统异常");
        }
    }

    @RequiresPermissions("admin:purchase:price:resource:delete")
    @PostMapping("/delete")
    @SysLog("删除询价文件")
    @ResponseBody
    public RestResponse deleteResource(@RequestParam(value = "sid") Long sid){
        if (sid == null || sid == 0){
            return RestResponse.failure("请选择要删除的文件！");
        }
        Resource resource = resourceService.selectById(sid);        // 找出要删除的资源实体
        if (resource == null){      // 资源不存在
            return RestResponse.failure("操作异常，删除失败！");
        }
        resource.setDelFlag(true);      // 进行逻辑删除
        boolean delete = resourceService.updateById(resource);
        if (delete){
            return RestResponse.success("删除成功！");
        }
        return RestResponse.failure("删除失败");
    }

}
