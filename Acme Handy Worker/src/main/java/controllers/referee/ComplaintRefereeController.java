package controllers.referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ComplaintService;
import services.RefereeService;

import controllers.AbstractController;
import domain.Complaint;
import domain.Referee;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private RefereeService refereeService;

	// Constructors ------------------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	// Listing B-RF 36.1 -------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView listComplaintsWithNoReports() {
//
//		ModelAndView result;
//		
//		Referee referee = refereeService.findByUserAccountId(LoginService
//				.getPrincipal().getId());
//
//		Collection<Complaint> complaints = complaintService
//				.getComplaintsWithNoReports();
//
//		result = new ModelAndView("complaint/list");
//		result.addObject("complaints", complaints);
//		result.addObject("referee", referee);
//		result.addObject("requestURI", "complaint/referee/list.do");
//
//		return result;
//	}

	// Listing B-RF 36.2 -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listComplaintsReferee() {

		ModelAndView result;

		Referee referee = refereeService.findByUserAccountId(LoginService
				.getPrincipal().getId());
		int refereeId = referee.getId();

		Collection<Complaint> complaints = complaintService
				.getComplaintsReferee(refereeId);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("referee", referee);
		result.addObject("requestURI", "complaint/referee/list.do");

		return result;
	}
}
