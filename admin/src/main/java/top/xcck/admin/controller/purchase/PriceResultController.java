package top.xcck.admin.controller.purchase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.xcck.admin.annotation.SysLog;
import top.xcck.admin.base.BaseController;
import top.xcck.admin.controller.system.RoleController;

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

}
