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

import controllers.AbstractController;
import domain.FixUpTask;
import services.FixUpTaskService;

@Controller
@RequestMapping("/customer/fixuptask")
public class FixUpTaskCustomerController extends AbstractController{

public FixUpTaskCustomerController(){
	super();
}

	@Autowired
	FixUpTaskService fixUpTaskService;

	//Listing---
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView res;
			Collection<FixUpTask> fixUpTasks;
			fixUpTasks = fixUpTaskService.getFixUpTasksCustomer();
			
			res = new ModelAndView("fixUpTask/list");
			res.addObject("fixUpTasks", fixUpTasks);
			res.addObject("requestURI", "fixUpTask/explorer/list.do");
			return res; 
		}

		// Create -----------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			FixUpTask fixUpTask;
			fixUpTask = this.fixUpTaskService.create();
			

			result = this.createEditModelAndView(fixUpTask);

			return result;
		}

		// Edit -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int fixUpTaskId) {
			ModelAndView result;
			FixUpTask fixUpTask;

			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			result = this.createEditModelAndView(fixUpTask);

			return result;
		}
		
	//Delete----------------------------------------------------------------------------------------------------------------------------------------
		
		@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(FixUpTask fixUpTask, BindingResult binding){
			
			ModelAndView res;
			
			try{
				this.fixUpTaskService.delete(fixUpTask);
				res= new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(fixUpTask,"task.commit.error");
			}
			return res;
		}
		
	//Save---------------------------------------------------------------	
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid FixUpTask fixUpTask, BindingResult binding){
			
			ModelAndView res;
			
			if(binding.hasErrors()){
				System.out.println("Fallos en: \n" + binding.getAllErrors());
				res = this.createEditModelAndView(fixUpTask);
			}else{
				try {
					this.fixUpTaskService.save(fixUpTask);
					res = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					System.out.println(oops.getCause());
					res = this.createEditModelAndView(fixUpTask, "task.commit.error");
				}
			}
			return res;
		}
		
	//Ancillary Methods---------
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask){
			ModelAndView res;
			res= this.createEditModelAndView(fixUpTask,null);
			return res;
		}
		
		protected ModelAndView createEditModelAndView(FixUpTask fixUpTask, String messageCode){
			ModelAndView res;

			res= new ModelAndView("fixUpTask/list");
			res.addObject("fixUpTask", fixUpTask);
			res.addObject("message", messageCode);
			
			return res;
		}

}
