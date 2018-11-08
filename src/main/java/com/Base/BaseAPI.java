package com.Base;


import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {
	PropertyManager pr = PropertyManager.getInstance();
	String repos = "/repos";
	String slash = "/";
	String branches = "/branches";
	String branchesResource = repos+slash+pr.gitUser+slash+pr.repoName+branches;
	String s1 = branchesResource;
	RequestSpecification request;
	
	private void setAPIURL(){
		
		pr.loadData();
		RestAssured.baseURI = pr.restURL;
		request = RestAssured.
				given().
							accept(ContentType.JSON).
							auth().basic(pr.gitUser, pr.gitPwd);
	}
	@BeforeClass
	public void setUp(){
		Log.info(" ------------ Execution starts -------------");
		setAPIURL();
	}
	@AfterClass
	public void tearDown(){
		Log.info(" ------------ Execution ends -------------");
	}

	
	public String getBranches(String getPath) {
		List <String> path = null;
			Log.info(branchesResource);
		
		try {			
					ValidatableResponse response = request.
			when().
						get(branchesResource).
			then().
						log().body().statusCode(200);
					Log.info("***** "+response.extract().path(getPath));
			path = response.extract().path(getPath);
			
			
		} catch (Exception e) {
			
			//Log.fatal(path.get(0));
			e.printStackTrace();
		}
		//Log.info("-------- "+path.get(0));
		return path.get(0);

	}
	
	public String getBranches(String bResource,String getPath) {
		String path = null;
		if (bResource.equals(null) || bResource=="") {
			Log.info(bResource);
			bResource=branchesResource;
			Log.info(bResource);
		}else{
			bResource=branchesResource+bResource;
			Log.info(bResource);
		}
		try {			
					ValidatableResponse response = request.
			when().
						get(bResource).
			then().
						log().body().statusCode(200);
					//Log.info("***** "+response.extract().path(getPath));
			path = response.extract().path(getPath);
			
			
		} catch (Exception e) {
			
			//Log.fatal(path.get(0));
			e.printStackTrace();
		}
		//Log.info("-------- "+path);
		return path;

	}

}
