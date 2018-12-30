package controllers.handyworker;

import java.util.ArrayList;
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
import services.HandyWorkerService;
import services.TutorialService;

import controllers.AbstractController;
import domain.HandyWorker;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyWorker")
public class TutorialHandyWorkerController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Constructors ------------------------------------------------------------

	public TutorialHandyWorkerController() {
		super();
	}

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		Collection<Tutorial> tutorials = tutorialService.findAll();
		Collection<Tutorial> tutorial = new ArrayList<Tutorial>();

		HandyWorker handyWorker = handyWorkerService
				.findByUserAccountId(LoginService.getPrincipal().getId());

		for (Tutorial t : tutorials) {
			if (t.getHandyWorker().equals(handyWorker)) {
				tutorial.add(t);
			}
		}

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/handyWorker/list.do");

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {

		ModelAndView result;

		Tutorial tutorial = tutorialService.findOne(tutorialId);

		result = new ModelAndView("tutorial/show");
		result.addObject("tutorial", tutorial);
		result.addObject("requestURI", "tutorial/handyWorker/show.do");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		Tutorial tutorial = tutorialService.create();

		result = this.createEditModelAndView(tutorial);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {

		ModelAndView result;

		Tutorial tutorial = tutorialService.findOne(tutorialId);

		result = createEditModelAndView(tutorial);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(tutorial);
		} else
			try {
				tutorialService.save(tutorial);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial,
						"tutorial.commit.error");
			}
		return result;
	}

	// Delete -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tutorial tutorial,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial,
					"tutorial.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Tutorial tutorial,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("tutorial/edit");

		result.addObject("tutorial", tutorial);
		result.addObject("message", message);

		return result;
	}
}
