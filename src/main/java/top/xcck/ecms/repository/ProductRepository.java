package top.xcck.ecms.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.xcck.ecms.model.pojo.TbProduct;

public interface ProductRepository extends ElasticsearchRepository<TbProduct, Long> {
}
