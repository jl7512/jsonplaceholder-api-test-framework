package com.jsonplaceholder.test.utils.json;

import com.google.gson.JsonObject;

import io.restassured.response.Response;

public class JsonUtils {
	public static JsonObject getJsonObject(Response response) {
		return response.getBody().as(JsonObject.class).getAsJsonObject();
	}

	public static String getField(Response response, String field) {
		return getJsonObject(response).get(field).getAsString();
	}
}
