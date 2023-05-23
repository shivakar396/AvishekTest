package xurititestscript;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import generic.FileCredential;
import generic.FileLib;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utility.JsonResCls;

public class TC_0001_Login {
	
	@SuppressWarnings("unchecked")
	
	@Test
	public void TM001testUserLogin() throws IOException
	{
		/*Reading UserLoginEmail & and their Password from credential.property file using 
		  FileCredential Class which is present in src/main/java/generic pkg*/ 

		FileCredential fc=new FileCredential();
		String usrlogeml=fc.readCredential("UserLoginEmail");
		//System.out.println(usrlogeml);
		String usrlogpss=fc.readCredential("UserLoginPassword");
		//System.out.println(usrlogpss);

		/*Reading Xuriti API Url from commondata.property file using 
		  FileLib Class which is present in src/main/java/generic pkg*/

		FileLib fl=new FileLib();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/auth/user-login";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();

		requestParams.put("email",usrlogeml);
		requestParams.put("password",usrlogpss);

		requestParams.put("recaptcha","test_recaptcha");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);
		System.out.println(response.getTime());
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		 //Fetching data of User from Response Body from here to
		String msg="",token="",usrid="",usrnm="",usreml="",usrmbl="",usrfrstnm="",usrlstnm="",usrrole="";int code=0;
		
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response,"message");
		System.out.println("User Message is= "+msg);
		token=js.getStringJsonValue(response,"token");
		usrid=js.getStringJsonValue(response,"user._id");
		usrnm=js.getStringJsonValue(response,"user.name");
		usreml=js.getStringJsonValue(response,"user.email");
		usrmbl=js.getStringJsonValue(response,"user.mobile_number");
		usrfrstnm=js.getStringJsonValue(response,"user.first_name");
		usrlstnm=js.getStringJsonValue(response,"user.last_name");
		usrrole=js.getStringJsonValue(response,"user.user_role");
		code=(int) js.getNumJsonValue(response,"code");	//upto here

		//Saving all the User usable data in commondata.property file by using setPropertyData() method of FileLib Class 

		fl.setPropertyData("UserMessage", msg);
		fl.setPropertyData("UserToken", token);
		fl.setPropertyData("UserID", usrid);
		fl.setPropertyData("UserName", usrnm);
		fl.setPropertyData("UserEmail", usreml);
		fl.setPropertyData("UserMobileNumber", usrmbl);
		fl.setPropertyData("UserFirstName", usrfrstnm);
		fl.setPropertyData("UserLastName", usrlstnm);
		fl.setPropertyData("UserRole", usrrole);

		Reporter.log("UserLogin",true);
		//Printing User Response Body
		System.out.println(response.prettyPrint());
		Assert.assertEquals(code, 100);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TM002testAdminLogin() throws IOException
	{
		/*Reading AdminLoginEmail & and their Password from credential.property file using 
		  FileLib Class which is present in src/main/java/generic pkg*/ 

		FileCredential fc=new FileCredential();
		String admnlogeml=fc.readCredential("AdminLoginEmail");
		String admnlogpss=fc.readCredential("AdminLoginPassword");

		/*Reading Xuriti API Url from commondata.property file using 
		  FileLib Class which is present in src/main/java/generic pkg*/ 

		FileLib fl=new FileLib();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/auth/user-login";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();

		requestParams.put("email",admnlogeml);
		requestParams.put("password",admnlogpss);
		requestParams.put("recaptcha","test_recaptcha");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);
		
		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		

		//Fetching data of Admin from Response Body from here to
		String msg="",token="",admnid="",admnm="",admneml="",admnmbl="",admnfrstnm="",admnlstnm="",admnrole="";int code=0;
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response,"message");
		System.out.println("Admin Message is= "+msg);
		token=js.getStringJsonValue(response,"token");
		admnid=js.getStringJsonValue(response,"user._id");
		admnm=js.getStringJsonValue(response,"user.name");
		admneml=js.getStringJsonValue(response,"user.email");
		admnmbl=js.getStringJsonValue(response,"user.mobile_number");
		admnfrstnm=js.getStringJsonValue(response,"user.first_name");
		admnlstnm=js.getStringJsonValue(response,"user.last_name");
		admnrole=js.getStringJsonValue(response,"user.user_role");
		code=(int) js.getNumJsonValue(response,"code");	//upto here
				
		//Saving all the Admin usable data in commondata.property file by using setPropertyData() method of FileLib Class

		fl.setPropertyData("AdminMessage", msg);
		fl.setPropertyData( "AdminToken", token);
		fl.setPropertyData("AdminID", admnid);
		fl.setPropertyData("AdminName", admnm);
		fl.setPropertyData("AdminEmail", admneml);
		fl.setPropertyData("AdminMobileNumber", admnmbl);
		fl.setPropertyData("AdminFirstName", admnfrstnm);
		fl.setPropertyData("AdminLastName", admnlstnm);
		fl.setPropertyData("AdminRole", admnrole);

		Reporter.log("AdminLogin",true);
			//Printing Admin Response Body
		System.out.println(response.prettyPrint());
		Assert.assertEquals(code, 100);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void TM003testManagerLogin() throws IOException
	{
		/*Reading ManagerLoginEmail & and their Password from credential.property file using 
		  FileCredential Class which is present in src/main/java/generic pkg*/


		FileCredential fc=new FileCredential();
		String mgrlogeml=fc.readCredential("ManagerLoginEmail");
		String mgrnlogpss=fc.readCredential("ManagerLoginPassword");

		/*Reading Xuriti API Url from commondata.property file using 
		  FileLib Class which is present in src/main/java/generic pkg*/

		FileLib fl=new FileLib();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/auth/user-login";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();

		requestParams.put("email",mgrlogeml);
		requestParams.put("password",mgrnlogpss);
		requestParams.put("recaptcha","test_recaptcha");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);

		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		
		//Fetching data of Manager from Response Body from here to
		String msg="",token="",mgrid="",mgrnm="",mgreml="",mgrmbl="",mgrfrstnm="",mgrlstnm="",mgrrole=""; int code=0;
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response,"message");
		System.out.println("Manager Message is= "+msg+"\n");
		token=js.getStringJsonValue(response,"token");
		mgrid=js.getStringJsonValue(response,"user._id");
		mgrnm=js.getStringJsonValue(response,"user.name");
		mgreml=js.getStringJsonValue(response,"user.email");
		mgrmbl=js.getStringJsonValue(response,"user.mobile_number");
		mgrfrstnm=js.getStringJsonValue(response,"user.first_name");
		mgrlstnm=js.getStringJsonValue(response,"user.last_name");
		mgrrole=js.getStringJsonValue(response,"user.user_role"); 
		code=(int) js.getNumJsonValue(response,"code");		//upto here
	
		//Saving all the Admin usable data in commondata.property file by using setPropertyData() method of FileLib Class

		fl.setPropertyData("ManagerMessage", msg);
		fl.setPropertyData( "ManagerToken", token);
		fl.setPropertyData("ManagerID", mgrid);
		fl.setPropertyData("ManagerName", mgrnm);
		fl.setPropertyData("ManagerEmail", mgreml);
		fl.setPropertyData("ManagerMobileNumber", mgrmbl);
		fl.setPropertyData("ManagerFirstName", mgrfrstnm);
		fl.setPropertyData("ManagerLastName", mgrlstnm);
		fl.setPropertyData("ManagerRole", mgrrole);

		Reporter.log("ManagerLogin",true);

			//Printing Manager Response Body
		System.out.println(response.prettyPrint());
		Assert.assertEquals(code, 100);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void TM004testNBFCLogin() throws IOException
	{
		/*Reading ManagerLoginEmail & and their Password from credential.property file using 
		  FileCredential Class which is present in src/main/java/generic pkg*/


		FileCredential fc=new FileCredential();
		String nbfc_logeml=fc.readCredential("NBFCLoginEmail");
		String nbfc_logpss=fc.readCredential("NBFCLoginPassword");

		/*Reading Xuriti API Url from commondata.property file using 
		  FileLib Class which is present in src/main/java/generic pkg*/

		FileLib fl=new FileLib();
		String xuritibaseurl=fc.readCredential("XuritiBaseUrl");

		RestAssured.baseURI=xuritibaseurl+"/auth/user-login";
		RequestSpecification  httpRequest=RestAssured.given();
		JSONObject 	requestParams=new JSONObject();

		requestParams.put("email",nbfc_logeml);
		requestParams.put("password",nbfc_logpss);
		requestParams.put("recaptcha","test_recaptcha");
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response=httpRequest.request(Method.POST);

		int stscd=response.getStatusCode();
		String stsln=response.getStatusLine();
		
		Assert.assertEquals(stsln, "HTTP/1.1 200 OK");
		Assert.assertEquals(stscd, 200);
		
		
		//Fetching data of Manager from Response Body from here to
		String msg="",token="",nbfc_id="",nbfc_nm="",nbfc_eml="",nbfc_mbl="",nbfc_frstnm="",nbfc_lstnm="",nbfc_role=""; int code=0;
		JsonResCls js=new JsonResCls();
		
		msg=js.getStringJsonValue(response,"message");
		System.out.println("NBFC Message is= "+msg+"\n");
		token=js.getStringJsonValue(response,"token");
		nbfc_id=js.getStringJsonValue(response,"user._id");
		nbfc_nm=js.getStringJsonValue(response,"user.name");
		nbfc_eml=js.getStringJsonValue(response,"user.email");
		nbfc_mbl=js.getStringJsonValue(response,"user.mobile_number");
		nbfc_frstnm=js.getStringJsonValue(response,"user.first_name");
		nbfc_lstnm=js.getStringJsonValue(response,"user.last_name");
		nbfc_role=js.getStringJsonValue(response,"user.user_role"); 
		code=(int) js.getNumJsonValue(response,"code");		//upto here
	
		//Saving all the Admin usable data in commondata.property file by using setPropertyData() method of FileLib Class

		fl.setPropertyData("ManagerMessage", msg);
		fl.setPropertyData("NBFCToken", token);
		fl.setPropertyData("NBFCUserID", nbfc_id);
		fl.setPropertyData("NBFCEmail", nbfc_eml);
		fl.setPropertyData("NBFCMobileNumber", nbfc_mbl);
		fl.setPropertyData("NBFCFirstName", nbfc_frstnm);
		fl.setPropertyData("NBFCLastName", nbfc_lstnm);
		fl.setPropertyData("NBFCRole", nbfc_role);

		Reporter.log("NBFCLogin",true);

			//Printing Manager Response Body
		System.out.println(response.prettyPrint());
		Assert.assertEquals(code, 100);
		
		Assert.assertEquals(nbfc_eml, "avishek.nbfc@gmail.com");
		Assert.assertEquals(nbfc_nm, "Avishek Singh");
	}
}