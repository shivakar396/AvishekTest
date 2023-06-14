package xurititestscript;

import java.io.IOException;
import java.sql.SQLException;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import generic.FileCredential;
import generic.FileLib;
import generic.MongoDBCls;

public class DeleteOrissaData {
	
	public static void main(String[] args)throws SQLException, ClassNotFoundException, IOException
	{
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
		collection.deleteMany(new Document("buyer",new ObjectId("6450a862c7dc91348d7ab232")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a2a0c7dc91348d7a78bb")));
		
		collection=database.getCollection("transactions");
		collection.deleteMany(new Document("account",new ObjectId("6450a862c7dc91348d7ab232")));
		collection.deleteMany(new Document("counter_party",new ObjectId("6450a862c7dc91348d7ab232")));
		
		collection=database.getCollection("payments");
		collection.deleteMany(new Document("buyer",new ObjectId("6450a862c7dc91348d7ab232")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a2a0c7dc91348d7a78bb")));
		
		collection=database.getCollection("invoices");

		collection.deleteMany(new Document("buyer",new ObjectId("6450a862c7dc91348d7ab232")));
		collection.deleteMany(new Document("seller",new ObjectId("6450a2a0c7dc91348d7a78bb")));
		
		System.out.println("Data Deleted Successfully");
		
		int limit=1000000;
		collection=database.getCollection("entities");
		collection.updateOne(Filters.eq("gstin", "29AAPFM2372J1ZR"), Updates.set("avail_credit", limit));
		collection.updateOne(Filters.eq("gstin", "29AAPFM2372J1ZR"), Updates.set("creditLimit", limit));
	    mongoClient.close();
	}

}
