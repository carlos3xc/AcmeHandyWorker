package controllers.referee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ComplaintService;

import controllers.AbstractController;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	// Constructors ------------------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	// Listing -----------------------------------------------------------------
}
