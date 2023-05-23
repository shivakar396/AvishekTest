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

import generic.FileLib;

public class MongoDBDelete {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {

	      MongoClientURI uri = new MongoClientURI("mongodb://xuritiDB:xuriti%40xuritiDB%40123%40098@10.0.1.5:27017/xuritidb?connectTimeoutMS=10000&authSource=xuritidb&authMechanism=SCRAM-SHA-1");
	      MongoClient mongoClient = new MongoClient(uri);

	      MongoDatabase database = mongoClient.getDatabase("xuritidb");

	      System.out.println("Collection created successfully");
	      for (String name : database.listCollectionNames()) {
	         System.out.println(name);
	      }
	    //UserID=645f21198ce31e0ab07e443f
	      
	    FileLib fl=new FileLib();
	    String usrid=fl.getPropertyData("UserID");
		String buyrcmpnyid=fl.getPropertyData("ANUPAMBUYERID");
		String slrcmpnyid=fl.getPropertyData("ANUPAMSELLERID");
		String nbfcid=fl.getNBFCID("Avishek Singh");
	 
		MongoCollection collection=database.getCollection("ledgers");
		
		System.out.println("Collection name is "+collection.getNamespace());
		collection.deleteMany(new Document("buyer",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("seller",new ObjectId(slrcmpnyid)));
		
		collection=database.getCollection("transactions");
		collection.deleteMany(new Document("account",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("counter_party",new ObjectId(buyrcmpnyid)));
		
		collection=database.getCollection("payments");
		collection.deleteMany(new Document("buyer",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("seller",new ObjectId(slrcmpnyid)));
		
		collection=database.getCollection("invoices");

		collection.deleteMany(new Document("buyer",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("seller",new ObjectId(slrcmpnyid)));
		
		collection=database.getCollection("cash_in_hands");
		collection.deleteMany(new Document("buyer",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("seller",new ObjectId(slrcmpnyid)));
	
		collection=database.getCollection("credit_company_rels");
		collection.deleteMany(new Document("company",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("company",new ObjectId(slrcmpnyid)));
		
		collection=database.getCollection("nbfc_maps");//645f3a1fc558304927ff065c
		collection.deleteMany(new Document("nbfc_id",new ObjectId(nbfcid)));
		
		collection=database.getCollection("entity_relations");
		collection.deleteMany(new Document("company",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("company",new ObjectId(slrcmpnyid)));
		
		collection=database.getCollection("entities");
		collection.deleteMany(new Document("_id",new ObjectId(buyrcmpnyid)));
		collection.deleteMany(new Document("_id",new ObjectId(slrcmpnyid)));
		
		System.out.println("Data Deleted Successfully");
	}
}
