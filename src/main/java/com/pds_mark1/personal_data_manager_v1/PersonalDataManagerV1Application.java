package com.pds_mark1.personal_data_manager_v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.pds_mark1.personal_data_manager_v1")
public class PersonalDataManagerV1Application {

	public static void main(String[] args) {
		SpringApplication.run(PersonalDataManagerV1Application.class, args);

		System.out.println("\n\n\n The Application Has Started \n\n\n");
	}

}
