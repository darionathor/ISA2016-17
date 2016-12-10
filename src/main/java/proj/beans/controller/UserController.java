package proj.beans.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import proj.beans.domain.User;
import proj.beans.service.UserService;

@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/api")
    public String welcome() {//Welcome page, non-rest
        return "Welcome to RestTemplate Example.";
    }
	@Autowired
	private UserService userService;

	@RequestMapping(
			value = "/api/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> getUsers() {
		logger.info("> getUser");

		Collection<User> user= userService.findAll();

		logger.info("< getUser");
		return new ResponseEntity<Collection<User>>(user,
				HttpStatus.OK);
	}

	@RequestMapping(
			value = "/api/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(
			@RequestBody User user) throws Exception {
		logger.info("> createGreeting");
		User saveduser = userService.create(user);
		logger.info("< createGreeting");
		return new ResponseEntity<User>(saveduser, HttpStatus.CREATED);
	}

	@RequestMapping(
			value = "/api/users/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateGreeting(
			@RequestBody User user) throws Exception {
		logger.info("> updateUser id:{}", user.getId());
		User updatedUser = userService.update(user);
		if (updatedUser== null) {
			return new ResponseEntity<User>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updateUser id:{}", user.getId());
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/api/user/{id}",
			method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(
			@PathVariable("id") Long id) {
		logger.info("> deleteUser id:{}", id);
		userService.delete(id);
		logger.info("< deleteUser id:{}", id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	
	/*
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNew(Model model) {
		model.addAttribute("user", new User());
		return "user";
	}
	
	@PostMapping(value = "/create")
	public ModelAndView createUser(@Valid User user, BindingResult result) throws Exception {
		logger.info("> createUser");
		if (result.hasErrors()) {
			return new ModelAndView("createUser", "formErrors", result.getAllErrors());
		}
		userService.create(user);
		logger.info("< createUser");
		return new ModelAndView("redirect:/user", "user", userService.findAll());
	}
	
	@PostMapping(value = "/update")
	public ModelAndView updateUser(@Valid User user, BindingResult result) throws Exception {
		logger.info("> updateUser");
		if (result.hasErrors()) {
			return new ModelAndView("updateUser", "formErrors", result.getAllErrors());
		}
		userService.update(user);
		logger.info("< updateUser");
		return new ModelAndView("redirect:/user", "user", userService.findAll());
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String getUpdate(@PathVariable Long id, Model model) {
		logger.info("> updateUser id:{}", id);
		model.addAttribute("user", userService.findOne(id));
		logger.info("< updateUser id:{}", id);
		return "updateUser";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable Long id) {
		logger.info("> deleteUser id:{}", id);
		userService.delete(id);
		logger.info("< deleteUser id:{}", id);
		return "redirect:..";
	}	
	
	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}
*/
}

