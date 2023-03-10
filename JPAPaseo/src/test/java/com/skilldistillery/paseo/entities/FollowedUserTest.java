package com.skilldistillery.paseo.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FollowedFollowedUserTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private FollowedUser followedUser;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("JPAPaseo");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		FollowedUserKey key = new FollowedUserKey(1, 1);
		followedUser = em.find(FollowedUser.class, key);
				}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		followedUser = null;
	}

	@Test
	void test() {
		assertNotNull(followedUser);
	}

	
}
