package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("fixUpTask/")
public class FixUpTaskController extends AbstractController{

public FixUpTaskController(){
	super();
}

	@Autowired
	FixUpTaskService fixUpTaskService;
	
	@Autowired
	ComplaintService complaintService;
	
	//Listing---
		@RequestMapping(value="/show", method = RequestMethod.GET)
		public ModelAndView show(@RequestParam final int fixUpTaskId){
			
			ModelAndView res;
			FixUpTask fixUpTask;
			Collection<Complaint> complaints;
			complaints = complaintService.getComplaintsFixUpTask(fixUpTaskId);
			fixUpTask = fixUpTaskService.findOne(fixUpTaskId);
			
			String fullName = fixUpTask.getCustomer().getName()+" " + fixUpTask.getCustomer().getMiddleName() + " "+ fixUpTask.getCustomer().getSurname();
			
			res = new ModelAndView("fixUpTask/show");
			res.addObject("fixUpTask", fixUpTask);
			res.addObject("complaints",complaints);
			res.addObject("fullName",fullName);
			res.addObject("requestURI", "fixUpTask/show.do");
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
			res= new ModelAndView("fixUpTask/edit");
			res.addObject("fixUpTask", fixUpTask);
			res.addObject("message", messageCode);
			
			return res;
		}

}
