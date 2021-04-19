package com.example.demo.dao;

import com.example.demo.pojo.ConfigurationPojo;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConfigurationDao {
    public ConfigurationPojo findByApplicationAndIndentifierIgnoringDeletedOn(String application, String identifier)
            throws ExecutionException, InterruptedException {
        ConfigurationPojo configurationPojo = null;

        // Create a reference to the configurations collection
        CollectionReference configurations = com.google.firebase.cloud.FirestoreClient.getFirestore().collection("CONFIGURATIONS");
        // Create a query against the collection.
        Query query = configurations.whereEqualTo("identifier", identifier).whereEqualTo("application", application);
        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<ConfigurationPojo> configurationPojoList= toList(querySnapshot.get().getDocuments());
        if (configurationPojoList.size()>0)
            configurationPojo= configurationPojoList.get(0);

        return configurationPojo;
    }
    public List <ConfigurationPojo> findByApplication(String application)
            throws ExecutionException, InterruptedException {
        // Create a reference to the configurations collection
        CollectionReference configurations = com.google.firebase.cloud.FirestoreClient.getFirestore().collection("CONFIGURATIONS");
        // Create a query against the collection.
        Query query = configurations.whereEqualTo("application", application);
        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<ConfigurationPojo> configurationPojoList= toList(querySnapshot.get().getDocuments());
        return configurationPojoList;
    }

    public ConfigurationPojo findByApplicationAndIndentifier(String application, String identifier)
            throws ExecutionException, InterruptedException {
        ConfigurationPojo configurationPojo = null;

        // Create a reference to the configurations collection
        CollectionReference configurations = com.google.firebase.cloud.FirestoreClient.getFirestore().collection("CONFIGURATIONS");
        // Create a query against the collection.
        Query query = configurations.whereEqualTo("identifier", identifier).whereEqualTo("application", application).whereEqualTo("deletedOn",null);
        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<ConfigurationPojo> configurationPojoList= toList(querySnapshot.get().getDocuments());
        if (configurationPojoList.size()>0)
            configurationPojo= configurationPojoList.get(0);
        return configurationPojo;
    }
    public ConfigurationPojo findByApplicationAndIndentifierAndVersion(String application, String identifier,Integer version)
            throws ExecutionException, InterruptedException {
        ConfigurationPojo configurationPojo = null;

        // Create a reference to the configurations collection
        CollectionReference configurations = com.google.firebase.cloud.FirestoreClient.getFirestore().collection("CONFIGURATIONS");
        // Create a query against the collection.
        Query query = configurations.whereEqualTo("identifier", identifier).whereEqualTo("application", application).whereEqualTo("version",version);
        // retrieve  query results asynchronously using query.get()
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<ConfigurationPojo> configurationPojoList= toList(querySnapshot.get().getDocuments());
        if (configurationPojoList.size()>0)
            configurationPojo= configurationPojoList.get(0);
        return configurationPojo;
    }
    private List<ConfigurationPojo> toList( List<QueryDocumentSnapshot> documents)
    {
        List<ConfigurationPojo> result=new ArrayList<>();
        for (DocumentSnapshot document :documents) {
            if (document.exists()) {
                result.add(document.toObject(ConfigurationPojo.class));
            }
        }
        Collections.sort(result, new Comparator<ConfigurationPojo>() {
            @Override
            public int compare(ConfigurationPojo o1, ConfigurationPojo o2) {
                return Integer.valueOf(o2.getVersion().compareTo(o1.getVersion()));
            }
        });
        return result;
    }


    public ConfigurationPojo save(ConfigurationPojo configuration) throws ExecutionException, InterruptedException {

        ConfigurationPojo result =null;
        boolean isNew=true;
        Firestore dbFirestore = com.google.firebase.cloud.FirestoreClient.getFirestore();
        // Create a reference to the configurations collection
        DocumentReference addedDocRef =null;
        if (configuration.getId()==null) {
            addedDocRef = dbFirestore.collection("CONFIGURATIONS").document();
        }
        else {
            isNew = false;
            addedDocRef = dbFirestore.collection("CONFIGURATIONS").document(configuration.getId());
        }
        ApiFuture<WriteResult> writeResult = addedDocRef.set(configuration);
        //Obtener datos
        DocumentReference docRef = dbFirestore.collection("CONFIGURATIONS").document(addedDocRef.getId());
        if (isNew) {
            configuration.setId(addedDocRef.getId());
            //Update Id
            addedDocRef.set(configuration);
        }
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if(document.exists()) {
            result = document.toObject(ConfigurationPojo.class);
        }
        return result;
    }
}
