package com.kaos.skynet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan({ "com.kaos.skynet.core.data.mapper",
		"com.kaos.skynet.api.data.his.mapper",
		"com.kaos.skynet.api.data.docare.mapper" })
@SpringBootApplication
@EnableScheduling
public class SkynetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkynetApplication.class, args);
	}

}
