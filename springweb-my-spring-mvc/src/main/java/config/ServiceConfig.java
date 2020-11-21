package config;

import annotation.ComponentScan;
import annotation.Configuration;

@Configuration
@ComponentScan(value = { "service" })
public class ServiceConfig {

}
