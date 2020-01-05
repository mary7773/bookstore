package com.bookstore.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.config.Utils;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.models.User;
import com.bookstore.service.UserService;
import com.bookstore.utility.MailConstructor;

/**
 * @author Marieta
 *
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8181", "http://localhost:4200"})
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailConstructor mailConstructor;

	@Autowired
	private JavaMailSender mailSender;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ResponseEntity<String> newUserPost(HttpServletRequest request, @RequestBody HashMap<String, String> mapper)
			throws Exception {
		String username = mapper.get("username");
		String userEmail = mapper.get("email");

		if (userService.findByUsername(username) != null) {
			return new ResponseEntity<String>("usernameExists", HttpStatus.BAD_REQUEST);
		}

		if (userService.findByEmail(userEmail) != null) {
			return new ResponseEntity<String>("emailExists", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);

		String password = Utils.randomPassword();

		String encryptedPassword = Utils.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);

		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user, userRoles);

		SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(email);

		return new ResponseEntity<String>("User Added Successfully!", HttpStatus.OK);

	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public ResponseEntity<String> forgetPasswordPost(HttpServletRequest request, @RequestBody HashMap<String, String> mapper)
			throws Exception {

		User user = userService.findByEmail(mapper.get("email"));

		if (user == null) {
			return new ResponseEntity<String>("Email not found", HttpStatus.BAD_REQUEST);
		}
		String password = Utils.randomPassword();

		String encryptedPassword = Utils.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		userService.save(user);

		SimpleMailMessage newEmail = mailConstructor.constructNewUserEmail(user, password);
		mailSender.send(newEmail);

		return new ResponseEntity<String>("Email sent!", HttpStatus.OK);

	}

	@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public ResponseEntity<String> profileInfo(
				@RequestBody HashMap<String, Object> mapper
			) throws Exception{
		
		int id = (Integer) mapper.get("id");
		String email = (String) mapper.get("email");
		String username = (String) mapper.get("username");
		String firstName = (String) mapper.get("firstName");
		String lastName = (String) mapper.get("lastName");
		String newPassword = (String) mapper.get("newPassword");
		String currentPassword = (String) mapper.get("currentPassword");
		
		User currentUser = userService.findById(Long.valueOf(id)).get();
		
		if(currentUser == null) {
			throw new Exception ("User not found");
		}
		
		if(userService.findByEmail(email) != null) {
			if(userService.findByEmail(email).getId() != currentUser.getId()) {
				return new ResponseEntity<String>("Email not found!", HttpStatus.BAD_REQUEST);
			}
		}
		
		if(userService.findByUsername(username) != null) {
			if(userService.findByUsername(username).getId() != currentUser.getId()) {
				return new ResponseEntity<String>("Username not found!", HttpStatus.BAD_REQUEST);
			}
		}
			PasswordEncoder passwordEncoder = Utils.passwordEncoder();
			String dbPassword = currentUser.getPassword();
			
			if(null != currentPassword)
			if(passwordEncoder.matches(currentPassword, dbPassword)) {
				if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
					currentUser.setPassword(passwordEncoder.encode(newPassword));
				}
				currentUser.setEmail(email);
			} else {
				return new ResponseEntity<String>("Incorrect current password!", HttpStatus.BAD_REQUEST);
			}
		
		
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setUsername(username);	
		userService.save(currentUser);
		
		return new ResponseEntity<String>("Update Success", HttpStatus.OK);
	}

	@RequestMapping("/getCurrentUser")
	public User getCurrentUser(Principal principal) {
		User user = new User();
		if (null != principal) {
			user = userService.findByUsername(principal.getName());
		}

		return user;
	}

}
