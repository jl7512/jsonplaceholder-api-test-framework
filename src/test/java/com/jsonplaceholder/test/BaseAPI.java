package com.jsonplaceholder.test;

import com.google.gson.JsonObject;
import com.jsonplaceholder.test.config.Config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {
	private RequestSpecification request;

	public BaseAPI() {
		RestAssured.baseURI = Config.BASE_URL;
		request = RestAssured.given().contentType(ContentType.JSON);
	}

	protected Response get(String route) {
		return request.get(route).then().extract().response();
	}

	protected Response post(String route, JsonObject jsonBody) {
		return request.body(jsonBody).post(route).then().extract().response();
	}

	protected Response patch(String route, JsonObject jsonBody) {
		return request.body(jsonBody).patch(route).then().extract().response();
	}
}
