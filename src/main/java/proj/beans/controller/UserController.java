package proj.beans.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

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

import proj.beans.domain.Jelo;
import proj.beans.domain.NewRestoranMessage;
import proj.beans.domain.Pice;
import proj.beans.domain.Restoran;
import proj.beans.domain.StringMessage;
import proj.beans.domain.User;
import proj.beans.domain.UserType;
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

	@RequestMapping(
			value = "/api/newRestoran",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String createNewMenadzerSistema(
			@RequestBody NewRestoranMessage restoranMessage) throws Exception {
		logger.info("> createRestoran");
		User user = new User();
		user.setType(UserType.MenadzerRestorana);
		user.setEmail(restoranMessage.getEmail());
		user.setUsername(restoranMessage.getUsername());
		user.setPassword(restoranMessage.getPassword());
		Iterator<User> it = userService.findAll().iterator();
		while(it.hasNext()){
			if(it.next().getUsername().equals(user.getUsername()))
					return "existsMenadzer";
		}
		Iterator<Restoran> it2 = restoranService.findAll().iterator();
		while(it2.hasNext()){
			if(it2.next().getNaziv().equals(restoranMessage.getNaziv()))
					return "existsRestoran";
		}
		
		userService.create(user);
		Restoran restoran=new Restoran();
		restoran.setJelovnik(new ArrayList<Jelo>());
		restoran.setKartaPica(new ArrayList<Pice>());
		restoran.setNaziv(restoranMessage.getNaziv());
		restoranService.create(restoran);
		
		logger.info("< createRestoran");
		return  "done";
	}
	@RequestMapping(
			value = "/api/restorani",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Restoran>> getRestorans() {
		logger.info("> getRestoran");

		Collection<Restoran> restorani= restoranService.findAll();

		logger.info("< getRestoran");
		return new ResponseEntity<Collection<Restoran>>(restorani,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/Restoran/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restoran> getRestoran(@PathVariable("id") Long id) {
		logger.info("> getRestoran");

		Restoran restorani= restoranService.findOne(id);

		logger.info("< getRestoran");
		return new ResponseEntity<Restoran>(restorani,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/RestoranNaziv/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateRestoranNaziv(@RequestBody StringMessage noviNaziv,@PathVariable("id") Long id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		restoran.setNaziv(noviNaziv.getString());
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/RestoranOpis/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateRestoranOpis(@RequestBody StringMessage noviOpis,@PathVariable("id") Long id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		restoran.setVrsta(noviOpis.getString());
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/RestoranDodajJelo/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranDodajJelo(@RequestBody Jelo novoJelo,@PathVariable("id") Long id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		novoJelo.setId(new Random().nextLong()/1000);
		restoran.getJelovnik().add(novoJelo);
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/RestoranObrisiJelo/{id}/{id2}",
			method = RequestMethod.DELETE)
	public String deleteJelo(
			@PathVariable("id") Long id,@PathVariable("id2") Long idJela) throws Exception {
		logger.info("> deleteJelo id:{}", idJela);
		//System.out.println(idJela);
		Restoran restoran=restoranService.findOne(id);
		for(int i=0;i<restoran.getJelovnik().size();i++){
			System.out.println(restoran.getJelovnik().get(i).getId());
			if(idJela.equals(restoran.getJelovnik().get(i).getId())){
				restoran.getJelovnik().remove(i);
				logger.info("< true ");
				break;
			}
		}
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< deleteJelo id:{}", idJela);
		return "success";
	}
	@RequestMapping(
			value = "/api/RestoranDodajPice/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranDodajPice(@RequestBody Pice novoJelo,@PathVariable("id") Long id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		novoJelo.setId(new Random().nextLong()/1000);
		restoran.getKartaPica().add(novoJelo);
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/RestoranObrisiPice/{id}/{id2}",
			method = RequestMethod.DELETE)
	public String deletePice(
			@PathVariable("id") Long id,@PathVariable("id2") Long idJela) throws Exception {
		logger.info("> deletePice id:{}", idJela);
		//System.out.println(idJela);
		Restoran restoran=restoranService.findOne(id);
		for(int i=0;i<restoran.getKartaPica().size();i++){
			System.out.println(restoran.getKartaPica().get(i).getId());
			if(idJela.equals(restoran.getKartaPica().get(i).getId())){
				restoran.getKartaPica().remove(i);
				logger.info("< true ");
				break;
			}
		}
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< deletePice id:{}", idJela);
		return "success";
	}
	
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

	@RequestMapping(
			value = "/api/newMenadzerSistema",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String createNewMenadzerSistema(
			@RequestBody User user) throws Exception {
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
			@RequestBody User user, HttpServletResponse response) throws Exception {
		
		logger.info("> guestLogin");
		user.setType(UserType.Gost);
		Iterator<User> it = userService.findAll().iterator();
		while(it.hasNext()){
			if(it.next().getUsername().equals(user.getUsername()))


		        return new RedirectView("index.html");
		}
		logger.info("< guestLogin");

        return new RedirectView("index.html");
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

