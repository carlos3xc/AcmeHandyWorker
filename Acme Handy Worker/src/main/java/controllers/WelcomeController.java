/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	//Services
	@Autowired
	ActorService actorService;
	
	@Autowired
	ConfigurationService configurationService;
	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String name = "Anonymous";

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		if(LoginService.hasRole("CUSTOMER")||LoginService.hasRole("HANDYWORKER")||
				LoginService.hasRole("REFEREE")||LoginService.hasRole("SPONSOR")||
				LoginService.hasRole("ADMIN")){
		name = actorService.getByUserAccountId(LoginService.getPrincipal()).getName();
		}
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("welcomeMessageSpanish",c.getWelcomeTextSpanish());
		result.addObject("welcomeMessageEnglish",c.getWelcomeTextEnglish());
		result.addObject("systemName", c.getSystemName());
		result.addObject("moment", moment);

		return result;
	}
}
