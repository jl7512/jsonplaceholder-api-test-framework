package com.jsonplaceholder.test.stepdefs;

import java.util.Arrays;
import java.util.List;
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
	
	@When("I fetch all posts")
	public void i_fetch_all_posts() {
		Response getAllPostsResponse = postsAPI.getAllPosts();
		testContext.set(getAllPostsResponse);
	}
	
	@When("I fetch all posts by a user with id {string}")
	public void i_fetch_all_posts_by_a_user_with_id(String userId) {
		Response getAllPostsByUserIdResponse = postsAPI.getAllPostsByUserId(userId);
		testContext.set(getAllPostsByUserIdResponse);
	}
	
	@When("I fetch all the posts with title {string}")
	public void i_fetch_all_the_posts_with_title(String postTitle) {
		Response getPostByTitleResponse = postsAPI.getPostByTitle(postTitle);
		testContext.set(getPostByTitleResponse);
	}
	
	@When("I update the post with id {string} with the following")
	public void i_update_the_post_with_id_with_the_following(String id, DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String userId = data.get("userId");
		String title = data.get("title");
		String body = data.get("body");

		JsonObject payload = new JsonObject();
		payload.addProperty("userId", userId);
		payload.addProperty("title", title);
		payload.addProperty("body", body);
		Response patchPostResponse = postsAPI.patchPost(id, payload);
		testContext.set(patchPostResponse);
	}
	
	@When("I edit the post with id {string} with new details")
	public void i_edit_the_post_with_id_with_new_details(String id, DataTable dataTable) {
		Map<String, String> data = dataTable.asMaps().get(0);
		String userId = data.get("userId");
		String title = data.get("title");
		String body = data.get("body");

		JsonObject payload = new JsonObject();
		payload.addProperty("id", id);
		payload.addProperty("userId", userId);
		payload.addProperty("title", title);
		payload.addProperty("body", body);
		Response putPostResponse = postsAPI.putPost(id, payload);
		testContext.set(putPostResponse);
	}
	
	@When("I delete the post with id {string}")
	public void i_delete_the_post_with_id(String postId) {
		Response deletePostWithIdResponse = postsAPI.deletePost(postId);
		testContext.set(deletePostWithIdResponse);
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
	
	@Then("all the posts should contain the following fields")
	public void all_the_posts_should_contain_the_following_fields(DataTable dataTable) {
		List<String> expectedFields = dataTable.asList();

		Response response = testContext.get();
		List<JsonObject> listOfPostJsonObjects = JsonUtils.getJsonObjectList(response);

		listOfPostJsonObjects.forEach(postJsonObject -> {
			expectedFields.forEach(expectedField -> {
				boolean hasField = postJsonObject.has(expectedField);
				Assert.assertTrue("Expected post to have field " + expectedField + "'", hasField);
			});
		});
	}

	@Then("it should contain the following fields")
	public void it_should_contain_the_following_fields(DataTable dataTable) {
		List<String> expectedFields = dataTable.asList();

		Response response = testContext.get();
		JsonObject postJsonObject = JsonUtils.getJsonObject(response);

		expectedFields.forEach(expectedField -> {
			boolean hasField = postJsonObject.has(expectedField);
			Assert.assertTrue("Expected to have field " + expectedField + "'", hasField);
		});
	}

	@Then("the id should be {string}")
	public void the_id_should_be(String expectedId) {
		Response response = testContext.get();
		String actualId = JsonUtils.getField(response, "id");
		Assert.assertTrue("Expected: " + expectedId + " Actual: " + actualId, expectedId.equals(actualId));
	}
	
	@Then("all the posts should have user id {string}")
	public void all_the_posts_should_have_user_id(String expectedUserId) {
		Response response = testContext.get();
		List<JsonObject> listOfPostJsonObjects = JsonUtils.getJsonObjectList(response);

		listOfPostJsonObjects.forEach(post -> {
			String actualUserId = post.get("userId").getAsString();
			boolean sameUserId = expectedUserId.equals(actualUserId);
			Assert.assertTrue("Expected the userId of post to be " + expectedUserId, sameUserId);
		});
	}

	@Then("all the posts should have title {string}")
	public void all_the_posts_should_have_title(String expectedPostTitle) {
		Response response = testContext.get();
		List<JsonObject> listOfPostJsonObjects = JsonUtils.getJsonObjectList(response);

		listOfPostJsonObjects.forEach(post -> {
			String actualPostTitle = post.get("title").getAsString();
			boolean sameTitle = expectedPostTitle.equals(actualPostTitle);
			Assert.assertTrue("Expected the title of post to be " + expectedPostTitle, sameTitle);
		});
	}
	
	@Then("the user id should be {string}")
	public void the_user_id_should_be(String expectedUserId) {
		Response response = testContext.get();
		String actualUserId = JsonUtils.getField(response, "userId");
		Assert.assertTrue(response.asPrettyString() + "\n" + "Expected: " + expectedUserId + " Actual: " + actualUserId,
				expectedUserId.equals(actualUserId));
	}
	
	@Then("the post with id {string} should be deleted")
	public void the_post_with_id_should_be_deleted(String postId) {
		Response getPostResponse = testContext.get();
		int actualStatusCode = getPostResponse.getStatusCode();
		int expectedStatusCode = 404;
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
	}
}
