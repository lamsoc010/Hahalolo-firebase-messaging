package com.vinhlam.firebaseConfig.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.vinhlam.firebaseConfig.entity.Notification;

@RestController
@RequestMapping("/api/notifications/")
public class SendMessageController {

	
//	API send notification to one device: http://localhost:8080/api/notifications/sendNotificationToOneDevice
	@PostMapping("/sendNotificationToOneDevice")
	public ResponseEntity<?> sendMessageToOne(@RequestBody Notification notification) throws FirebaseMessagingException {
		String registrationToken = "eSawRx5t55iA8WSuRV47-Q:APA91bGLYQ004VdFr6ug_aqhPNtqCHCm-QueOEWhG9G3lCCLFd05YyoDGZNDcIqgQ2zhATXA90NJAphIzeIuMCmjso2H8-m8IyDWKuKbYZcwLxlgb_P6--9t0g_W7J7xSngkB_DFM8mW";
		// See documentation on defining a message payload.
		com.google.firebase.messaging.Notification notification1 = com.google.firebase.messaging.Notification.builder()
			    .setTitle("Title")
			    .setBody("Body")
			    .build();
		Message message = Message.builder().putData(notification.getTitle(), notification.getBody()).setNotification(notification1)
				.setToken(registrationToken).build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = FirebaseMessaging.getInstance().send(message);
		if(!response.isEmpty()) {
			System.out.println("Successfully sent message: " + response);
			return ResponseEntity.ok("Send notification success");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Send notification fail");
		}
		
	}
		
//	API send notification to many device: http://localhost:8080/api/notifications/sendNotificationToManyDevice
	@PostMapping("/sendNotificationToManyDevice")
	public ResponseEntity<?> sendMessageToMany(@RequestBody Notification notification) throws FirebaseMessagingException {
		// These registration tokens come from the client FCM SDKs.
		List<String> registrationTokens = Arrays.asList(
		    "eSawRx5t55iA8WSuRV47-Q:APA91bGLYQ004VdFr6ug_aqhPNtqCHCm-QueOEWhG9G3lCCLFd05YyoDGZNDcIqgQ2zhATXA90NJAphIzeIuMCmjso2H8-m8IyDWKuKbYZcwLxlgb_P6--9t0g_W7J7xSngkB_DFM8mW",
		    
		    "e0rgnXRKBxvxvKxzFgNA53:APA91bEel7OLGNBMYtKrh4NrvpsAjjuSmTtNno2-eWJ6xvzxJva9ex6DpRK0sqgIrBieykoQLERFlvR-Pt9WAkeZ1qimxroXIhNfM6SJH5LU62VuvVLQFoWF2awSxJ11a0cERZJ-548J"
		);

		MulticastMessage message = MulticastMessage.builder()
		    .putData(notification.getTitle(), notification.getBody())
		    .addAllTokens(registrationTokens)
		    .build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		if (response.getFailureCount() > 0) {
		  List<SendResponse> responses = response.getResponses();
		  List<String> failedTokens = new ArrayList<>();
		  for (int i = 0; i < responses.size(); i++) {
		    if (!responses.get(i).isSuccessful()) {
		      // The order of responses corresponds to the order of the registration tokens.
		      failedTokens.add(registrationTokens.get(i));
		    }
		  }

		  System.out.println("List of tokens that caused failures: " + failedTokens);
		}
		if(response.getSuccessCount() != 0) {
			System.out.println("Successfully sent message: " + response);
			return ResponseEntity.ok("Send notification success");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Send notification fail");
		}
		
	}
}
