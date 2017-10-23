package com.cptd.persistence;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

	private DbHelper helper;

	public void insertUser(User user) throws Exception {
		try {
			helper = DbHelper.getHelper();
			Statement st = helper.getConnection().createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO " + Utility.TABLE_NAME + " VALUES(NULL,' ");
			sql.append(user.getUsername() + "','" + user.getPassword() + "', ");
			sql.append(user.isAdmin() + " )");

			st.execute(sql.toString());
		} catch (Exception e) {
			throw e;
		}

	}

	public List<User> getAllUsers() throws Exception {
		try {
			helper = DbHelper.getHelper();
			Statement st = helper.getConnection().createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM User");
			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				boolean isAdmin = rs.getBoolean("admin");

				User temp = new User(username, password, isAdmin);
				users.add(temp);

			}
			return users;
		} catch (Exception e) {
			throw e;
		}
	}

	public void makeAdmin(User user) throws Exception {
		try {
			helper = DbHelper.getHelper();
			Statement st = helper.getConnection().createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE " + Utility.TABLE_NAME + " SET ");
			sql.append(Utility.ADMIN_COLUMN + "=" + Utility.ADMIN);
			sql.append(" WHERE " + Utility.USER_COLUMN + "='"
					+ user.getUsername() + "' AND ");
			sql.append(Utility.PASS_COLUMN + "='" + user.getPassword() + "'");
			st.executeUpdate(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean isAdmin(String username, String password) throws Exception {
		try {
			helper = DbHelper.getHelper();
			boolean response = false;
			Statement st = helper.getConnection().createStatement();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * " + " FROM " + Utility.TABLE_NAME);
			sql.append(" WHERE " + Utility.USER_COLUMN + "='" + username
					+ "'AND ");
			sql.append(Utility.PASS_COLUMN + "='" + password + "'");
			ResultSet rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				response = rs.getBoolean(Utility.ADMIN_COLUMN);
			}
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	public void deleteUser(User user) throws Exception {
		try {
			helper = DbHelper.getHelper();
			Statement st = helper.getConnection().createStatement();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM " + Utility.TABLE_NAME + " WHERE "
					+ Utility.USER_COLUMN);
			sql.append(" LIKE '" + user.getUsername() + "'AND "
					+ Utility.PASS_COLUMN + "='" + user.getPassword() + "'");
			st.execute(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}

	public User getUser(String username, String password) throws Exception {
		try {
			helper = DbHelper.getHelper();
			Statement st = helper.getConnection().createStatement();
			String uName = "", pass = "";
			boolean admin = false;

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM " + Utility.TABLE_NAME + " WHERE "
					+ Utility.USER_COLUMN);
			sql.append(" ='" + username + "'AND " + Utility.PASS_COLUMN + "='"
					+ password + "'");
			ResultSet rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				uName = rs.getString(Utility.USER_COLUMN);
				pass = rs.getString(Utility.PASS_COLUMN);
				admin = rs.getBoolean(Utility.ADMIN_COLUMN);
			}
			return new User(uName, pass, admin);

		} catch (Exception e) {
			throw e;
		}
	}
}
