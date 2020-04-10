package top.xcck.admin.config;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xcck.admin.freemark.SysUserTempletModel;
import top.xcck.admin.freemark.SystemDirective;

import javax.annotation.PostConstruct;

/**
 * Created by wangl on 2017/11/26.
 * todo:
 */
@Component
public class FreemarkerConfig {

    @Autowired
    private Configuration configuration;

    @Autowired
    private SystemDirective systemDirective;

    @Autowired
    private SysUserTempletModel sysUserTempletModel;


    @PostConstruct
    public void setSharedVariable() {
        //系统字典标签
        configuration.setSharedVariable("my",systemDirective);
        //获取系统用户信息
        configuration.setSharedVariable("sysuser",sysUserTempletModel);
    }
}
