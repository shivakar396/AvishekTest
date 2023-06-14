package xurititestscript;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DeleteAssamData {

	public static void main(String[] args) {
		 MongoClientURI uri = new MongoClientURI("mongodb://xuritiDB:xuriti%40xuritiDB%40123%40098@10.0.1.5:27017/xuritidb?connectTimeoutMS=10000&authSource=xuritidb&authMechanism=SCRAM-SHA-1");
	      MongoClient mongoClient = new MongoClient(uri);

	      MongoDatabase database = mongoClient.getDatabase("xuritidb");

	      System.out.println("Collection created successfully");
	      for (String name : database.listCollectionNames()) {
	         System.out.println(name);
	      }
	    //UserID=645f21198ce31e0ab07e443f
	      
	 
		MongoCollection collection=database.getCollection("ledgers");
		
		System.out.println("Collection name is "+collection.getNamespace());
		collection.deleteMany(new Document("buyer",new ObjectId("6450a89ec7dc91348d7ab37f")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a415c7dc91348d7a8d23")));
		
		collection=database.getCollection("transactions");
		collection.deleteMany(new Document("account",new ObjectId("6450a89ec7dc91348d7ab37f")));
		collection.deleteMany(new Document("counter_party",new ObjectId("6450a89ec7dc91348d7ab37f")));
		
		collection=database.getCollection("payments");
		collection.deleteMany(new Document("buyer",new ObjectId("6450a89ec7dc91348d7ab37f")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a415c7dc91348d7a8d23")));
		
		collection=database.getCollection("invoices");

		collection.deleteMany(new Document("buyer",new ObjectId("6450a89ec7dc91348d7ab37f")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a415c7dc91348d7a8d23")));
		
		System.out.println("Data Deleted Successfully");
		
		int limit=500000;
		collection=database.getCollection("entities");
		collection.updateOne(Filters.eq("gstin", "07JAMPS5692M1ZH"), Updates.set("avail_credit", limit));
	    mongoClient.close();

	}
}
