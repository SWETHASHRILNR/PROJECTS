package com.ecom.service;

import com.ecom.model.User;

public interface UserService {

	User registerUser(User user);

	User authenticate(String username, String password);

	User createUser(String username, String password, String email);

}
