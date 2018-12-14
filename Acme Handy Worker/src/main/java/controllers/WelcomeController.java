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

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	//Services
	@Autowired
	ActorService actorService;
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

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);

		return result;
	}
}
