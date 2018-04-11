package com.prs.business.web;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prs.business.user.User;
import com.prs.business.user.UserRepository;
import com.prs.util.PRSMaintenanceReturn;


@Controller   
@RequestMapping(path="/Users")
public class UserController {
	@Autowired 
	private UserRepository userRepository;

	@GetMapping(path="/List")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	@GetMapping(path="/Get")
	public @ResponseBody User getUser(@RequestParam int id) {
		Optional<User> u = userRepository.findById(id);
		if (u.isPresent())
			return u.get();
		else
			return null;
	}
	@PostMapping(path="/Add")
	public @ResponseBody PRSMaintenanceReturn addNewUser (@RequestBody User user) {
		try {
			userRepository.save(user);
		}
		catch (Exception e) {
			user = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(user);
	}
	@GetMapping(path="/Remove")
	public @ResponseBody PRSMaintenanceReturn deleteUser (@RequestParam int id) {
		Optional<User> user = userRepository.findById(id);
		try {
			userRepository.delete(user.get());
		}
		catch (Exception e) {
			user = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(user.get());
	}
	@PostMapping(path="/Change")
	public @ResponseBody PRSMaintenanceReturn updateUser (@RequestBody User user) {
		try {
			userRepository.save(user);
		}
		catch (Exception e) {
			user = null;
		}
		return PRSMaintenanceReturn.getMaintReturn(user);
	}
}