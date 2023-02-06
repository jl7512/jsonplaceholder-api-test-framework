package com.jsonplaceholder.test;

import io.restassured.response.Response;

public class TestContext {
	private Response response;

	public void set(Response response) {
		this.response = response;
	}

	public Response get() {
		return response;
	}
}
