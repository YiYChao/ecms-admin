package top.xcck.admin.service;

import top.xcck.admin.entity.Resource;
import com.baomidou.mybatisplus.service.IService;
/**
 * <p>
 * 系统资源 服务类
 * </p>
 *
 * @author wangl
 * @since 2018-01-14
 */
public interface ResourceService extends IService<Resource> {

    int getCountByHash(String hash);

    Resource getRescourceByHash(String hash);

}
