package com.example.demo.controller;

import java.util.concurrent.ExecutionException;

import com.example.demo.service.FirebaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.objects.Person;

@RestController
public class RestDemoController {
	@Autowired
	FirebaseServices firebaseServices;
	@GetMapping("/getUserDetails")
	public Person getExample(@RequestHeader() String name) throws InterruptedException, ExecutionException {
		return firebaseServices.getUserDetails(name);
	}

	@PostMapping("/createUser")
	public String postExample(@RequestBody Person person) throws InterruptedException, ExecutionException {
		return firebaseServices.saveUserDetail(person);
	}



	@PutMapping("/updateUser")
	public String putExample(@RequestBody Person person) throws InterruptedException, ExecutionException {
		return firebaseServices.updateUserDetail(person);
	}

	@DeleteMapping("/deleteUser")
	public String deleteExample(@RequestHeader String name) {
		return firebaseServices.deleteUserDetail(name);
	}

}
