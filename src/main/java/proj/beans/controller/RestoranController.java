package proj.beans.controller;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import proj.beans.domain.NewRestoranMessage;
import proj.beans.domain.User;
import proj.beans.domain.UserType;
import proj.beans.service.UserService;

@RestController

public class RestoranController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//TODO preseliti kod iz userControllera ovde.
	
}
