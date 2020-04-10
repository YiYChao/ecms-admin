package top.xcck.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.xcck.admin.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogDao extends BaseMapper<Log> {

    List<Map> selectSelfMonthData();
}
