package controllers.administrator;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Box;
import domain.Configuration;
import domain.Word;

@Controller
@RequestMapping("/administrator/admin")
public class AdministratorConfigurationAndDashboardController extends AbstractController {
	
	//Services
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public AdministratorConfigurationAndDashboardController() {
		super();
	}
	
	//Edit-------------------------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		//como solo debe existir una se puede hacer esto.
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		
		res = this.createEditModelAndView(c);
		return res;
	}
	
	//Save-------------------------------------------------------------	
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Configuration config, BindingResult binding){
		ModelAndView res;
		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				configurationService.save(config);
				res = new ModelAndView("redirect:edit.do");
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
				//TODO: añadir el commit error al admin.properties de message.
			}
		}
		return res;
	}
	
	//Add and remove methods ------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="addSpam")
	public ModelAndView addSpam(@Valid Word word, BindingResult binding){
		ModelAndView res;
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				List<Word> spams = config.getspamWords();
				spams.add(word);
				config.setSpamWords(spams);
				configurationService.save(config);
				res = new ModelAndView("redirect:edit.do");
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
				//TODO: añadir el commit error al admin.properties de message.
			}
		}
		return res;
	}
	
	//TODO: removeSpamWord, addCreditCard
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	protected ModelAndView createEditModelAndView(Configuration config, String messageCode){
		ModelAndView res;
		//TODO:add word and creditcardmake to the view.
		
		res = new ModelAndView("administrator/configuration");
		res.addObject("configuration", config);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}