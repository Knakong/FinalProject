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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.paseo.entities.User;
import com.skilldistillery.paseo.entities.UserWalk;
import com.skilldistillery.paseo.entities.Walk;
import com.skilldistillery.paseo.services.AuthService;
import com.skilldistillery.paseo.services.UserService;
import com.skilldistillery.paseo.services.UserWalkService;
import com.skilldistillery.paseo.services.WalkService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })
public class WalkController {

	@Autowired
	private WalkService walkService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService auth;

	@Autowired
	private UserWalkService userWalkService;

	@GetMapping("walks")
	public List<Walk> show(Principal principal) {
		User user = auth.getUserByUsername(principal.getName());
		return walkService.showWalksThatArePublic(user);
	}

	@PostMapping("walks/search")
	public List<Walk> findWalksBySearch(@RequestBody Walk searchWalk, HttpServletResponse res, Principal princal) {
		List<Walk> results = null;
		System.out.println(searchWalk);
		try {
			results = walkService.searchByWalk(searchWalk);
			if (results.isEmpty()) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();
		}

		return results;

	}

	@GetMapping("walks/{walkId}")
	public Walk findByWalkId(@PathVariable int walkId, HttpServletResponse res) {
		Walk output = null;
		output = walkService.findById(walkId);
		System.out.println(output);
		if (output == null) {
			res.setStatus(404);
		}
		return output;
	}

	@GetMapping("walks/user/{userId}")
	public List<Walk> findAllByUserId(@PathVariable("userId") int id, HttpServletResponse res, HttpServletRequest req) {
		boolean enabled = true;
		if (id > 0 && enabled) {
			List<Walk> userWalks = null;

			try {

				userWalks = walkService.findAllWalksByUserId(id);
				res.setStatus(200);
				return userWalks;

			} catch (Exception e) {
				res.setStatus(405);
				e.printStackTrace();
			}

		}
		return null;
	}

	@PostMapping("walks")
	public Walk create(@RequestBody Walk walk, HttpServletResponse resp, HttpServletRequest req) {
		Walk newWalk = null;

		try {
			newWalk = walkService.create(walk, walk.getUser().getId());
			resp.setStatus(201);

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
		}
		return newWalk;
	}

	@PutMapping("walks/{walkId}")
	public Walk updateWalk(Principal principal, @PathVariable int walkId, @RequestBody Walk walk,
			HttpServletRequest req, HttpServletResponse res) {

		Walk updatedWalk = null;

		try {
			updatedWalk = walkService.findById(walkId);
			if (updatedWalk == null) {
				res.setStatus(404);
			} else {
				User loggedInUser = auth.getUserByUsername(principal.getName());
				if (loggedInUser.getUsername() == updatedWalk.getUser().getUsername()
						|| loggedInUser.getRole().equals("ADMIN")) {
					updatedWalk = walkService.update(walk, walkId);
					res.setStatus(202);
				} else {
					res.setStatus(401);
				}
			}
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();

		}

		return updatedWalk;
	}

	@DeleteMapping("walks/{walkId}")
	public void delete(Principal principal, @PathVariable int walkId, HttpServletResponse res) {
		Walk deleteMe = walkService.findById(walkId);
		User loggedInUser = auth.getUserByUsername(principal.getName());

		if (loggedInUser.getUsername() == deleteMe.getUser().getUsername() || loggedInUser.getRole().equals("ADMIN")) {
			boolean deleted = walkService.disableWalk(walkId);
			try {
				if (deleted == true) {
					res.setStatus(200);
				}
			} catch (Exception e) {
				e.printStackTrace();
				res.setStatus(404);
			}
		} else {
			res.setStatus(401);
		}

	}

	@GetMapping("walks/joined/{id}")
	public List<Walk> findJoinedWalksByUserId(@PathVariable int id, HttpServletResponse res) {
		List<Walk> output = walkService.getJoinedWalksByUserId(id);
		if (output == null || output.isEmpty()) {
			res.setStatus(404);
		}
		return output;
	}

	@PostMapping("walks/join/{walkId}")
	public void joinWalk(@PathVariable int walkId, Principal principal, HttpServletResponse res) {
		Walk walk = walkService.findById(walkId);
		User loggedInUser = auth.getUserByUsername(principal.getName());

		if (walk != null && loggedInUser != null) {
			try {
				userWalkService.create(loggedInUser, walk);
				res.setStatus(201);
			} catch (Exception e) {
				e.printStackTrace();
				res.setStatus(400);
			}
		} else {
			res.setStatus(404);
		}
	}
	
	@DeleteMapping("walks/leave/{walkId}")
	public void leaveWalk(@PathVariable int walkId, Principal principal, HttpServletResponse res) {
		Walk walk = walkService.findById(walkId);
		User loggedInUser = auth.getUserByUsername(principal.getName());

		if (walk != null && loggedInUser != null) {
			try {
				userWalkService.delete(loggedInUser, walk);
				res.setStatus(204);
			} catch (Exception e) {
				e.printStackTrace();
				res.setStatus(400);
			}
		} else {
			res.setStatus(404);
		}
	}
}
