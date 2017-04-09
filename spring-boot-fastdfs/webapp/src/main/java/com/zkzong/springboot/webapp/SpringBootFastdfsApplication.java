package com.zkzong.springboot.webapp;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = "com.zkzong.springboot")
@Import(FdfsClientConfig.class)
public class SpringBootFastdfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFastdfsApplication.class, args);
	}
}
