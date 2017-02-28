package proj.beans.controller;

import java.util.ArrayList;
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

import proj.beans.domain.NewRadnikMessage;
import proj.beans.domain.Ponuda;
import proj.beans.domain.PonudaJela;
import proj.beans.domain.PonudaMessage;
import proj.beans.domain.PonudaOutMessage;
import proj.beans.domain.PonudaPica;
import proj.beans.domain.PonudaPonudjaca;
import proj.beans.domain.PonudaPonudjacaMessage;
import proj.beans.domain.PonudaState;
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
public class PonudjacController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

   
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
			value = "/api/Ponudjac",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String Ponudjac(@RequestBody NewRadnikMessage message) throws Exception {
		//System.out.println(noviOpis);
		User user= new User();
		user.setEmail(message.getEmail());
		user.setUsername(message.getUsername());
		user.setPassword("");
		user.setRepeatedPassword("");
		user.setType(UserType.Ponudjac);
		User rs=userService.create(user);
		if (rs==null) {
			return "error";
		}
		return "success";
	}
	@RequestMapping(
			value = "/api/objaviPonudu/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranObjaviPonudu(@RequestBody PonudaMessage ponudaMess,@PathVariable("id") String id) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updatePonuda id:{}", restoran.getId());
		//novoJelo.setId();
		System.out.println(ponudaMess.getDoDatuma()+ponudaMess.getOdDatuma());
		Ponuda ponuda= new Ponuda();
		ponuda.setDo(ponudaMess.getDoDatuma());
		ponuda.setOd(ponudaMess.getOdDatuma());
		ponuda.setRestoran(restoran.getId());
		ponuda.setJelo(ponudaMess.getJelo());
		ponuda.setPice(ponudaMess.getPice());
		Ponuda updatePonuda=ponudaService.create(ponuda);
		if (updatePonuda== null) {
			return "error";
		}
		logger.info("< updatePonuda id:{}", restoran.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/prihvatiPonudu/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String prihvatiPonudu(@RequestBody StringMessage mess,@PathVariable("id") String id) throws Exception {
		PonudaPonudjaca pon= ponudaPonudjacaService.findOne(mess.getString());
		//System.out.println(noviOpis);
		logger.info("> updatePonuda id:{}", pon.getId());
		//novoJelo.setId();
		Ponuda ponuda= ponudaService.findOne(pon.getPonuda());
		ponuda.setPrihvacenaPonuda(pon.getId());
		Ponuda updatePonuda=ponudaService.update(ponuda);
		
		pon.setStanje(PonudaState.accepted);
		Collection<PonudaPonudjaca> ponkol= ponudaPonudjacaService.findAll();
		for(PonudaPonudjaca p:ponkol){
			if(p.getPonuda().equals(ponuda.getId())&& !p.getId().equals(pon.getId())){
				p.setStanje(PonudaState.failed);
				ponudaPonudjacaService.update(p);
			}
			
		}
		if (updatePonuda== null) {
			return "error";
		}
		logger.info("< updatePonuda id:{}", pon.getId());
		return "success";
	}
	@RequestMapping(
			value = "/api/ponude/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaOutMessage>> getPonude(@PathVariable("id") String id) {
		logger.info("> getRestoran");
		Collection<PonudaPonudjaca> SveponudePon= ponudaPonudjacaService.findAll();
		
		Collection<Ponuda> Sveponude= ponudaService.findAll();
		ArrayList<PonudaOutMessage> outmessagi= new ArrayList<PonudaOutMessage>();
		Restoran restorani =restoranService.findOne(id);
		for (Ponuda a : Sveponude){
			if(a.getRestoran().equals(id)){
				PonudaOutMessage pom=new PonudaOutMessage(a,restorani);
				
				for(PonudaPonudjaca p:SveponudePon){
					if(p.getPonuda().equals(a.getId())){
						pom.getPonude().add(p);
					}
				}
				outmessagi.add(pom);
			}
		}
		
		logger.info("< getRestoran");
		return new ResponseEntity<Collection<PonudaOutMessage>>(outmessagi,
				HttpStatus.OK);
	}
	@RequestMapping(
			value = "/api/svePonude",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaOutMessage>> getSvePonude() {
		logger.info("> getRestoran");

		Collection<Ponuda> Sveponude= ponudaService.findAll();
		ArrayList<PonudaOutMessage> outmessagi= new ArrayList<PonudaOutMessage>();
		Collection<Restoran> restorani =restoranService.findAll();
		for (Ponuda a : Sveponude){
			for(Restoran r: restorani){
				if(a.getRestoran().equals(r.getId())){
					outmessagi.add(new PonudaOutMessage(a,r));
					break;
				}
			}
		}
		
		logger.info("< getRestoran");
		return new ResponseEntity<Collection<PonudaOutMessage>>(outmessagi,
				HttpStatus.OK);
	}

	@RequestMapping(
			value = "/api/ponudi/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String PonudjacObjaviPonudu(@RequestBody PonudaPonudjacaMessage ponudaMess,@PathVariable("id") String id) throws Exception {
		Ponuda ponuda=ponudaService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> ponudiPonudu id:{}", ponuda.getId());
		//novoJelo.setId();
		PonudaPonudjaca pp= new PonudaPonudjaca();
		pp.setPonuda(ponuda.getId());
		pp.setpJela(new ArrayList<PonudaJela>());
		pp.setpPice(new ArrayList<PonudaPica>());
		for(ArrayList<String> l:ponudaMess.getIdArtikla()){
			String idd=l.get(0);
			String kol=l.get(1);
			String cena=l.get(2);
			for(String st:ponuda.getJelo()){
				if(st.equals(idd)){
					pp.getpJela().add(new PonudaJela(idd,kol,cena));
					break;
				}
			}
			for(String st:ponuda.getPice()){
				if(st.equals(idd)){
					pp.getpPice().add(new PonudaPica(idd,kol,cena));
					break;
				}
			}
		}
		PonudaPonudjaca updatePonuda=ponudaPonudjacaService.create(pp);
		if (updatePonuda== null) {
			return "error";
		}
		logger.info("< ponudiPonudu id:{}", ponuda.getId());
		return "success";
	}
}
