package com.jvs.services;

import java.util.Collection;

import com.jvs.models.User;

public interface UserService {
	public Collection<User> findAllUsers();
	public User findUserByUserName(String username);
	public User findUserByUserNameAndPassword(String username, String password);
	public Long getUserCount();
	public Long saveUser(User user);
	public void updateUser(User user);
	public void deleteUser(long id);
}
