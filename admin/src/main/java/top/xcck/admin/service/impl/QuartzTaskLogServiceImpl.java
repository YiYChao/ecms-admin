package top.xcck.admin.service.impl;

import top.xcck.admin.entity.QuartzTaskLog;
import top.xcck.admin.dao.QuartzTaskLogDao;
import top.xcck.admin.service.QuartzTaskLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class QuartzTaskLogServiceImpl extends ServiceImpl<QuartzTaskLogDao, QuartzTaskLog> implements QuartzTaskLogService {

}
