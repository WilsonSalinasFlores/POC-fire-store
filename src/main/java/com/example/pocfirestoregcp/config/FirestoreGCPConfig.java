package com.example.pocfirestoregcp.config;

import com.example.pocfirestoregcp.config.DataBase;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirestoreGCPConfig {
    @Bean
    public void initFireStore() throws IOException {
        FileInputStream servicesAcount = new FileInputStream("D:/APPS Java/poc-firestore-efx-firebase-adminsdk-dau4n-7c3af7505a.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(servicesAcount);
        String projectId="poc-firestore-efx";
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);
        DataBase.setDb(FirestoreClient.getFirestore());
    }

}
