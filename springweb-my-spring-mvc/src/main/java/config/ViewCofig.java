package config;

import annotation.Bean;
import annotation.ComponentScan;
import annotation.Configuration;
import view.Fix;

@Configuration
@ComponentScan(value = { "view" })
public class ViewCofig {
	@Bean
	public Fix fix() {
		return new Fix("",".jsp","/index.jsp");
	}
}
