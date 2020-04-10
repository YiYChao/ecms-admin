package top.xcck.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("top.xcck.admin.dao")
public class XcAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(XcAdminApplication.class, args);
	}
}
