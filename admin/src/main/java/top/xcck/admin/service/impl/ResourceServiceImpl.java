package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import top.xcck.admin.entity.Resource;
import top.xcck.admin.dao.ResourceDao;
import top.xcck.admin.service.ResourceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements ResourceService {

    @Override
    public int getCountByHash(String hash) {
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        wrapper.eq("hash",hash);
        return selectCount(wrapper);
    }

    @Override
    public Resource getRescourceByHash(String hash) {
        EntityWrapper<Resource> wrapper = new EntityWrapper<>();
        wrapper.eq("hash",hash);
        return selectOne(wrapper);
    }
}
