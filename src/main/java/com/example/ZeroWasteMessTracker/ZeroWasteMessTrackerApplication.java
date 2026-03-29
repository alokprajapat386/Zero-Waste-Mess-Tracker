package com.example.ZeroWasteMessTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZeroWasteMessTrackerApplication {



	public static void main(String[] args) {
		System.setProperty("spring.output.ansi.enabled", "NEVER");
		SpringApplication.run(ZeroWasteMessTrackerApplication.class, args);
	}

}
