package config;

import annotation.ComponentScan;
import annotation.Configuration;

@Configuration
@ComponentScan(value = { "dao" })
public class DataSourceConfig {
	static public String driverClass="com.mysql.cj.jdbc.Driver";
	static public String jdbcURL="jdbc:mysql://localhost/myspringmvc?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
	static public String user="root";
	static public String pwd="123456";
}
