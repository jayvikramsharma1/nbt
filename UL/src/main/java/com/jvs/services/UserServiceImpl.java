package com.jvs.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jvs.dao.UserRepository;
import com.jvs.models.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	public Collection<User> findAllUsers() {
		List<User> users = new ArrayList<>();
		for(User u : userRepository.findAll()){
			users.add(u);
		}
		return users;
	}

	@Override
	public User findUserByUserName(String username) {
		return userRepository.findUserByUserName(username);
	}

	@Override
	public User findUserByUserNameAndPassword(String username, String password) {
		return userRepository.findUserByUserNameAndPassword(username, password);
	}

	@Override
	public Long getUserCount() {
		return userRepository.getUserCount();
	}

	@Override
	public Long saveUser(User user) {
		User usr = userRepository.save(user);
		return usr.getId();
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user.getId(), user.getFullname(), user.getPassword());
	}

	@Override
	public void deleteUser(long id) {
		userRepository.delete(id);
	}
	
	
	
	
	
	
}
