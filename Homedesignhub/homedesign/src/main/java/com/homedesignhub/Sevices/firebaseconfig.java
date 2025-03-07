package com.homedesignhub.Sevices;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.core.Tag;
import com.homedesignhub.controllers.CartItem;

import javafx.scene.control.Alert;


public class firebaseconfig {

    private static Firestore db;
    private static DocumentReference dReference;

    static {
        try {
            initializeFirebase();
            System.out.println("FireStore Initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("homedesign/src/main/resources/java-fx-firebase-store-b6881-firebase-adminsdk-5t5tr-b5c2792121.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public void addData(String collection, String document, Map<String, Object> data) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public DocumentSnapshot getData(String collection, String document) throws ExecutionException, InterruptedException {
        try { 
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();   
            throw e;
        }
    }

    public boolean authenticateUser(String username, String password) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
    
        if (document.exists()) {
            String storedPassword = document.getString("password");
            return password.equals(storedPassword);
        }
    
        showAlert("Invalid Login", "Invalid Credential....!");
        return false;
    }

    public boolean authenticateDesigner(String username, String password) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("designers").document(username).get().get();
    
        if (document.exists()) {
            String storedPassword = document.getString("password");
            return password.equals(storedPassword);
        }
    
        showAlert("Invalid Login", "Invalid Credential....!");
        return false;
    }

    public void showAlert(String title, String message) {
        Alert alert  = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public List<Map<String, Object>> getProducts(String categoryName) throws ExecutionException, InterruptedException {
        List<Map<String, Object>> products = new ArrayList<>();
        System.out.println("Fetching products for category: " + categoryName);
        ApiFuture<QuerySnapshot> query = db.collection(categoryName).get();
        QuerySnapshot querySnapshot;
        try {
            querySnapshot = query.get();
            System.out.println("Documents found: " + querySnapshot.size());
            for (QueryDocumentSnapshot document : querySnapshot) {
                Map<String, Object> product = document.getData();
                products.add(product);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching products from Firestore: " + e.getMessage());
            throw e;
        }
        return products;
    }

    public List<Map<String, Object>> getAllUsers() throws ExecutionException, InterruptedException {
        List<Map<String, Object>> users = new ArrayList<>();
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            users.add(document.getData());
        }
        return users;
    }

    public List<Map<String, Object>> getUserCartItems(String username) throws ExecutionException, InterruptedException {
        List<Map<String, Object>> cartItems = new ArrayList<>();
        ApiFuture<QuerySnapshot> query = db.collection("users").document(username).collection("cart").get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            cartItems.add(document.getData());
        }
        return cartItems;
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        CollectionReference cartRef = dReference.collection("cart");
        ApiFuture<QuerySnapshot> future = cartRef.get();

        try {
            QuerySnapshot querySnapshot = future.get();
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                Map<String, Object> data = document.getData();
                String imageUrl = (String) data.get("ImageURL");
                String price = (String) data.get("price");
                String type = (String) data.get("type");
                cartItems.add(new CartItem(imageUrl, price, type));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

     public static void addCartItem(String username, String imageUrl, String price, String itemType) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(username).collection("cart").document();
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("ImageURL", imageUrl);
        cartItem.put("price", price);
        cartItem.put("itemType", itemType);

        WriteResult result = docRef.set(cartItem).get();
        System.out.println("Cart item added at: " + result.getUpdateTime());
    }
}
