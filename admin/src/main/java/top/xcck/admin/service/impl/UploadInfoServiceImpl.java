package top.xcck.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import top.xcck.admin.dao.UploadInfoDao;
import top.xcck.admin.entity.UploadInfo;
import top.xcck.admin.service.UploadInfoService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UploadInfoServiceImpl extends ServiceImpl<UploadInfoDao, UploadInfo> implements UploadInfoService {

    @Cacheable(value = "uploadInfoCache",key = "'getinfo'",unless = "#result == null")
    @Override
    public UploadInfo getOneInfo() {
        EntityWrapper<UploadInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag",false);
        return selectOne(wrapper);
    }

    @CacheEvict(value = "uploadInfoCache",key = "'getinfo'")
    @Override
    public void updateInfo(UploadInfo uploadInfo) {
        updateById(uploadInfo);
    }
}
