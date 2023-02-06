package com.jsonplaceholder.test;

import com.google.gson.JsonObject;

import io.restassured.response.Response;

public class PostsAPI extends BaseAPI {
	private final String BASE_ROUTE = "posts/";
	private final String GET_POSTS_BY_USER_ID_ROUTE = BASE_ROUTE + "?userId=";
	private final String GET_POSTS_BY_POST_TITLE_ROUTE = BASE_ROUTE + "?title=";

	public PostsAPI() {
		super();
	}

	public Response createPost(JsonObject payload) {
		return post(BASE_ROUTE, payload);
	}

	public Response getPostById(String postId) {
		return get(BASE_ROUTE + postId);
	}
	
	public Response getAllPosts() {
		return get(BASE_ROUTE);
	}

	public Response getAllPostsByUserId(String userId) {
		return get(GET_POSTS_BY_USER_ID_ROUTE + userId);
	}
	
	public Response getPostByTitle(String postTitle) {
		return get(GET_POSTS_BY_POST_TITLE_ROUTE + postTitle);
	}

	public Response patchPost(String postId, JsonObject payload) {
		return patch(BASE_ROUTE + postId, payload);
	}
}
