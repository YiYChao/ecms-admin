package top.xcck.admin.controller.system;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.entity.Resource;
import top.xcck.admin.util.LayerData;
import top.xcck.admin.util.QiniuFileUtil;
import top.xcck.admin.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/system/resource")
public class ResourceController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("list")
    @SysLog("跳转资源展示列表")
    public String list(){
        return "admin/system/resource/list";
    }

    @RequiresPermissions("sys:resource:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<Resource> list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                    @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                                    ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<Resource> layerData = new LayerData<>();
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                wrapper.like("file_type", type);
            }
            String hash = (String)map.get("hash");
            if(StringUtils.isNotBlank(hash)){
                wrapper.eq("hash",hash);
            }
            String source = (String)map.get("source");
            if(StringUtils.isNotBlank(source)){
                wrapper.eq("source",source);
            }
        }
        Page<Resource> dataPage = resourceService.selectPage(new Page<>(page,limit),wrapper);
        layerData.setCount(dataPage.getTotal());
        layerData.setData(dataPage.getRecords());
        return layerData;
    }

    @RequiresPermissions("sys:resource:delete")
    @PostMapping("delete")
    @SysLog("删除系统资源")
    @ResponseBody
    public RestResponse delete(@RequestParam("ids[]") List<Long> ids){
        if(ids == null || ids.size() == 0){
            return RestResponse.failure("删除ID不能为空");
        }
        List<Resource> rescources = resourceService.selectBatchIds(ids);
        if(rescources == null || rescources.size()==0){
            return RestResponse.failure("请求参数不正确");
        }
        for (Resource rescource : rescources){
            QiniuFileUtil.deleteQiniuP(rescource.getWebUrl());
        }
        resourceService.deleteBatchIds(ids);
        return RestResponse.success();
    }

    @GetMapping("test")
    @SysLog("测试错误请求")
    public String error() throws Exception {
        throw new Exception("测试异常消息");
    }
}
