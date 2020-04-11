package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.xcck.admin.dao.PriceResultDao;
import top.xcck.admin.entity.PriceResult;
import top.xcck.admin.service.PriceResultService;
/**
 * @Description: 定价结果业务层操作接口实现
 * @Author : YiYChao
 * @date : 2020/4/11 11:42
 * @version : V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PriceResultServiceImpl extends ServiceImpl<PriceResultDao, PriceResult> implements PriceResultService {

    @Override
    public void getJDPrice(String path) {
        // TODO
    }
}
