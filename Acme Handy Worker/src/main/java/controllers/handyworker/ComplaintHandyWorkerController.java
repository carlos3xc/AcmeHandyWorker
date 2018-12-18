package controllers.handyworker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ComplaintService;

import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	// Constructors ------------------------------------------------------------

	public ComplaintHandyWorkerController() {
		super();
	}

	// Listing B-RF 37.3 -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		int handyWorkerId = LoginService.getPrincipal().getId();

		Collection<Complaint> complaints = complaintService
				.getComplaintsHandyWorker(handyWorkerId);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/handyWorker/list.do");

		return result;
	}

}
