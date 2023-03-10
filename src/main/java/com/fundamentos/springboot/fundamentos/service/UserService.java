package com.fundamentos.springboot.fundamentos.service;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	private final Log LOG = LogFactory.getLog(UserService.class);
	private UserRepository userRepository;
	public UserService(UserRepository userRepository){
		this.userRepository = userRepository;
	}


	@Transactional
	public void saveTransactional(List<User> users){
		users.stream()
		.peek(user -> LOG.info("Usuario insertado: " + user))
				.forEach(userRepository::save);
				//.forEach(user -> userRepository.save(user));
	}

	public  List<User> getAllUsers(){
		return userRepository.findAll();
	}

	public User save(User newUser) {
		return userRepository.save(newUser);
	}

	public void delete(Long id) {
		userRepository.delete(new User(id));
	}

	public User update(User newUser, Long id) {
		return
				userRepository.findById(id)
				.map(
						user -> {
							user.setEmail(newUser.getEmail());
							user.setBirthdate(newUser.getBirthdate());
							user.setName(newUser.getName());
							return userRepository.save(user);
						}
				).get();
	}
}
