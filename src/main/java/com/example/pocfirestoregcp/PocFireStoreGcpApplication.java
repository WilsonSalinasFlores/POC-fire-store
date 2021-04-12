package com.example.pocfirestoregcp;


import com.example.pocfirestoregcp.config.DataBase;
import com.example.pocfirestoregcp.model.City;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.ExecutionException;
@SpringBootApplication
public class PocFireStoreGcpApplication {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(PocFireStoreGcpApplication.class, args);

		saveData();
		readData();
		saveCity();
		saveCityWithOutId();
	}
	private static void saveData() throws ExecutionException, InterruptedException {
		DocumentReference docRef = DataBase.getDb().collection("users").document("alovelace2");
// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("first", "Ivan");
		data.put("last", "Salinas");
		data.put("born", 1815);
		data.put("date",new Date());
//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
// ...
// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());
	}
	private static void readData() throws ExecutionException, InterruptedException {
// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = DataBase.getDb().collection("users").get();
// ...
// query.get() blocks on response
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			System.out.println("User: " + document.getId());
			System.out.println("First: " + document.getString("first"));
			if (document.contains("middle")) {
				System.out.println("Middle: " + document.getString("middle"));
			}
			System.out.println("Last: " + document.getString("last"));
			System.out.println("Born: " + document.getLong("born"));
		}
	}
	private static void  saveCity() throws ExecutionException, InterruptedException {
		City city = new City("Pasadena", "CA", "USA", false, 3900000L,
				Arrays.asList("west_coast", "socal"));
		ApiFuture<WriteResult> future = DataBase.getDb().collection("cities").document("LA1").set(city);
// block on response if required
		System.out.println("Update time : " + future.get().getUpdateTime());
	}
	private static void  saveCityWithOutId() throws ExecutionException, InterruptedException {
		Map<String, Object> data = new HashMap<>();
		data.put("name", "Tokyo");
		data.put("country", "Japan");
		ApiFuture<DocumentReference> addedDocRef = DataBase.getDb().collection("cities").add(data);
		System.out.println("Added document with ID: " + addedDocRef.get().getId());
	}
}