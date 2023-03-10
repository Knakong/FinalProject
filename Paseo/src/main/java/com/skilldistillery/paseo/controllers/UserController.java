package com.skilldistillery.paseo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.paseo.entities.User;
import com.skilldistillery.paseo.repositories.UserRepository;
import com.skilldistillery.paseo.services.AuthService;
import com.skilldistillery.paseo.services.FollowedUserService;
import com.skilldistillery.paseo.services.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })

public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private FollowedUserService followedUserService;
	
	@Autowired 
	private UserRepository userRepo;

	@GetMapping("users")
	public List<User> show(HttpServletRequest req, HttpServletResponse resp) {

		return userService.show();

	}
	
	@GetMapping("users/{id}")
	public User findUser(@PathVariable int id, HttpServletResponse resp) {
		User input = userService.findById(id);
		User output = new User();
		
		if (input == null) {
			resp.setStatus(404);
		} else {
			output.setAddress(input.getAddress());
			output.setBirthdate(input.getBirthdate());
			output.setDescription(input.getDescription());
			output.setEnabled(input.getEnabled());
			output.setFirstName(input.getFirstName());
			output.setLastName(input.getLastName());
			output.setGender(input.getGender());
			output.setId(input.getId());
			output.setProfileImageUrl(input.getProfileImageUrl());
			output.setRole(input.getRole());
			output.setUsername(input.getUsername());
		}
		
		return output;
	}
	
	@GetMapping("users/find/{username}")
	public User findUserByUsername(@PathVariable String username, HttpServletResponse resp ) {
		User output = authService.getUserByUsername(username);
		if (output == null) {
			resp.setStatus(404);
		}
		return output;
	}

	@PutMapping("users/{id}")
	public User update(@PathVariable int id, @RequestBody User user, HttpServletRequest req, HttpServletResponse resp,
			Principal principal) {
		User existing = null;
		System.out.println(user);
		try {
			existing = userService.update(user, id);
			if (existing == null) {
				resp.setStatus(404);
			}

			else {
				resp.setStatus(202);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
		}

		return existing;

	}

	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id, HttpServletRequest req, HttpServletResponse resp,
			Principal principal) {
		try {
			User user = userService.findById(id);
			if (user != null && userService.destroy(user.getUsername(), id)) {
				resp.setStatus(204);
			} else {
				resp.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);

		}

	}

	@GetMapping("users/{id}/friends")
	public List<User> findFriendsByUserId(@PathVariable int id, HttpServletResponse resp) {

		List<User> output = null;
		User user = userService.findById(id);
		

		if (user == null) {
			resp.setStatus(404);
		} else {
			output = followedUserService.findByUserId(id);
			return user.getFollowedUsers();
		}

		return output;
	}
	
	@GetMapping("users/{id}/following")
	public List<User> findFollowingByUserId(@PathVariable int id, HttpServletResponse resp) {
		User user = userService.findById(id);
		List<User> output = null;

		if (user == null) {
			resp.setStatus(404);
		} else {
			output = followedUserService.findByFollowedUserId(user);
		}

		return output;
	}
	
	@GetMapping("users/matches")
	public List<User> findUsersByMatchingPreferences(Principal principal, HttpServletResponse resp) {
		List<User> output = null;
		User loggedInUser = authService.getUserByUsername(principal.getName());
		
		output = userService.findUsersByMatchingPreferences(loggedInUser);
		
		return output;
	}
	
	@GetMapping("users/search/{keyword}")
	public List<User> findByFirstNameOrLastNameOrUsername(@PathVariable("keyword") String keyword, Principal principal, HttpServletResponse resp){
		List<User> searchResult = null;
		
		User loggedInUser = userRepo.findByUsername(principal.getName());
		
		try {
			if(loggedInUser != null) {
				
				searchResult = userService.findByFirstNameLastNameOrUsername(keyword);
				
				resp.setStatus(200);
			}
		} catch (Exception e) {
			resp.setStatus(404);
			e.printStackTrace();
		}
		
		return searchResult;
		
	}

}
