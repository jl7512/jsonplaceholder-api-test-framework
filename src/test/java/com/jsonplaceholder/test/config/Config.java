package com.jsonplaceholder.test.config;

import java.util.ResourceBundle;

public final class Config {
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("production");
	public static final String BASE_URL = resourceBundle.getString("base_url");
}
