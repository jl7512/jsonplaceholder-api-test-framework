package com.jsonplaceholder.test.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber",
		"json:target/cucumber.json" }, features = "src/test/resources/features", glue = {
				"com.jsonplaceholder.test.stepdefs" }, tags = "@complete")
public class TestRunner {

}
