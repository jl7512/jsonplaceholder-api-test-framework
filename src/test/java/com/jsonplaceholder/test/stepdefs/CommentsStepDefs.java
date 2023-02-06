package com.jsonplaceholder.test.stepdefs;

import java.util.Map;

import org.junit.Assert;

import com.google.gson.JsonObject;
import com.jsonplaceholder.test.CommentsAPI;
import com.jsonplaceholder.test.TestContext;
import com.jsonplaceholder.test.utils.json.JsonUtils;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class CommentsStepDefs {
	private CommentsAPI commentsAPI;
	private TestContext testContext;

	public CommentsStepDefs(CommentsAPI commentsAPI, TestContext testContext) {
		this.commentsAPI = commentsAPI;
		this.testContext = testContext;
	}

	@When("I create a comment on the post with id {string}")
	public void i_create_a_comment_on_the_post_with_id(String postId, DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String id = data.get("id");
		String name = data.get("name");
		String email = data.get("email");
		String body = data.get("body");

		JsonObject payload = new JsonObject();
		payload.addProperty("postId", postId);
		payload.addProperty("id", id);
		payload.addProperty("name", name);
		payload.addProperty("email", email);
		payload.addProperty("body", body);
		Response createCommentOnPostResponse = commentsAPI.createCommentOnAPost(payload);
		testContext.set(createCommentOnPostResponse);
	}

	@When("I create a comment")
	public void i_create_a_comment(DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String postId = data.get("postId");
		String name = data.get("name");
		String email = data.get("email");
		String body = data.get("body");
		
		JsonObject payload = new JsonObject();
		payload.addProperty("postId", postId);
		payload.addProperty("name", name);
		payload.addProperty("email", email);
		payload.addProperty("body", body);
		Response createCommentResponse = commentsAPI.createComment(payload);
		testContext.set(createCommentResponse);
	}
	
	@When("I fetch the comment with id {string}")
	public void i_fetch_the_comment_with_id(String id) {
		Response getCommentsResponse = commentsAPI.getCommentsById(id);
		testContext.set(getCommentsResponse);
	}
	
	@Then("the comment should contain the following")
	public void the_comment_should_contain_the_following(DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String expectedId = JsonUtils.getField(testContext.get(), "id");
		String expectedPostId = data.get("postId");
		String expectedName = data.get("name");
		String expectedEmail = data.get("email");
		String expectedBody = data.get("body");

		Response getCommentsByIdResponse = commentsAPI.getCommentsById(expectedPostId);
		String actualId = JsonUtils.getField(getCommentsByIdResponse, "id");
		String actualPostId = JsonUtils.getField(getCommentsByIdResponse, "postId");
		String actualName = JsonUtils.getField(getCommentsByIdResponse, "name");
		String actualEmail = JsonUtils.getField(getCommentsByIdResponse, "email");
		String actualBody = JsonUtils.getField(getCommentsByIdResponse, "body");

		Assert.assertTrue("Expected id: " + expectedId + "\n" + "Actual id: " + actualId, expectedId.equals(actualId));
		Assert.assertTrue("Expected post id: " + expectedPostId + "\n" + "Actual post id: " + actualPostId,
				expectedPostId.equals(actualPostId));
		Assert.assertTrue("Expected name: " + expectedName + "\n" + "Actual name: " + actualName,
				expectedName.equals(actualName));
		Assert.assertTrue("Expected email: " + expectedEmail + "\n" + "Actual email: " + actualEmail,
				expectedEmail.equals(actualEmail));
		Assert.assertTrue("Expected body: " + expectedBody + "\n" + "Actual body: " + actualBody,
				expectedBody.equals(actualBody));
	}
}
