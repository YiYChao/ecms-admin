package top.xcck.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.xcck.admin.entity.Menu;
import top.xcck.admin.entity.VO.ShowMenu;

import java.util.List;
import java.util.Map;

public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> showAllMenusList(Map map);

    List<Menu> getMenus(Map map);

    List<ShowMenu> selectShowMenuByUser(Map<String,Object> map);
}