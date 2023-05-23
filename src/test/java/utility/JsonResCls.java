package utility;

import org.testng.Reporter;

import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

public class JsonResCls {
		/**
		 * 
		 * @param response the reference variable of the Response Interface
		 * @param path Json Path
		 * @return
		 * 
		 */
		public  String getStringJsonValue(Response response, String path) {
			String data="";
			try {
				data=response.body().jsonPath().get(path);
			}
			catch(JsonPathException e) {
				Reporter.log(response.prettyPrint(),true);
			}
			return data;
		}
		/**
		 * 
		 * @param response the reference variable of the Response Interface
		 * @param path Json Path
		 * @return
		 * 
		 */
		public float getNumJsonValue(Response response, String path) {
			float data=0.0F;
			try {
				data=response.body().jsonPath().get(path);
			}
			catch(JsonPathException e) {
				Reporter.log(response.prettyPrint(),true);
			}
			catch (ClassCastException e) {
				int x=response.body().jsonPath().get(path);
				data=x;
			}
			catch (NullPointerException e) {
				
			}
			return data;
		}
}
