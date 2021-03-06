/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.CustomerService;
import services.MessageService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("customer/application/")
public class CustomerApplicationController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ApplicationService appService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MessageService messageService;
	
	private int appId;
	
	// Constructors -----------------------------------------------------------

	public CustomerApplicationController() {
		super();
	}

	// Listing ----------------------------------------------------------------		

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Application> applications;
		
		int id = customerService.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		
		applications = appService.applicationByCustomer(id);
		
		for(Application a:applications){
			if(a.getCreditCard()==null && (a.getStatus().equals("ACCEPTED") || a.getStatus().equals("REJECTED"))){
				a.setStatus("PENDING");
				appService.save(a);
			}
		}
		
		result = new ModelAndView("application/list");
		result.addObject("applications",applications);
		result.addObject("requestURI","customer/application/list.do");

		return result;
	}
	
	// Edit ------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int appId) {
		ModelAndView result;
		Application app;
		
		app = appService.findOne(appId);
		Customer logged = customerService.findByUserAccountId(LoginService.getPrincipal().getId());

		if(app.getFixUpTask().getCustomer().equals(logged)){
			result = createEditModelAndView(app);
		}else{
			result = new ModelAndView("error/access");
		}
		
		return result;
	}
	
	// Save ------------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Application app, BindingResult binding) {
		ModelAndView result;
		Application saved;
		if(binding.hasErrors()){
			result = createEditModelAndView(app);
		}else{
			try{
				saved = appService.save(app);

				if(saved.getStatus().equals("ACCEPTED")){
//					FixUpTask fx = saved.getFixUpTask();
//					for(Application a: fx.getApplications()){
//						if(!a.equals(saved) ){
//							a.setStatus("REJECTED");
//							appService.save(a);
//						}
//					}
					result = new ModelAndView("redirect:/creditCard/create.do?appId=" + app.getId());
				}else{
					result = new ModelAndView("redirect:/customer/application/edit.do?appId=" + app.getId());
				}
				
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="saveAll")
	public ModelAndView saveAll(@Valid Application app, BindingResult binding) {
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(app);
		}else{
			try{
				appService.save(app);
				result = new ModelAndView("redirect:/customer/application/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		}
		
		return result;
	}
	
	// Cancel -----------------------------------------------------------------
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="cancel")
	public ModelAndView cancel(@Valid Application app) {
		ModelAndView result;
		app.setStatus("PENDING");
		
			try{
				appService.save(app);
				result = new ModelAndView("redirect:/customer/application/list.do");
			}catch(Throwable oops){
				result = createEditModelAndView(app,"application.commit.error");
			}
		
		return result;
	}
	
//	// Createcc ------------------------------------------------------------------------------------------	
//
//		@RequestMapping(value="/creditCard/create", method=RequestMethod.GET)
//		public ModelAndView create(int appId) {
//			ModelAndView result;
//			CreditCard cc = new CreditCard();
//			this.appId = appId;
//			
//			result = createEditModelAndView(cc);
//
//			return result;
//		}
//		
//		// Savecc --------------------------------------------------------------------------------------------
//		
//		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
//		public ModelAndView save(@Valid CreditCard cc, BindingResult binding) {
//			ModelAndView result;
//			Application app = appService.findOne(this.appId), saved;
//			app.setCreditCard(cc);
//			
//			if(binding.hasErrors()){
//				result = createEditModelAndView(cc);
//			}else{
//				try{
//					saved = appService.save(app);
//					messageService.sendSystemMessages(saved);
//					result = new ModelAndView("redirect:/customer/application/list.do");
//				}catch(Throwable oops){
//					result = createEditModelAndView(cc,"creditCard.commit.error");
//				}
//			}
//			
//			return result;
//		}
//		
//		// Cancelcc -------------------------------------------------------------------------------------------
//		
//		@RequestMapping(value="/edit", method=RequestMethod.POST, params="cancel")
//		public ModelAndView cancel() {
//			ModelAndView result;
//			Application app = appService.findOne(this.appId);
//			app.setStatus("PENDING");
//			
//			appService.save(app);
//			result = new ModelAndView("redirect:/customer/application/list.do");
//			
//			return result;
//		}
//
//		//Ancillary Methodscc ---------------------------------------------------------------------------------
//			
//			protected ModelAndView createEditModelAndView(CreditCard cc){
//				ModelAndView res;
//				res= this.createEditModelAndView(cc,null);
//				return res;
//			}
//			
//			protected ModelAndView createEditModelAndView(CreditCard cc, String messageCode){
//				ModelAndView res;
//				
//				res= new ModelAndView("creditCard/create");
//				res.addObject("creditCard",cc);
//				res.addObject("message", messageCode);	
//				
//				return res;
//			}	
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Application application){
		ModelAndView result;
		
		result = createEditModelAndView(application,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Application app, String msgCode){
		ModelAndView result;
		CreditCard cc;
		cc = new CreditCard();
		System.out.println(app.getStatus());
		if(app.getStatus().equals("REJECTED")){
			
			result = new ModelAndView("application/comment");
			
			result.addObject("application",app);
			result.addObject("menssage",msgCode);
		}else{
			result = new ModelAndView("application/edit");
		
			result.addObject("application",app);
			result.addObject("creditCard",cc);
			result.addObject("menssage",msgCode);
		}
		return result;
	}
	
}
