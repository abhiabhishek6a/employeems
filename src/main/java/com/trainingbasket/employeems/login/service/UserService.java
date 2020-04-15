package com.trainingbasket.employeems.login.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainingbasket.employeems.login.model.Role;
import com.trainingbasket.employeems.login.model.User;
import com.trainingbasket.employeems.login.repository.RoleRepository;
import com.trainingbasket.employeems.login.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User findUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		user.setActive(true);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

}
