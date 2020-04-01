package top.xcck.ecms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.xcck.ecms.model.pojo.TbLibpage;
import top.xcck.ecms.model.pojo.TbProduct;
import top.xcck.ecms.service.ITbProductService;
import top.xcck.ecms.utils.CompleatePrice;

import java.util.List;

/**
 * <p>
 * 商品基本信息表 前端控制器
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ITbProductService productService;
    @Autowired
    private CompleatePrice compleatePrice;

    @RequestMapping(value = "/price")
    public String compleatePrice(){
        IPage<TbProduct> page = new Page<>();
        page.setCurrent(1); //当前页
        page.setSize(50);    //每页条数
        IPage<TbProduct> productPage = productService.page(page);
        List<TbProduct> newList = compleatePrice.getPrice(productPage.getRecords());
        productService.updateBatchById(newList);
        for (int i = 2; i <= productPage.getTotal(); i++) {
            page.setCurrent(i);
            productPage = productService.page(page);
            newList = compleatePrice.getPrice(productPage.getRecords());
            productService.updateBatchById(newList);
            System.out.println("》》》》》》》》》》当前页数是：" + i);
        }
        return "SUCCESS";
    }

}

