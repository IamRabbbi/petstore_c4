package com.petstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

@SpringBootTest
class PetstoreApplicationTests {

	Logger log = Logger.getLogger(getClass().getName());

	@Value("${myname}")
	String myname;

	@Test
	void contextLoads() {
	}

	@Test
	void printPropertyValueTest(){

		log.info(myname);
	}



}
