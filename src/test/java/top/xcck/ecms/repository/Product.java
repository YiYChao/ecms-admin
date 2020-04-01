package top.xcck.ecms.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.xcck.ecms.model.pojo.TbProduct;
import top.xcck.ecms.service.ITbProductService;

import javax.sound.midi.SoundbankResource;

/**
 *  @Description: SpringBoot 集成ES测试
 *  @author YiYChao
 *  @Date: 2020/4/1 14:59
 *  @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Product {

    @Autowired
    private ITbProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test(){
//        TbProduct one = productService.getOne(new QueryWrapper<TbProduct>().lambda().eq(TbProduct::getId, 1));
//        productRepository.index(one);

        System.out.println(productRepository.findById(1L));
    }

}
