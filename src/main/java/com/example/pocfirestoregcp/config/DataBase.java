package com.example.pocfirestoregcp.config;
import com.google.cloud.firestore.Firestore;
public class DataBase {
    private static Firestore db;

    public static Firestore getDb() {
        return db;
    }

    public static void setDb(Firestore db) {
        DataBase.db = db;
    }

}
