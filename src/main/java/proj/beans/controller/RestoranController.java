package proj.beans.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

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

import proj.beans.domain.Jelo;
import proj.beans.domain.NewRadnikMessage;
import proj.beans.domain.NewRestoranMessage;
import proj.beans.domain.OceneHraneIPica;
import proj.beans.domain.Pice;
import proj.beans.domain.Poseta;
import proj.beans.domain.Restoran;
import proj.beans.domain.StringMessage;
import proj.beans.domain.User;
import proj.beans.domain.UserType;
import proj.beans.service.PonudaPonudjacaService;
import proj.beans.service.PonudaService;
import proj.beans.service.PosetaService;
import proj.beans.service.RestoranService;
import proj.beans.service.UserService;

@RestController

public class RestoranController {

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
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//TODO preseliti kod iz userControllera ovde.
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
		restoran.setMenadzer(user.getId());
		restoran.setRadnici(new ArrayList<String>());
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
	public ResponseEntity<Restoran> getRestoran(@PathVariable("id") String id) {
		logger.info("> getRestoran");

		Restoran restorani= restoranService.findOne(id);

		logger.info("< getRestoran");
		return new ResponseEntity<Restoran>(restorani,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/OcenaRestoran/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StringMessage> OcenaRestoran(@PathVariable("id") String id) {
		logger.info("> getRestoran");

		Restoran restorani= restoranService.findOne(id);
		Collection<Poseta> posete= posetaService.findAll();
		double sum=0;
		double count=0;
		for(Poseta pos:posete){
			if(pos.getRestoran().equals(restorani.getId())){
				sum+=pos.getOcena().getOcenaRestorana();
				count++;
			}
		}
		StringMessage out= new StringMessage();
		if(count!=0)
		out.setString(Double.toString(sum/count));
		else
			out.setString("0");
		logger.info("< getRestoran");
		return new ResponseEntity<StringMessage>(out,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/oceneHrane/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<OceneHraneIPica>> oceneHrane(@PathVariable("id") String id) {
		logger.info("> getRestoran");

		Restoran restorani= restoranService.findOne(id);
		Collection<Poseta> posete= posetaService.findAll();
		
		ArrayList<OceneHraneIPica> out= new ArrayList<OceneHraneIPica>();
		
		for(Jelo jel:restorani.getJelovnik()){
			double sum=0;
			double count=0;
			for(Poseta pos:posete){
				if(pos.getRestoran().equals(restorani.getId())){
					for(String nar:pos.getNarucenaJela()){
						if(nar.equals(jel.getId())){
							sum+=pos.getOcena().getOcenaJela();
							count++;
						}
					}
				
			}
			}
			OceneHraneIPica e= new OceneHraneIPica();
			e.setCena(Float.toString(jel.getCena()));
			e.setNaziv(jel.getNaziv());
			e.setOpis(jel.getOpis());
			if(count!=0)
			e.setOcena(Double.toString(sum/count));
			out.add(e);
		}
		for(Pice jel:restorani.getKartaPica()){
			double sum=0;
			double count=0;
			for(Poseta pos:posete){
				if(pos.getRestoran().equals(restorani.getId())){
					for(String nar:pos.getNarucenaJela()){
						if(nar.equals(jel.getId())){
							sum+=pos.getOcena().getOcenaJela();
							count++;
						}
					}
				
			}
			}
			OceneHraneIPica e= new OceneHraneIPica();
			e.setCena(Float.toString(jel.getCena()));
			e.setNaziv(jel.getNaziv());
			e.setOpis(jel.getOpis());
			if(count!=0)
			e.setOcena(Double.toString(sum/count));
			out.add(e);
		}
		logger.info("< getRestoran");
		return new ResponseEntity<ArrayList<OceneHraneIPica>>(out,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/RestoranNaziv/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateRestoranNaziv(@RequestBody StringMessage noviNaziv,@PathVariable("id") String id) throws Exception {
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
	public String updateRestoranOpis(@RequestBody StringMessage noviOpis,@PathVariable("id") String id) throws Exception {
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
	public String RestoranDodajJelo(@RequestBody Jelo novoJelo,@PathVariable("id") String id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		novoJelo.setId(Long.toString((new Random().nextLong()/1000)));
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
			@PathVariable("id") String id,@PathVariable("id2") Long idJela) throws Exception {
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
	public String RestoranDodajPice(@RequestBody Pice novoJelo,@PathVariable("id") String id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		novoJelo.setId(Long.toString((new Random().nextLong()/1000)));
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
			@PathVariable("id") String id,@PathVariable("id2") Long idJela) throws Exception {
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
			value = "/api/RestoranRadnikRegister/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranRadnikRegister(@RequestBody NewRadnikMessage message,@PathVariable("id") String id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		User user= new User();
		user.setEmail(message.getEmail());
		user.setPassword("");
		user.setRepeatedPassword("");
		user.setUsername(message.getUsername());
		user.setDatumRodjenja(message.getDatumRodjenja());
		user.setKonfekcijskiBroj(message.getKonfekcijskiBroj());
		user.setVelicinaObuce(message.getVelicinaObuce());
		user.setIme(message.getIme());
		user.setPrezime(message.getPrezime());
		if(message.getVrsta().equals("konobar"))user.setType(UserType.Konobar);
		if(message.getVrsta().equals("kuvar"))user.setType(UserType.Kuvar);
		if(message.getVrsta().equals("sanker"))user.setType(UserType.Sanker);
		
		User rs=userService.create(user);
		restoran.getRadnici().add(rs.getId());
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		Restoran updateRestoran=restoranService.update(restoran);
		
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
	}
}
