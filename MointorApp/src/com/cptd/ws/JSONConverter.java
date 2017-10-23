package com.cptd.ws;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.cptd.persistence.User;

public class JSONConverter {
	public static final String USER = "username";
	public static final String PASS = "password";
	public static final String ADMIN = "admin";

	public static String UsersToJSON(List<User> users) throws Exception {
		JSONArray array = new JSONArray();
		for (User temp : users) {

			JSONObject obj = new JSONObject();
			obj.put(JSONConverter.USER, temp.getUsername());
			obj.put(JSONConverter.PASS, temp.getPassword());
			obj.put(JSONConverter.ADMIN, temp.isAdmin());
			array.put(obj);

		}
		return array.toString();
	}

	public static User JSONtoUser(String result) throws Exception {
		JSONObject obj = new JSONObject(result);
		String username = obj.getString(JSONConverter.USER);
		String password = obj.getString(JSONConverter.PASS);
		boolean isAdmin = obj.getBoolean(JSONConverter.ADMIN);
		return new User(username, password, isAdmin);
	}

	public static String userToJSON(User user) throws Exception {

		JSONObject obj = new JSONObject();
		obj.put(JSONConverter.USER, user.getUsername());
		obj.put(JSONConverter.PASS, user.getPassword());
		obj.put(JSONConverter.ADMIN, user.isAdmin());
		return obj.toString();
	}
}
