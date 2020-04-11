package top.xcck.admin.base;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.xcck.admin.entity.User;
import top.xcck.admin.realm.AuthRealm;
import top.xcck.admin.service.*;

public class BaseController {
	
	public User getCurrentUser() {
		AuthRealm.ShiroUser shiroUser = (AuthRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(shiroUser == null) {
			return null;
		}
		User loginUser = userService.selectById(shiroUser.getId());
		return loginUser;
	}

	@Autowired
	protected UserService userService;

	@Autowired
	protected MenuService menuService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected DictService dictService;

	@Autowired
	protected ResourceService resourceService;

	@Autowired
	protected TableService tableService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	protected LogService logService;

	@Autowired
	protected QuartzTaskService quartzTaskService;

	@Autowired
	protected QuartzTaskLogService quartzTaskLogService;

	@Autowired
	protected UploadInfoService uploadInfoService;

	@Autowired
	protected ProductService productService;

	@Autowired
	protected PriceResourceService priceResourceService;

	@Autowired
	protected PriceResultService priceResultService;
}
