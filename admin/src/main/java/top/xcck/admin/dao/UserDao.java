package top.xcck.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import top.xcck.admin.entity.Role;
import top.xcck.admin.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

public interface UserDao extends BaseMapper<User> {
	User selectUserByMap(Map<String, Object> map);

	void saveUserRoles(@Param("userId")Long id, @Param("roleIds")Set<Role> roles);

	void dropUserRolesByUserId(@Param("userId")Long userId);

	Map selectUserMenuCount();
}