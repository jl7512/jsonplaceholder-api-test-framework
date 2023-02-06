package com.jsonplaceholder.test.stepdefs;

import java.util.Arrays;
import java.util.Map;

import org.junit.Assert;

import com.google.gson.JsonObject;
import com.jsonplaceholder.test.PostsAPI;
import com.jsonplaceholder.test.TestContext;
import com.jsonplaceholder.test.utils.json.JsonUtils;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class PostsStepDefs {
	private PostsAPI postsAPI;
	private TestContext testContext;

	public PostsStepDefs(PostsAPI postsAPI, TestContext testContext) {
		this.postsAPI = postsAPI;
		this.testContext = testContext;
	}

	@When("I create a post")
	public void i_create_a_post(DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String id = data.get("id");
		String userId = data.get("userId");
		String title = data.get("title");
		String body = data.get("body");

		JsonObject payload = new JsonObject();
		payload.addProperty("id", id);
		payload.addProperty("userId", userId);
		payload.addProperty("title", title);
		payload.addProperty("body", body);
		Response createPostResponse = postsAPI.createPost(payload);
		testContext.set(createPostResponse);
	}

	@When("I fetch the post with id {string}")
	public void i_fetch_the_post_with_id(String postId) {
		Response getPostByIdResponse = postsAPI.getPostById(postId);
		testContext.set(getPostByIdResponse);
	}

	@Then("the post title should be {string}")
	public void the_post_title_should_be(String expectedTitle) {
		Response response = testContext.get();
		String actualTitle = JsonUtils.getField(response, "title");
		Assert.assertTrue("Expected post title: " + expectedTitle + "\n" + "Actual post title: " + actualTitle,
				expectedTitle.equals(actualTitle));
	}

	@Then("the post body should be {string}")
	public void the_post_body_should_be(String expectedPostBody) {
		Response response = testContext.get();
		char[] actualPostBodyCharacters = JsonUtils.getField(response, "body").trim().replaceAll("[\\t\\n\\r]+", "")
				.toCharArray();
		char[] expectedPostBodyCharacters = expectedPostBody.trim().replaceAll("[\\t\\n\\r]+", "").toCharArray();
		boolean samePostBody = Arrays.equals(actualPostBodyCharacters, expectedPostBodyCharacters);
		Assert.assertTrue(samePostBody);
	}

	@Then("the request should fail")
	public void the_request_should_fail() {
		Response response = testContext.get();
		int actualStatusCode = response.getStatusCode();
		int expectedStatusCode = 400;
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
	}
}
