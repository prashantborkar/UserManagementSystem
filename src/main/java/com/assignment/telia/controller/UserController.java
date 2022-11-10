package com.assignment.telia.controller;


import com.assignment.telia.exceptionHandler.ResourceException;
import com.assignment.telia.model.UserInformation;
import com.assignment.telia.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@ApiOperation(value = "CREATION OF NEW USER BY THIS API CALL", response = UserInformation.class)
	@PostMapping("/users")
	public ResponseEntity<UserInformation> createUser(@RequestBody UserInformation users) throws ResourceException {
		List<UserInformation> usersList = null;
		boolean emailValidate = false;
		try {
			usersList = new ArrayList<>(userRepository.findByPersonalNumber(users.getPersonalNumber()));
			if (usersList.isEmpty()) {
				emailValidate = patternMatches(users.getEmailAddress());
				if (emailValidate) {
					UserInformation _user = userRepository
							.save(new UserInformation(users.getPersonalNumber(), users.getFullName(), users.getDateOfBirth(), users.getEmailAddress()));
					return new ResponseEntity<>(_user, HttpStatus.CREATED);
				} else {
					throw new Exception();
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			if (users == null) {
				throw new ResourceException(HttpStatus.OK, "Please fill the correct details as all fields are mandatory.");
			} else {
				assert usersList != null;
				if (!usersList.isEmpty()) {
					throw new ResourceException(HttpStatus.OK, "The given User details is already exists in User System Management with Personal Number: " + users.getPersonalNumber());
				} else if (!emailValidate) {
					throw new ResourceException(HttpStatus.OK, "The given User Email is invalid !!! " + users.getEmailAddress());
				} else {
					throw new ResourceException(HttpStatus.OK, "Please fill the correct details as all fields are mandatory.");
				}
			}
		}
	}

	@ApiOperation(value = "Users can able to get their details from this API call and They can fetch the information from the same API by using PERSONAL NUMBER", response = UserInformation.class)
	@GetMapping("/users")
	public ResponseEntity<List<UserInformation>> getAllUsers(@RequestParam(required = false) Long personalNumber) {
		try {
			List<UserInformation> users = new ArrayList<>();

			if (personalNumber == null)
				users.addAll(userRepository.findAll());
			else
				users.addAll(userRepository.findByPersonalNumber(personalNumber));

			if (users.isEmpty()) {
				throw new ResourceException(HttpStatus.NOT_FOUND, "Could not find user with Personal Number: " + personalNumber + ".");
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceException(HttpStatus.NOT_FOUND, "User does not found in UMS with Personal Number: " + personalNumber + ".");
		}
	}

	@ApiOperation(value = "Users can able to fetch the information from API by using PERSONAL NUMBER", response = UserInformation.class)
	@GetMapping("/users/{personalNumber}")
	public ResponseEntity<UserInformation> getUserByPersonalNumber(@PathVariable("personalNumber") long personalNumber) {
		Optional<UserInformation> userData = userRepository.findById(personalNumber);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			throw new ResourceException(HttpStatus.NOT_FOUND, "User does not found in UMS with Personal Number: " + personalNumber + ".");
		}
	}


	@ApiOperation(value = "User can Update their details from this API", response = UserInformation.class)
	@PutMapping("/user/{personalId}")
	public ResponseEntity<UserInformation> updateUserDetails(@PathVariable("personalId") long personalNumber, @RequestBody UserInformation user) {
		Optional<UserInformation> userData = userRepository.findById(personalNumber);

		if (userData.isPresent()) {
			UserInformation _user = userData.get();
			_user.setPersonalNumber(user.getPersonalNumber());
			_user.setFullName(user.getFullName());
			_user.setDateOfBirth(user.getDateOfBirth());
			_user.setEmailAddress(user.getEmailAddress());
			return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "User can Delete their details from this API", response = UserInformation.class)
	@DeleteMapping("/user/{personalNumber}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("personalNumber") long personalNumber) {
		Optional<UserInformation> userData = null;
		try {
			userData = userRepository.findById(personalNumber);
			if (!userData.isEmpty()) {
				userRepository.deleteById(userData.get().getPersonalNumber());
				throw new ResourceException(HttpStatus.OK, "User has been deleted from UMS : " + personalNumber + ".");
			} else {
				throw new ResourceException(HttpStatus.OK, "The given Personal Number doesn't exist in UMS: " + personalNumber);
			}

		} catch (Exception e) {
			if (!userData.isEmpty()) {
				throw new ResourceException(HttpStatus.OK, "User has been deleted from UMS : " + personalNumber + ".");
			} else {
				throw new ResourceException(HttpStatus.OK, "The given Personal Number doesn't exist in UMS: " + personalNumber);
			}
		}
	}

	public static boolean patternMatches(String emailAddress) {
		return Pattern.compile("^(.+)@(\\S+)$")
				.matcher(emailAddress)
				.matches();
	}

}
