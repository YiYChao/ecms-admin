package top.xcck.admin.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import top.xcck.admin.dao.ProductDao;
import top.xcck.admin.entity.Product;
import top.xcck.admin.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
}
