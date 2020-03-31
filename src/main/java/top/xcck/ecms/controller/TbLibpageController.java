package top.xcck.ecms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.xcck.ecms.model.pojo.TbLibpage;
import top.xcck.ecms.model.pojo.TbProduct;
import top.xcck.ecms.service.ITbLibpageService;
import top.xcck.ecms.service.ITbProductService;
import top.xcck.ecms.utils.ExtractProduct;

import java.util.List;

/**
 * <p>
 * 商品内容原始信息表 前端控制器
 * </p>
 *
 * @author YiYChao
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/page")
public class TbLibpageController {

    @Autowired
    private ITbLibpageService libpageService;
    @Autowired
    private ITbProductService productService;
    @Autowired
    ExtractProduct extractProduct;

    @GetMapping(value = "/list")
    public IPage<TbLibpage> getList(){
        //需要在Config配置类中配置分页插件
        IPage<TbLibpage> page = new Page<>();
        page.setCurrent(328); //当前页
        page.setSize(1);    //每页条数
        IPage<TbLibpage> libpageIPage = libpageService.page(page);
        TbLibpage libpage = libpageIPage.getRecords().get(0);
        List<TbProduct> productList = extractProduct.extract(libpage.getPage());
        insertProduct(productList);
        for (int i = 2; i <= libpageIPage.getTotal(); i++) {
            page.setCurrent(i);
            libpageIPage = libpageService.page(page);
            libpage = libpageIPage.getRecords().get(0);
            productList = extractProduct.extract(libpage.getPage());;
            insertProduct(productList);
        }
        return libpageIPage;
    }

    private void insertProduct(List<TbProduct> productList){
        for(TbProduct product : productList){
            TbProduct dbProduct = productService.getOne(new QueryWrapper<TbProduct>().lambda().eq(TbProduct::getTitle, product.getTitle()));
            if (dbProduct != null){
                System.out.println("更新记录：" + dbProduct.getId());
                product.setId(dbProduct.getId());
            }
            productService.saveOrUpdate(product);
        }
    }
}

