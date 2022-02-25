package com.kaos.his;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.kaos.his.mapper")
@SpringBootApplication
// @EnableScheduling
public class HisApplication {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

}
