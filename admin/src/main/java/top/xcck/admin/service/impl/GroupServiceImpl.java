package top.xcck.admin.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import top.xcck.admin.dao.GroupDao;
import top.xcck.admin.entity.Group;
import top.xcck.admin.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GroupServiceImpl extends ServiceImpl<GroupDao, Group> implements GroupService {
	
}
