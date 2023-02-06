package com.jsonplaceholder.test;

import com.google.gson.JsonObject;

import io.restassured.response.Response;

public class CommentsAPI extends BaseAPI {
	private final String BASE_ROUTE = "comments/";

	public CommentsAPI() {
		super();
	}

	public Response createCommentOnAPost(JsonObject payload) {
		return post(BASE_ROUTE, payload);
	}
	
	public Response getCommentsById(String commentId) {
		return get(BASE_ROUTE + commentId);
	}
}
