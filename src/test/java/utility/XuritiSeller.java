package utility;

import java.io.File;
import java.io.IOException;

import generic.FileCredential;
import generic.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class XuritiSeller {
	
	public  Response setInvoiceUpload(String file_name) throws IOException {
		
		//https://uat.xuriti.app/api/invoice/upload-invoice-excel
		FileLib fl=new FileLib();
		FileCredential fc=new FileCredential();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");
		
		String usrid=fl.getPropertyData("UserID");
		
		File file=new File("./src/test/resources/data/"+file_name);
		String abpath=file.getAbsolutePath();
		String token=fl.getPropertyData("UserToken");
		
		Response response=RestAssured.given().header("Authorization","Bearer "+token)
		.multiPart("file",new File(abpath),"multipart/form-data")
		.when().post(xuritibaseurl+"/invoice/upload-invoice-excel")
		.thenReturn();
		
		return response;
	}
	public Response getSellererInvoices(String seller_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/invoice/search-invoice/64536eb3c65a2264547ccaca/seller
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/invoice/search-invoice/"+seller_company_id+"/seller";
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	
	public Response getSellerPaymentHistory(String seller_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/payment/transctionshistory?seller=64536eb3c65a2264547ccaca
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/payment/transctionshistory";
		String endpnt="?seller="+seller_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization","Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
	
	public Response getSellerTransactionLedger(String seller_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/ledger/statement/buyer/64536eb2c65a2264547ccab3
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/ledger/statement/buyer/"+seller_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET);
		
		return response;
	}
	
	public Response getSellerTransactionalStatement(String seller_company_id) throws IOException
	{
		//https://uat.xuriti.app/api/ledger/companies/transaction_ledger?seller=64536eb3c65a2264547ccaca
		
		FileLib fl=new FileLib();
		String xuritibaseurl=fl.getPropertyData("XuritiBaseUrl");
		String usrtoken=fl.getPropertyData("UserToken");
		
		RestAssured.baseURI=xuritibaseurl+"/ledger/companies/transaction_ledger";
		String endpnt="?seller="+seller_company_id;
		RequestSpecification httpRequest=RestAssured.given();
		httpRequest.header("Authorization", "Bearer "+usrtoken);
		Response response=httpRequest.request(Method.GET,endpnt);
		
		return response;
	}
}
