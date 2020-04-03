package top.xcck.ecms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.xcck.ecms.model.pojo.TbProduct;
import top.xcck.ecms.repository.ProductRepository;
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
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/price")
    public String compleatePrice() {
        IPage<TbProduct> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
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

    @ApiOperation(value = "queryProduct", notes = "查询商品信息")
    @PostMapping("/query")
    public Page<TbProduct> queryProduct(@RequestParam String code, @RequestParam String title,
                                        @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "0") Integer size) {
        System.out.println("收到查询请求" + title + code);
        // 分页查询对构建
        from = from == 0 ? 1 : from;
        size = size == 0 ? 15 : size;
        Pageable pageable = PageRequest.of(from, size);
        // 初始化查询
        if (!Strings.isNullOrEmpty(code) && !Strings.isNullOrEmpty(title)){
            return productRepository.findAll(pageable);
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();     // 布尔查询
        if (!"".equals(code)) {
            boolQuery.must(QueryBuilders.matchPhraseQuery("code", code));
        }
        if (!Strings.isNullOrEmpty(title)) {
            boolQuery.must(QueryBuilders.matchPhraseQuery("title", title));
        }
        //排序查询对象构建
//        FieldSortBuilder order = SortBuilders.fieldSort("released").order(SortOrder.DESC);

        // 设置高亮的前缀和后缀
        String preTag = "<font color='#dd4b39'>";
        String postTag = "</font>";

        // 构建查询
        SearchQuery query = new NativeSearchQueryBuilder()
                .withHighlightFields(new HighlightBuilder.Field("title").preTags(preTag).postTags(postTag))
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();
        Page<TbProduct> products = productRepository.search(query);
        return products;
    }

    @RequestMapping(value = "/test")
    public String test(String title, String code){
        System.out.println("接收到请求" + title + code);
        return "接收到请求" + title + code;
    }

}

