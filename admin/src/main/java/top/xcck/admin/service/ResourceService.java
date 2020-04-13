package top.xcck.admin.service;

import com.baomidou.mybatisplus.service.IService;
import top.xcck.admin.entity.Resource;

public interface ResourceService extends IService<Resource> {

    int getCountByHash(String hash);

    Resource getRescourceByHash(String hash);

}
