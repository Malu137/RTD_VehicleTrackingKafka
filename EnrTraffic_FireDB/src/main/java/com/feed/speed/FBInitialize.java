package com.feed.speed;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.jsonmessage.BusPos;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Component
public class FBInitialize {
	
	FirebaseApp defaultApp;
	DatabaseReference fbActiveBusRef;
	CollectionReference fsColRef;
	CollectionReference fsColTripRef;
	DatabaseReference fbAlertRef;
	
	boolean fbReady = false;
	
	public FBInitialize() {
		this.initialize();
	}
	
	//@PostConstruct
	public void initialize() {  
		 try {  
			 FileInputStream serviceAccount =  
					 new FileInputStream("FirebasePrivateKey.json");  
			 FirebaseOptions options = new FirebaseOptions.Builder()  
					 .setCredentials(GoogleCredentials.fromStream(serviceAccount))  
					 .setDatabaseUrl("https://realtimemaps-f67ad.firebaseio.com/")  
					 .build();  
			 this.defaultApp = FirebaseApp.initializeApp(options);  
			 
			 initFirebaseDB();
			 initFirestoreDB();
			 
			 fbReady = true;
			 System.out.println("FBinitialized");
			 
			 
			 CompTrip trip = new CompTrip("Bus1", "trips", "route", 1, 2, 3, 4, 5, 6, 7);		 
			 postCompTrip("Bus1", "trip1", trip);
			 postCompTrip("Bus1","trip2",trip);
			 //post("bus18", new ActiveBus("ABC",1233,34,-104,"123","123",12,12, 0, 0, 0, 0, 0, 0, 0));
			 //this.get("bus18");
		 
		 } catch (Exception e) {  
			 e.printStackTrace();
			 System.out.println("Here3");
		 }
	}
	
	 private void initFirebaseDB() throws Exception{
		 try {
			 FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance(this.defaultApp);
			 DatabaseReference fbRef = fbDatabase.getReference();
			 this.fbActiveBusRef = fbRef.child("ActiveBuses");
			 this.fbAlertRef = fbRef.child("Alerts");
			 
		 }
		 catch(Exception e) {
			 System.out.println("Error initializing firebase");
			 throw new Exception(e);
		 }
	 }
	 
	 private void initFirestoreDB() throws Exception{
		 try {
			 Firestore db = FirestoreClient.getFirestore();
			 this.fsColRef = db.collection("ActiveBuses");
			 this.fsColTripRef = db.collection("CompletedTrip");
		 }
		 catch(Exception e) {
			 System.out.println("Error initializing firestore");
			 throw new Exception(e);
		 }
	 }
	 
	 public void post(String busID, ActiveBus busPos) throws Exception{
		 
		 System.out.println("Inside post...");
		 if(!fbReady) {
			 System.out.println("Firebase is not initialized ...");
			 return;
		 }
		 // write data to firebase
		 DatabaseReference childref = this.fbActiveBusRef.child(busID);
		 ApiFuture<Void> xyz = childref.setValueAsync(busPos);
		 
		 //write data to firestore
		 DocumentReference fsDocRef = this.fsColRef.document(busID);
		//asynchronously write data
		ApiFuture<WriteResult> result = fsDocRef.set(busPos);
		System.out.println("Update time : " + result.get().getUpdateTime());
		/*
		while(!xyz.isDone() || !result.isDone()) {
			Thread.sleep(1000);
		}*/
	 }
	 
	 public ActiveBus get(String busID) throws Exception{
		 
		 if(!fbReady) {
			 System.out.println("Firebase is not initialized ...");
			 return null;
		 }
		 
		 //read data from firestore
		 DocumentReference fsDocRef = this.fsColRef.document(busID);
		 ApiFuture<DocumentSnapshot> document = fsDocRef.get();
		 /*
		 while (! document.isDone()) {
			 Thread.sleep(1000);
		 }*/
		 DocumentSnapshot ds = document.get();
		 if(!ds.exists()) {
			 System.out.println("Doc doesnot exist.");
			 return null;
		 }
		 else {
			 System.out.println(ds.toString());
			 ActiveBus bp = ds.toObject(ActiveBus.class);
			 return bp;
		 }
		 
		 //Thread.sleep(1000);
		  //System.out.println("User: " + document.getId());
		 //System.out.println(document.get());
		 //System.out.println(busID);
		 //System.out.println(fsDocRef.toString());
		  
		  
	 }
	 
	 public void deleteActive(String busID) throws Exception{
		 if(!fbReady) {
			 System.out.println("Firebase is not initialized ...");
			 return;
		 }
		 System.out.println("Deleting Trip");
		 //Delete from firestore
		 DocumentReference fsDocRef = this.fsColRef.document(busID);
		 ApiFuture<WriteResult> writeResult  = fsDocRef.delete();
		 
		 //Delete from realtime db
		 DatabaseReference childref = this.fbActiveBusRef.child(busID);
		 ApiFuture<Void> waitres = childref.removeValueAsync();
		 /*
		 while(!waitres.isDone() || !writeResult.isDone()) {
			 Thread.sleep(1000);
		 }*/	  
		 
		 
	 }
	 
	 public void postCompTrip(String busID, String tripID, CompTrip trip) throws Exception{
		 System.out.println("Inside postComptrip...");
		 
		 if(!fbReady) {
			 System.out.println("Firebase is not initialized ...");
			 return;
		 }
		 //Set doc ref to busid/tripid/
		 DocumentReference fsDocRef = this.fsColTripRef.document(busID);
		 Map <String,CompTrip> val = new HashMap<String,CompTrip>();
		 val.put(tripID, trip);
		 ApiFuture<WriteResult> result = fsDocRef.set(val, SetOptions.merge());
		 System.out.println("Update time : " + result.get().getUpdateTime());
		 /*
		 while(!result.isDone()) {
				Thread.sleep(1000);
			}
		 */
	 }
	 
	 public void postAlert(String alert, String alertType) {
		 System.out.println("Posting alert");
		 Map<String,String> alertmap = new HashMap<String,String>();
		 alertmap.put(alertType,alert);
		 ApiFuture<Void> res = this.fbAlertRef.setValueAsync(alertmap);		 
	 }
	 
}  