package top.xcck.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.xcck.admin.entity.Menu;
import top.xcck.admin.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface RoleDao extends BaseMapper<Role> {

    Role selectRoleById(@Param("id") Long id);

    void saveRoleMenus(@Param("roleId") Long id, @Param("menus") Set<Menu> menus);

    void dropRoleMenus(@Param("roleId")Long roleId);

    void dropRoleUsers(@Param("roleId")Long roleId);
}