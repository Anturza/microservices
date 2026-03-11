package study.mailapp;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import study.mailapp.services.EmailService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
class EmailNotificationAppTests {

	private final String TO_EMAIL = "test@mail.ru";
	private final String SUBJECT = "Информация тестового сервиса";
	private final String BODY = "Ваш аккаунт был успешно создан";

	@Autowired
	private EmailService emailService;

	@MockitoBean
	private JavaMailSender mailSender;

	@Test
	void testSendSimpleEmailWithCorrectPayloadToUser() {

		emailService.sendSimpleEmail(TO_EMAIL, SUBJECT, BODY);

		ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mailSender).send(messageCaptor.capture());

		SimpleMailMessage sentMessage = messageCaptor.getValue();
		assertEquals(TO_EMAIL, sentMessage.getTo()[0]);
		assertEquals(SUBJECT, sentMessage.getSubject());
		assertEquals(BODY, sentMessage.getText());
	}

}
