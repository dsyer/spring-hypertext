package org.springframework.experimental.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}

	@Test
	void contextLoads() {
	}

}

@SpringBootApplication
class DemoApplication {}