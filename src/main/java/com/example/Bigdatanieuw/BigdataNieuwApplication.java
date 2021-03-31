package com.example.Bigdatanieuw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BigdataNieuwApplication {
	private static final Logger log = LoggerFactory.getLogger(BigdataNieuwApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BigdataNieuwApplication.class, args);
	}
}
