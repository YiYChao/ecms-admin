package top.xcck.ecms.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.xcck.ecms.model.pojo.TbProduct;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<TbProduct, Long> {

//    List<TbProduct> findByTitleOrOrBrandOrCode(String title, String brand, String code, Pageable pageable);
}
