package com.vinhlam.firebaseConfig.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;

@Service
public class FCMInitializer {

	@Value("${app.firebase-configuration-file}")
	private String firebaseConfigPath;
	Logger logger = LoggerFactory.getLogger(FCMInitializer.class);

	@PostConstruct
	public void initialize() throws FirebaseMessagingException {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(
							GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
					.build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				String registrationToken = "eSawRx5t55iA8WSuRV47-Q:APA91bGLYQ004VdFr6ug_aqhPNtqCHCm-QueOEWhG9G3lCCLFd05YyoDGZNDcIqgQ2zhATXA90NJAphIzeIuMCmjso2H8-m8IyDWKuKbYZcwLxlgb_P6--9t0g_W7J7xSngkB_DFM8mW";
				// See documentation on defining a message payload.
				Message message = Message.builder().putData("score", "850").putData("time", "2:45")
						.setToken(registrationToken).build();

				// Send a message to the device corresponding to the provided
				// registration token.
				String response = FirebaseMessaging.getInstance().send(message);
				// Response is a message ID string.
				System.out.println("Successfully sent message: " + response);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}