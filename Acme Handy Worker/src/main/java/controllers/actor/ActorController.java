package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;

import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services
	// -------------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;

	// Constructors
	// ---------------------------------------------------------------

	public ActorController() {
		super();
	}

	// Create handyWorker -----------------------------------------------------

	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {

		ModelAndView result;

		HandyWorker handyWorker = handyWorkerService.create();

		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	// Create customer --------------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {

		ModelAndView result;

		Customer customer = customerService.create();

		result = this.createEditModelAndView(customer);
		result.addObject("customer", customer);

		return result;
	}

	// Create sponsor ---------------------------------------------------------

	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {

		ModelAndView result;

		Sponsor sponsor = sponsorService.create();

		result = this.createEditModelAndView(sponsor);

		return result;
	}

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int actorId) {

		ModelAndView result;

		Actor actor = actorService.findOne(actorId);

		result = new ModelAndView("actor/show");
		result.addObject("actor", actor);
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {

		ModelAndView result;
		Actor actor;

		actor = actorService.findOne(actorId);
		result = createEditModelAndView(actor);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor,
			final BindingResult binding) {

		ModelAndView result;

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String pass = encoder.encodePassword(actor.getUserAccount()
				.getPassword(), null);
		actor.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(actor);
		} else
			try {
				actorService.save(actor);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor,
						"actor.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Actor actor,
			final String message) {

		ModelAndView result;

		Collection<SocialProfile> socialProfiles;
		UserAccount userAccount;
		Boolean isSuspicious;
		Boolean isBanned;

		socialProfiles = actor.getSocialProfiles();
		userAccount = actor.getUserAccount();
		isSuspicious = actor.getIsSuspicious();
		isBanned = actor.getIsBanned();

		result = new ModelAndView("actor/edit");

		result.addObject("actor", actor);
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("userAccount", userAccount);
		result.addObject("isSuspicious", isSuspicious);
		result.addObject("isBanned", isBanned);

		result.addObject("message", message);

		return result;
	}

}
