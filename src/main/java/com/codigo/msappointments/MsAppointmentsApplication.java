package com.codigo.msappointments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.codigo.*")
public class MsAppointmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAppointmentsApplication.class, args);
	}

}
