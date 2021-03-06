package proj.beans.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

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
	public String Ponudjac(@RequestBody NewRadnikMessage message, HttpSession session) throws Exception {
		//System.out.println(noviOpis);
		String id= (String)session.getAttribute("user");
		User menadzer=null;
		if(id!=null)
			menadzer=userService.findOne(id);
		
		if(menadzer!=null && menadzer.getType().equals(UserType.MenadzerRestorana)){
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
		}return "failed";
	}
	@RequestMapping(
			value = "/api/objaviPonudu/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranObjaviPonudu(@RequestBody PonudaMessage ponudaMess,@PathVariable("id") String id, HttpSession session) throws Exception {
		String idusera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idusera!=null)
			menadzer=userService.findOne(idusera);
		
		
		
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updatePonuda id:{}", restoran.getId());
		//novoJelo.setId();
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
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
		}else return "failed";
	}
	@RequestMapping(
			value = "/api/prihvatiPonudu/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String prihvatiPonudu(@RequestBody StringMessage mess,@PathVariable("id") String id, HttpSession session) throws Exception {
		PonudaPonudjaca pon= ponudaPonudjacaService.findOne(mess.getString());
		//System.out.println(noviOpis);
		logger.info("> updatePonuda id:{}", pon.getId());
		//novoJelo.setId();		
		
		Ponuda ponuda= ponudaService.findOne(pon.getPonuda());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		Restoran restoran=restoranService.findOne(ponuda.getRestoran());
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		
		
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
		ponudaPonudjacaService.update(pon);
		if (updatePonuda== null) {
			return "error";
		}
		logger.info("< updatePonuda id:{}", pon.getId());
		return "success";
		}return "failed";
	}
	@RequestMapping(
			value = "/api/ponude/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaOutMessage>> getPonude(@PathVariable("id") String id,HttpSession session) throws ParseException {
		logger.info("> getRestoran");
		Collection<PonudaPonudjaca> SveponudePon= ponudaPonudjacaService.findAll();
		
		Collection<Ponuda> Sveponude= ponudaService.findAll();
		ArrayList<PonudaOutMessage> outmessagi= new ArrayList<PonudaOutMessage>();
		Restoran restoran =restoranService.findOne(id);
		
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			

		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		
		for (Ponuda a : Sveponude){
			if(a.getRestoran().equals(id)){
				PonudaOutMessage pom=new PonudaOutMessage(a,restoran);
				
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
		return new ResponseEntity<Collection<PonudaOutMessage>>(outmessagi,
				HttpStatus.FORBIDDEN);
	}
	@RequestMapping(
			value = "/api/svePonude",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PonudaOutMessage>> getSvePonude(HttpSession session) throws ParseException {
		logger.info("> getRestoran");
		String idPonudjaca=(String) session.getAttribute("user");
		User ponudjac=null;
		ArrayList<PonudaOutMessage> outmessagi= new ArrayList<PonudaOutMessage>();
		if(idPonudjaca!=null)
			ponudjac=userService.findOne(idPonudjaca);
		if(ponudjac!=null && ponudjac.getType().equals(UserType.Ponudjac)){
		Collection<Ponuda> Sveponude= ponudaService.findAll();
		Collection<Restoran> restorani =restoranService.findAll();
		for (Ponuda a : Sveponude){
			for(Restoran r: restorani){
				if(a.getRestoran().equals(r.getId())){
					PonudaOutMessage o=new PonudaOutMessage(a,r);
					Collection<PonudaPonudjaca> po= ponudaPonudjacaService.findAll();
					for(PonudaPonudjaca p:po){
						if(p.getPonudjac().equals(ponudjac.getId()) && p.getPonuda().equals(a.getId())){
							ArrayList<PonudaPonudjaca> ponude = new ArrayList<PonudaPonudjaca>();
									ponude.add(p);
							o.setPonude(ponude);
							break;
						}
					}
					if(a.getPrihvacenaPonuda()!=null){
					PonudaPonudjaca ppp=
							ponudaPonudjacaService.findOne(a.getPrihvacenaPonuda());
					if(ppp!=null && ppp.getPonudjac().equals(ponudjac.getId()))o.setAccepted(true);
					else o.setAccepted(false);
					}
					outmessagi.add(o);
					break;
				}
			}
		}
		
		logger.info("< getRestoran");
		return new ResponseEntity<Collection<PonudaOutMessage>>(outmessagi,
				HttpStatus.OK);
		}return new ResponseEntity<Collection<PonudaOutMessage>>(outmessagi,
				HttpStatus.FORBIDDEN);
	}

	@RequestMapping(
			value = "/api/ponudi/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String PonudjacObjaviPonudu(@RequestBody PonudaPonudjacaMessage ponudaMess,@PathVariable("id") String id,HttpSession session) throws Exception {
		Ponuda ponuda=ponudaService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> ponudiPonudu id:{}", ponuda.getId());
		//novoJelo.setId();
		String idPonudjaca=(String) session.getAttribute("user");
		User ponudjac=null;
		if(idPonudjaca!=null)
			ponudjac=userService.findOne(idPonudjaca);
		if(ponudjac!=null && ponudjac.getType().equals(UserType.Ponudjac)){
		Collection<PonudaPonudjaca> ponude= ponudaPonudjacaService.findAll();
		PonudaPonudjaca pp=null;
		boolean update=false;
		for(PonudaPonudjaca p:ponude){
			if(p.getPonudjac().equals(idPonudjaca)&& p.getPonuda().equals(ponuda.getId())){
				pp=p;
				update=true;
				break;
			}
		}if(pp==null)
		pp= new PonudaPonudjaca();
		pp.setPonuda(ponuda.getId());
		pp.setPonudjac(ponudjac.getId());
		pp.setpJela(new ArrayList<PonudaJela>());
		pp.setpPice(new ArrayList<PonudaPica>());
		for(ArrayList<String> l:ponudaMess.getIdArtikla()){
			String idd=l.get(0);
			String kol=l.get(1);
			String cena=l.get(2);
			if(ponuda.getJelo()!=null)
			for(String st:ponuda.getJelo()){
				if(st.equals(idd)){
					pp.getpJela().add(new PonudaJela(idd,kol,cena));
					break;
				}
			}
			if(ponuda.getPice()!=null)
			for(String st:ponuda.getPice()){
				if(st.equals(idd)){
					pp.getpPice().add(new PonudaPica(idd,kol,cena));
					break;
				}
			}
		}PonudaPonudjaca updatePonuda=null;
		if(update)updatePonuda=ponudaPonudjacaService.update(pp);
		else
		 updatePonuda=ponudaPonudjacaService.create(pp);
		if (updatePonuda== null) {
			return "error";
		}
		logger.info("< ponudiPonudu id:{}", ponuda.getId());
		return "success";
		}return "failed";
	}
}
