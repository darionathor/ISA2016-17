package proj.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import proj.domain.User;
import proj.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@GetMapping
	public ModelAndView getGreetings() {
		logger.info("> getUser");

		Collection<User> user= userService.findAll();

		logger.info("< getUser");
		return new ModelAndView("list", "user", user);
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNew(Model model) {
		model.addAttribute("user", new User());
		return "createUser";
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

}

