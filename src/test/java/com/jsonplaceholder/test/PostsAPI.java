package com.jsonplaceholder.test;

import com.google.gson.JsonObject;

import io.restassured.response.Response;

public class PostsAPI extends BaseAPI {
	private final String BASE_ROUTE = "posts/";

	public PostsAPI() {
		super();
	}

	public Response createPost(JsonObject payload) {
		return post(BASE_ROUTE, payload);
	}

	public Response getPostById(String postId) {
		return get(BASE_ROUTE + postId);
	}
}
