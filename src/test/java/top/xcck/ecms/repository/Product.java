package top.xcck.ecms.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import top.xcck.ecms.model.pojo.TbProduct;
import top.xcck.ecms.service.ITbProductService;

import javax.sound.midi.SoundbankResource;
import java.util.List;

/**
 * @author YiYChao
 * @version V1.0
 * @Description: SpringBoot 集成ES测试
 * @Date: 2020/4/1 14:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Product {

    @Autowired
    private ITbProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test() {
        List<TbProduct> list = productService.list();
        for (int i = 0; i < 200; i++) {
            productRepository.index(list.get(i));
        }

//        TbProduct one = productService.getOne(new QueryWrapper<TbProduct>().lambda().eq(TbProduct::getId, 1L));
//        productRepository.index(one);
//        productRepository.deleteById(1L);
//        System.out.println(productRepository.findById(1L));
//        Pageable pageable
//        List<TbProduct> productList = productRepository.findByTitleOrOrBrandOrCode("", "", "11774386207");
//        for (TbProduct product : productList){
//            System.out.println(product);
//        }
    }

    public void query(){
        String code = "",title = "";
        int from = 0, size = 0;
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();     // 布尔查询
        if (!"".equals(code)){
            boolQuery.must(QueryBuilders.matchPhraseQuery("code", code));
        }
        if (!Strings.isNullOrEmpty(title)){
            boolQuery.must(QueryBuilders.matchPhraseQuery("title", title));
        }
        from = from == 0 ? 1 : from;
        size = size == 0 ? 15 : size;
        Pageable pageable = PageRequest.of(from, size);

        // 构建查询
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(pageable)
                .build();

        Page<TbProduct> productPage = productRepository.search(query);
    }

}
