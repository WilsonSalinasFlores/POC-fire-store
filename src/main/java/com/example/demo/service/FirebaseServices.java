package com.example.demo.service;

import com.example.demo.objects.Person;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseServices {
    public String saveUserDetail(Person person) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture= dbFirestore.collection("users")
                .document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public Person getUserDetails(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference=dbFirestore.collection("users").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document=future.get();
        Person person =null;
        if (document.exists())
            person =document.toObject(Person.class);
        return person;
    }
    public String updateUserDetail(Person person) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture= dbFirestore.collection("users")
                .document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }
    public String deleteUserDetail(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture= dbFirestore.collection("users").document(name).delete();
        return "Documento eliminado "+ name;
    }

}
