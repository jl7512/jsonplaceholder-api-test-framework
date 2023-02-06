package com.jsonplaceholder.test.stepdefs;

import org.junit.Assert;

import com.jsonplaceholder.test.TestContext;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class SharedStepDefs {
	private TestContext testContext;

	public SharedStepDefs(TestContext testContext) {
		this.testContext = testContext;
	}
	
	@Then("the request should fail")
	public void the_request_should_fail() {
		Response response = testContext.get();
		int actualStatusCode = response.getStatusCode();
		int expectedStatusCode = 400;
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
	}
}
