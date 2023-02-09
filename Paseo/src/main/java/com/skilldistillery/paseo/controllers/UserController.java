package com.skilldistillery.paseo.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.paseo.entities.User;
import com.skilldistillery.paseo.services.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })

public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("users/{id}")
	public User show(@PathVariable int id, HttpServletRequest req, HttpServletResponse resp, Principal principal) {

		User user = userService.show(principal.getName(), id);
		if (user == null) {
			resp.setStatus(404);
		}

		return user;

	}

	@PutMapping("users/{id}")
	public User update(@PathVariable int id, @RequestBody User user, HttpServletRequest req, HttpServletResponse resp,
			Principal principal) {

		try {
			user = userService.update(principal.getName(), user, id);
			if (user == null) {
				resp.setStatus(404);
			}
			resp.setStatus(204);
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
			user = null;
		}
		return user;

	}

	@DeleteMapping("users/{id}")
	public void deleteUser(@PathVariable int id, HttpServletRequest req, HttpServletResponse resp,
			Principal principal) {
		try {
			if (userService.destroy(principal.getName(), id)) {
				resp.setStatus(204);
			} else {
				resp.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);

		}

	}
}
