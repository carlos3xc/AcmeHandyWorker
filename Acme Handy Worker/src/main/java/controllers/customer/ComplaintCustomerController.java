package controllers.customer;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Complaint;
import domain.SocialProfile;

import services.ComplaintService;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	// Constructors ------------------------------------------------------------

	public ComplaintCustomerController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Complaint> complaints;

		complaints = complaintService.findAll();

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("requestURI", "complaint/customer/list.do");

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {

		ModelAndView result;

		Complaint complaint = complaintService.findOne(complaintId);

		result = new ModelAndView("complaint/show");
		result.addObject("requestURI", "complaint/customer/show.do");
		result.addObject("complaint", complaint);

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.create();

		result = this.createEditModelAndView(complaint);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId) {

		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.findOne(complaintId);
		result = createEditModelAndView(complaint);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(complaint);
		} else
			try {
				complaintService.save(complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint,
						"complaint.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.complaintService.delete(complaint);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(complaint,
					"complaint.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Complaint complaint,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("complaint/edit");

		result.addObject("complaint", complaint);
		result.addObject("message", message);

		return result;
	}

}
