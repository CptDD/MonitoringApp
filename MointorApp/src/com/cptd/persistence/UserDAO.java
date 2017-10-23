package com.cptd.persistence;

import java.util.List;

public interface UserDAO {
	public void insertUser(User user) throws Exception;

	public User getUser(String username, String password) throws Exception;

	public void deleteUser(User user) throws Exception;

	public boolean isAdmin(String username, String password) throws Exception;

	public void makeAdmin(User user) throws Exception;

	public List<User> getAllUsers() throws Exception;

}
