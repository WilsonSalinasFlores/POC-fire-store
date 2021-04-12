package com.example.pocfirestoregcp.dao;


import com.example.pocfirestoregcp.config.DataBase;
import com.example.pocfirestoregcp.pojo.ConfigurationPojo;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;


import java.util.concurrent.ExecutionException;

public interface ConfiguratorDao  {

    default ConfigurationPojo findByApplicationAndIndentifierIgnoringDeletedOn(String application, String identifier) throws ExecutionException, InterruptedException {
        ConfigurationPojo configurationPojo = null;
        // Create a reference to the configurations collection
        CollectionReference configurations = DataBase.getDb().collection("CONFIGURATIONS");
// Create a query against the collection.
        Query query = configurations.whereEqualTo("IDENTIFIER", identifier).whereEqualTo("APPLICATION",application).orderBy("CREATED_ON", Query.Direction.DESCENDING).limit(1);
// retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            configurationPojo= document.toObject(ConfigurationPojo.class);
        }
        return configurationPojo;
    }

    default ConfigurationPojo save(ConfigurationPojo configuration) throws ExecutionException, InterruptedException {
        ConfigurationPojo result =null;
        DocumentReference addedDocRef = DataBase.getDb().collection("CONFIGURATIONS").document();
        ApiFuture<WriteResult> writeResult = addedDocRef.set(configuration);
        //Obtener datos
        DocumentReference docRef = DataBase.getDb().collection("CONFIGURATIONS").document(addedDocRef.getId());

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if(document.exists()) {
            result = document.toObject(ConfigurationPojo.class);
        }
        return result;
    }

}
