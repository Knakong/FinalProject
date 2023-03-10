package com.skilldistillery.paseo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.paseo.entities.Address;
import com.skilldistillery.paseo.entities.User;
import com.skilldistillery.paseo.services.AddressService;
import com.skilldistillery.paseo.services.UserService;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost" })

public class AddressController {

	@Autowired
	private AddressService addressService;
	@Autowired
	private UserService userService;
	
	@GetMapping("address")
	public List<Address> index() {
		return addressService.index();
	}

	@PostMapping("address/user/{userId}")
	public Address createUserAddress(Principal principal, @PathVariable int userId, @RequestBody Address address,
			HttpServletRequest req, HttpServletResponse res) {
		Address newAddress = null;
		try {

			User user = userService.findById(userId);
			if (user == null) {
				res.setStatus(404);
			} else {
				newAddress = addressService.create(address);

				user.setAddress(newAddress);

				userService.update(user, user.getId());
				res.setStatus(202);

			}
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();

		}
	return newAddress;
	}
 
	@PostMapping("address")
	public Address create(Principal principal, @RequestBody Address address, HttpServletResponse res,
			HttpServletRequest req) {

		Address newAddress = null;
		try {
			try {

				newAddress = addressService.create(address);

				res.setStatus(202);

			}

			catch (Exception e) {
				res.setStatus(400);
				e.printStackTrace();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newAddress;
	}

	@PutMapping("address/{addressId}")
	public Address updateAddress(Principal princial, @PathVariable int addressId, @RequestBody Address address,
			HttpServletRequest req, HttpServletResponse res) {

		Address existing = null;

		try {
			existing = addressService.findById(addressId);
			if (existing == null) {
				res.setStatus(404);
			} else {
				existing = addressService.update(address, addressId);
				res.setStatus(202);
				res.setHeader("Location", "http://localhost:8090/api/address/" + existing.getId());

			}
		} catch (Exception e) {
			res.setStatus(400);
			e.printStackTrace();

		}

		return existing;
	}

}
