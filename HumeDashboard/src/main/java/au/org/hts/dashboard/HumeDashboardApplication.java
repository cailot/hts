package au.org.hts.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages="au.org.hts.dashboard")
public class HumeDashboardApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(HumeDashboardApplication.class, args); 
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(HumeDashboardApplication.class);
	}
}
