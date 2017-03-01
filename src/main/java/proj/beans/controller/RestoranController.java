package proj.beans.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

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

import proj.beans.domain.Jelo;
import proj.beans.domain.NewRadnikMessage;
import proj.beans.domain.NewRestoranMessage;
import proj.beans.domain.OceneHraneIPica;
import proj.beans.domain.OceneKonobaraMessage;
import proj.beans.domain.Pice;
import proj.beans.domain.PonudaMessage;
import proj.beans.domain.Poseta;
import proj.beans.domain.Raspored;
import proj.beans.domain.RasporedRada;
import proj.beans.domain.RasporedRadaMessage;
import proj.beans.domain.Restoran;
import proj.beans.domain.StringMessage;
import proj.beans.domain.User;
import proj.beans.domain.UserType;
import proj.beans.service.PonudaPonudjacaService;
import proj.beans.service.PonudaService;
import proj.beans.service.PosetaService;
import proj.beans.service.RasporedRadaService;
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
	@Autowired
	private RasporedRadaService rasporedRadaService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//TODO preseliti kod iz userControllera ovde.
	@RequestMapping(
			value = "/api/newRestoran",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String createNewMenadzerRestorana(
			@RequestBody NewRestoranMessage restoranMessage,HttpSession session) throws Exception {
		logger.info("> createRestoran");
		String id=(String)session.getAttribute("user");
		User us=null;
		if(id!=null)
			us= userService.findOne(id);
		if(us!=null && us.getType().equals(UserType.MenadzerSistema)){
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
		}else return "failed";
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
			if(restorani!=null)
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
			value = "/api/oceneKonobara/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<OceneKonobaraMessage>> oceneKonobara(@PathVariable("id") String id) {
		logger.info("> getRestoran");
		ArrayList<OceneKonobaraMessage> out= new ArrayList<OceneKonobaraMessage>();
		
		Restoran restorani= restoranService.findOne(id);
		Collection<Poseta> posete= posetaService.findAll();
		for(String idKonobara:restorani.getRadnici()){
			User konobar=userService.findOne(idKonobara);
			double sumaOcena=0;
			double sumaZarade=0;
			double ocenaCount=0;
			if(konobar.getType().equals(UserType.Konobar))
			for(Poseta p:posete){
				if(p.getKonobar().equals(idKonobara)){
					sumaOcena+=p.getOcena().getOcenaUsluge();
					ocenaCount++;
					for(String jeloId:p.getNarucenaJela()){
						for(Jelo j:restorani.getJelovnik()){
							if(j.getId().equals(jeloId)){
								sumaZarade+=j.getCena();
								break;
							}
						}
					};
					for(String jeloId:p.getNarucenaPica()){
						for(Pice j:restorani.getKartaPica()){
							if(j.getId().equals(jeloId)){
								sumaZarade+=j.getCena();
								break;
							}
						}
					};
				}
			}
			OceneKonobaraMessage o= new OceneKonobaraMessage();
			o.setIme(konobar.getIme());
			o.setPrezime(konobar.getPrezime());
			if(ocenaCount!=0)
			o.setOcena(Double.toString(sumaOcena/ocenaCount));
			else
				o.setOcena("0");
			o.setPrihod(Double.toString(sumaZarade));
			out.add(o);
		}
		
		
		logger.info("< getRestoran");
		return new ResponseEntity<ArrayList<OceneKonobaraMessage>>(out,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/RestoranNaziv/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateRestoranNaziv(@RequestBody StringMessage noviNaziv,@PathVariable("id") String id,HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		restoran.setNaziv(noviNaziv.getString());
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		}return "failed";
	}
	@RequestMapping(
			value = "/api/Rasporedi/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<RasporedRada> Rasporedi(@PathVariable("id") String id,HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		ArrayList<RasporedRada> rr= new ArrayList<RasporedRada>();
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		Collection<RasporedRada> sve= rasporedRadaService.findAll();
		for(RasporedRada r:sve){
			if(r.getRadnik()!=null)
			for(String us:restoran.getRadnici()){
				if(r.getRadnik().equals(us)){
					rr.add(r);
					break;
				}
			}
			
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		
		}return rr;
	}

	@RequestMapping(
			value = "/api/PosecenostRestorana/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Integer> PosecenostRestorana(@PathVariable("id") String id,HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		ArrayList<Integer> rr= new ArrayList<Integer>();
		for(int i=0;i<7;i++)
			rr.add(0);
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		Collection<Poseta> sve= posetaService.findAll();
		for(Poseta p:sve){
			int index=p.getDatum().getDay();
			System.out.println(p.getDatum().toString());
			int val=rr.get(index);
			rr.set(index, val+1);
		}
		
		logger.info("< updateRestoran id:{}", restoran.getId());
		
		}return rr;
	}
	@RequestMapping(
			value = "/api/definisiRaspored/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String definisiRaspored(@RequestBody RasporedRadaMessage raspored,@PathVariable("id") String id,HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
			RasporedRada rr= new RasporedRada();
			rr.setDatum(raspored.getDatum());
			rr.setDoVreme(raspored.getDoTime());
			rr.setOdVreme(raspored.getOdTime());
			rr.setRadnik(raspored.getSelekt());
			rr.setReoni(raspored.getReon());
		
			RasporedRada updateRestoran=rasporedRadaService.update(rr);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		}return "failed";
	}	
	private User prijavljen(String id){
		if(id!=null)
			return userService.findOne(id);
		else return null;
	}
	@RequestMapping(
			value = "/api/prihodiRestorana/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public StringMessage prihodiRestorana(@RequestBody PonudaMessage mess,@PathVariable("id") String id,HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		StringMessage out= new StringMessage();
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
		
		System.out.println(mess.getDoDatuma());
		System.out.println(mess.getOdDatuma());
		out.setString("0");
		double sum=0;
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		Date od= df.parse(mess.getOdDatuma());
		Date Do= df.parse(mess.getDoDatuma());
		Collection<Poseta> posete=posetaService.findAll();
		for(Poseta pos:posete){
			if(pos.getDatum().after(od)&& pos.getDatum().before(Do)){
				for(String jeloId:pos.getNarucenaJela()){
					for(Jelo j:restoran.getJelovnik()){
						if(j.getId().equals(jeloId)){
							sum+=j.getCena();
							break;
						}
					}
				};
				for(String jeloId:pos.getNarucenaPica()){
					for(Pice j:restoran.getKartaPica()){
						if(j.getId().equals(jeloId)){
							sum+=j.getCena();
							break;
						}
					}
				};
			};
		}
		out.setString(Double.toString(sum));
		logger.info("< updateRestoran id:{}", restoran.getId());
		}return out;
	}
	@RequestMapping(
			value = "/api/menadzerOvogRestorana/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public StringMessage menadzerOvogRestorana(@PathVariable("id") String id, HttpSession session) throws Exception {
		String idUsera=(String)session.getAttribute("user");
		User user=prijavljen(idUsera);
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> menadzerOvogRestorana id:{}", restoran.getId());
		StringMessage out= new StringMessage();
		if (user==null) {
			out.setString("error");
			return out;
		}
		logger.info("< menadzerOvogRestorana id:{}", restoran.getId());
		if(restoran.getMenadzer().equals(idUsera))			
		 out.setString("true");
		else out.setString("false");
		return out;
	}
	@RequestMapping(
			value = "/api/zaposleniRestorana/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<User> zaposleniRestorana(@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviNaziv);
		logger.info("> zaposleniurestoranu id:{}", restoran.getId());
		ArrayList<User> out=new ArrayList<User>();
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		for(String s:restoran.getRadnici()){
			out.add(userService.findOne(s));
		}
		
		logger.info("< zaposleniurestoranu id:{}", restoran.getId());
		return out;
		}return out;
	}@RequestMapping(
			value = "/api/vrstaZaposlenog/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public StringMessage vrstaZaposlenog(@PathVariable("id") String id, HttpSession session) throws Exception {
		//System.out.println(noviNaziv);
		StringMessage out=new StringMessage();
		User zaposleni=userService.findOne(id);
		if(zaposleni!=null&& zaposleni.getType().equals(UserType.Konobar))
			out.setString("konobar");
			return out;
	}
	@RequestMapping(
			value = "/api/RestoranOpis/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String updateRestoranOpis(@RequestBody StringMessage noviOpis,@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		restoran.setVrsta(noviOpis.getString());
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		}return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranDodajJelo/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranDodajJelo(@RequestBody Jelo novoJelo,@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		//novoJelo.setId();
		novoJelo.setId(Long.toString((new Random().nextLong()/1000)));
		restoran.getJelovnik().add(novoJelo);
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		}return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranObrisiJelo/{id}/{id2}",
			method = RequestMethod.DELETE)
	public String deleteJelo(
			@PathVariable("id") String id,@PathVariable("id2") String idJela, HttpSession session) throws Exception {
		logger.info("> deleteJelo id:{}", idJela);
		//System.out.println(idJela);
		Restoran restoran=restoranService.findOne(id);
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
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
		}return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranDodajPice/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranDodajPice(@RequestBody Pice novoJelo,@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		novoJelo.setId(Long.toString((new Random().nextLong()/1000)));
		restoran.getKartaPica().add(novoJelo);
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		} return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranPostaviRaspored/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranPostaviRaspored(@RequestBody Raspored raspored,@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		//System.out.println(noviOpis);
		logger.info("> updateRestoran id:{}", restoran.getId());
		//novoJelo.setId();
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
		
		if(restoran.getRaspored()==null)	
			restoran.setRaspored(raspored);
		Restoran updateRestoran=restoranService.update(restoran);
		if (updateRestoran== null) {
			return "error";
		}
		logger.info("< updateRestoran id:{}", restoran.getId());
		return "success";
		} return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranObrisiPice/{id}/{id2}",
			method = RequestMethod.DELETE)
	public String deletePice(
			@PathVariable("id") String id,@PathVariable("id2") String idJela, HttpSession session) throws Exception {
		logger.info("> deletePice id:{}", idJela);
		//System.out.println(idJela);
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		Restoran restoran=restoranService.findOne(id);
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
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
		return "failed";
	}
	@RequestMapping(
			value = "/api/RestoranRadnikRegister/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String RestoranRadnikRegister(@RequestBody NewRadnikMessage message,@PathVariable("id") String id, HttpSession session) throws Exception {
		Restoran restoran=restoranService.findOne(id);
		String idMenadzera=(String) session.getAttribute("user");
		User menadzer=null;
		if(idMenadzera!=null)
			menadzer=userService.findOne(idMenadzera);
			
		
		if(menadzer!=null && restoran!=null && restoran.getMenadzer().equals(menadzer.getId())){
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
		}return "failed"; 
	}
}
