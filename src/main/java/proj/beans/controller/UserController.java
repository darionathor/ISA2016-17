package proj.beans.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.view.RedirectView;

import proj.beans.domain.StringMessage;
import proj.beans.domain.User;
import proj.beans.domain.UserType;
import proj.beans.service.PonudaPonudjacaService;
import proj.beans.service.PonudaService;
import proj.beans.service.PosetaService;
import proj.beans.service.RestoranService;
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


	@Autowired
	private RestoranService restoranService;
	@Autowired
	private PonudaService ponudaService;
	@Autowired
	private PonudaPonudjacaService ponudaPonudjacaService;
	@Autowired
	private PosetaService posetaService;

	
	
	@RequestMapping(
			value = "/api/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<User>> getUsers(HttpSession session) {
		logger.info("> getUser");
		String id=(String)session.getAttribute("user");
		User user=null;
		if(id!=null)
		user= userService.findOne(id);
		Collection<User> users=new ArrayList<User>();
		if(user!=null && user.getType().equals(UserType.MenadzerSistema))
			users= userService.findAll();

		logger.info("< getUser");
		return new ResponseEntity<Collection<User>>(users,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/loggedUserType",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringMessage> loggedUserType(HttpSession session) {
		logger.info("> getUserType");
		StringMessage mess= new StringMessage();
		String id=(String)session.getAttribute("user");
		User user=null;
		if(id!=null)
		user= userService.findOne(id);
		if(user!=null){
			if(user.getType().equals(UserType.Gost))mess.setString("gost");
			if(user.getType().equals(UserType.Konobar))mess.setString("konobar");
			if(user.getType().equals(UserType.Kuvar))mess.setString("kuvar");
			if(user.getType().equals(UserType.MenadzerRestorana))mess.setString("menadzerRestorana");
			if(user.getType().equals(UserType.MenadzerSistema))mess.setString("menadzerSistema");
			if(user.getType().equals(UserType.Ponudjac))mess.setString("ponudjac");
			if(user.getType().equals(UserType.Sanker))mess.setString("sanker");
		}else mess.setString("failed");

		logger.info("< getUserType");
		return new ResponseEntity<StringMessage>(mess,
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
			@PathVariable("id") String id) {	
		logger.info("> deleteUser id:{}", id);
		userService.delete(id);
		logger.info("< deleteUser id:{}", id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
	@RequestMapping(
			value = "/api/logout",
			method = RequestMethod.POST)
	public ResponseEntity<StringMessage> logout(
			HttpSession session) {	
		logger.info("> logout");
		session.setAttribute("user", null);
		logger.info("< logout");
		return new ResponseEntity<StringMessage>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(
			value = "/api/newMenadzerSistema",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String createNewMenadzerSistema(
			@RequestBody User user, HttpSession session) throws Exception {
		String id=(String)session.getAttribute("user");
		User us=null;
		if(id!=null)
			us= userService.findOne(id);
		if(us!=null && us.getType().equals(UserType.MenadzerSistema))
		logger.info("> createMenadzerSistema");
		user.setType(UserType.MenadzerSistema);
		Iterator<User> it = userService.findAll().iterator();
		while(it.hasNext()){
			if(it.next().getUsername().equals(user.getUsername()))
					return "exists";
		}
		userService.create(user);
		logger.info("< createMenadzerSistema");
		return  "done";
	}
	
	@RequestMapping(
			value = "/api/guestRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView guestRegistration(
			@RequestBody User user, HttpServletResponse response) throws Exception {
		logger.info("> guestRegistration");
		user.setType(UserType.Gost);
		Iterator<User> it = userService.findAll().iterator();
		while(it.hasNext()){
			if(it.next().getUsername().equals(user.getUsername()))


		        return new RedirectView("index.html", false);
		}
		
		userService.create(user);
		logger.info("< guestRegistration");

        return new RedirectView("index.html",false);
	}
	

	@RequestMapping(
			value = "/api/guestLogin",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView guestLogin(
			@RequestBody User user, HttpServletResponse response,HttpSession session) throws Exception {
		logger.info("> guestLogin");
		user.setType(UserType.Gost);
		Iterator<User> it = userService.findAll().iterator();
		while(it.hasNext()){
			User use=it.next();
			if(use.getUsername().equals(user.getUsername())){
				if(use.getPassword().equals(user.getPassword())){
					System.out.println("ziv je, ziiiiv");
					session.setAttribute("user", use.getId());
				}
			}
				
			
		}
		logger.info("< guestLogin");

        return new RedirectView("");
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

