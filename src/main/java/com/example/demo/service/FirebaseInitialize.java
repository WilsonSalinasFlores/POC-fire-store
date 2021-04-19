package com.example.demo.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;


@Service
public class FirebaseInitialize {
    @PostConstruct
    public void initialize()  {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("./poc-firestore-efx-firebase-adminsdk-dau4n-7c3af7505a.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
