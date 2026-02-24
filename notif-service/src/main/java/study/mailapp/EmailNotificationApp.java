package study.mailapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class EmailNotificationApp {

	public static void main(String[] args) {
		SpringApplication.run(EmailNotificationApp.class, args);
	}

}
