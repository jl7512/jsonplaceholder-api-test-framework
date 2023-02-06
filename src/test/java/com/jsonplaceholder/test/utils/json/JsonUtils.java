package com.jsonplaceholder.test.utils.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import io.restassured.response.Response;

public class JsonUtils {
	public static JsonObject getJsonObject(Response response) {
		return response.getBody().as(JsonObject.class).getAsJsonObject();
	}

	public static List<JsonObject> getJsonObjectList(Response response) {
		Type jsonListType = new TypeToken<List<JsonObject>>() {}.getType();
		return new Gson().fromJson(response.asPrettyString(), jsonListType);
	}

	public static String getField(Response response, String field) {
		return getJsonObject(response).get(field).getAsString();
	}
}
