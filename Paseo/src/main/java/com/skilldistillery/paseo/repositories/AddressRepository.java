package com.skilldistillery.paseo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.paseo.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	Address findById(int id);
	Address findByStreetAndCityAndStateAndZip(String street, String city, String state, String zip);
}
