package com.jvs.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jvs.models.User;
import com.jvs.pojo.loginPojo;
import com.jvs.pojo.signupPojo;
import com.jvs.services.UserService;


@Controller
public class MainController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/")
	public String init() {
		return "index";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@GetMapping("/index")
	public String login() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	
	@PostMapping("/edit")
	@ResponseBody
	public String editUser(@ModelAttribute signupPojo signupPojo, BindingResult bindingResult, HttpServletRequest req,
			HttpServletResponse res, HttpSession session) throws IOException {
		if (session.getAttribute("roles").equals("ADMIN")) {
			if (signupPojo != null && validateForm(signupPojo)) {
				User user = new User();
				user.setFullname(signupPojo.getFullname());
				user.setUsername(signupPojo.getUsername());
				user.setPassword(bCryptPasswordEncoder.encode(signupPojo.getPassword()));
				user.setRoles(signupPojo.getFullname());
				user.setId(signupPojo.getId());
				userService.updateUser(user);
			}
			return getJsonFromList(userService.findAllUsers());
		} else {
			req.setAttribute("errorMessage", "UnAuthorized Access");
			res.sendRedirect("error");
			return "error";
		}
	}
	
	@GetMapping("/delete")
	@ResponseBody
	public String deleteUser(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		if(session.getAttribute("roles").equals("ADMIN")) {
			userService.deleteUser(Long.parseLong(id));
			return getJsonFromList(userService.findAllUsers());
		} else {
			req.setAttribute("errorMessage", "UnAuthorized Access");
			res.sendRedirect("error");
			return "error";
		}
			
		
	}
	
	
	@PostMapping("/signup")
	public String doSignup(@ModelAttribute signupPojo signupPojo, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws IOException {
		System.out.println(signupPojo);
		if(validateForm(signupPojo) && !isUserAlreadyExist(signupPojo)){
			User usr = new User();
			usr.setFullname(signupPojo.getFullname());
			usr.setPassword(bCryptPasswordEncoder.encode(signupPojo.getPassword()));
			usr.setUsername(signupPojo.getUsername());
			if(checkFirstUser(signupPojo)){
				usr.setRoles("ADMIN");
				long id = userService.saveUser(usr);
				if(id > 0) {
					req.setAttribute("fullname", usr.getFullname());
					req.setAttribute("message", "Signup Sucesssful");
					res.sendRedirect("/home");
					return "index";
				} else {
					req.setAttribute("errorMessage", "Login Failed");
					return "error";
				}
					
			} else {
				usr.setRoles(signupPojo.getRoles());
				long id = userService.saveUser(usr);
				
				if(id > 0) {
					req.setAttribute("fullname", usr.getFullname());
					req.setAttribute("message", "Signup Sucesssful");
					return "index";
				} else {
					req.setAttribute("errorMessage", "Login Failed");
					return "error";
				}
			}
			
		}
		return "index";
	}
	
	@GetMapping("/findAllUsers")
	@ResponseBody
	public String getAllUsers() {
		return getJsonFromList(userService.findAllUsers());
	}
	
	@GetMapping("/findUsers")
	@ResponseBody
	public String getUserByUserName(@RequestParam("username") String username) throws JsonGenerationException, JsonMappingException, IOException {
		User user = userService.findUserByUserName(username);
		if(user != null)
			return user.getUsername();
		else
			return null;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginUser");
		session.removeAttribute("loginStatus");
		session.removeAttribute("roles");
		return "index";
	}
	
	@PostMapping("/login")
	public String loginUser(@ModelAttribute loginPojo loginPojo, BindingResult bindingResult, HttpServletRequest req, HttpServletResponse res, HttpSession session) throws ServletException, IOException {
		System.out.println(loginPojo);
		User user = validateUserLogin(loginPojo);
		System.out.println(user);
		if(user != null ) {
			session.setAttribute("loginUser", user.getFullname());
			session.setAttribute("loginStatus", "success");
			session.setAttribute("roles", user.getRoles());
			if(user.getRoles().equalsIgnoreCase("ADMIN")) {
				return "admin";
			}
			else
				return "home";
		} else {
			req.setAttribute("loginStatus", "failed");
			return "index";
		}
	}
	
	public boolean validateForm(signupPojo signupPojo) {
		if(signupPojo.getPassword() != "" && signupPojo.getCnfPassword() != "" && signupPojo.getPassword().equals(signupPojo.getCnfPassword()) && signupPojo.getFullname() != "" && signupPojo.getRoles() != "" && signupPojo.getUsername() != "") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean checkFirstUser(signupPojo signupPojo) {
		if(userService.getUserCount() < 1)
			return true;
		return false;
	}
	
	public boolean isUserAlreadyExist(signupPojo signupPojo) {
		User usr = userService.findUserByUserName(signupPojo.getUsername());
		if(usr == null) 
			return false;
		else
			return true;
	}
	
	public User validateUserLogin(loginPojo loginPojo) {
		
		User user = userService.findUserByUserName(loginPojo.getUsername());
		if (user != null && user.getId() > 0)
			if (bCryptPasswordEncoder.matches(loginPojo.getPassword(), user.getPassword()))
				return user;

		return null;
	}
	
	
	public static String getJsonFromList(Collection<?> collection) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(collection);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return jsonString;
	}
	
	
}
