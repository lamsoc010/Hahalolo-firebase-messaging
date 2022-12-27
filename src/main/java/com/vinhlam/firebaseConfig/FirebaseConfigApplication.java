package com.vinhlam.firebaseConfig;

import java.io.FileInputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class FirebaseConfigApplication {

	public static void main(String[] args) throws Exception {
	    SpringApplication.run(FirebaseConfigApplication.class, args);
	  }

}
