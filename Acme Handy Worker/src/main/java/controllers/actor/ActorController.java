package controllers.actor;

import java.util.ArrayList;
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

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Referee;
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
	private RefereeService refereeService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ApplicationService applicationService;

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
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		Authority authority2 = new Authority();
		authority2.setAuthority("HANDYWORKER");

		Authority authority3 = new Authority();
		authority3.setAuthority("SPONSOR");

		Authority authority4 = new Authority();
		authority4.setAuthority("REFEREE");

		Authority authority5 = new Authority();
		authority5.setAuthority("ADMIN");
		
		Boolean custProfileHw = false;

		result = new ModelAndView("actor/show");
		if (actorId != null) {
			Actor actor = actorService.findOne(actorId);
			Boolean logged = false;
			if(LoginService.getPrincipal().equals(actor.getUserAccount())) logged=true;
			result.addObject("actor", actor);
			result.addObject("logged",logged);
			if(LoginService.getPrincipal().getAuthorities().contains(authority2) &&
					actor.getUserAccount().getAuthorities().contains(authority)){ //Si el logeado es hw y vemos el perfil de customer
				custProfileHw = true;
				Customer customer = customerService.findOne(actor.getId());
				result.addObject("fixUpTasks", customer.getFixUpTasks());
				result.addObject("custProfileHw",custProfileHw);
			}
			
		}else{
			Actor actor = actorService.getByUserAccountId(LoginService.getPrincipal());
			Collection<SocialProfile> socialProfiles = actor.getSocialProfiles();
			Boolean logged = true;
			result.addObject("logged",logged);

		if (actor.getUserAccount().getAuthorities().contains(authority)) {
			custProfileHw=true;
			Customer customer = (Customer) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			customer = customerService.findOne(actor.getId());

			Collection<FixUpTask> fixUpTasks = customer.getFixUpTasks();

			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("custProfileHw",custProfileHw);
			result.addObject("actor", customer);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority2)) {
			HandyWorker handyWorker = (HandyWorker) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			handyWorker = handyWorkerService.findOne(actor.getId());

			int handyWorkerId = handyWorker.getId();

			Collection<Application> applications = applicationService
					.applicationByHandyWorker(handyWorkerId);

			result.addObject("applications", applications);
			result.addObject("actor", handyWorker);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority3)) {
			Sponsor sponsor = (Sponsor) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			sponsor = sponsorService.findOne(actor.getId());

			result.addObject("actor", sponsor);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority4)) {
			Referee referee = (Referee) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			referee = refereeService.findOne(actor.getId());

			result.addObject("actor", referee);
		}

		if (actor.getUserAccount().getAuthorities().contains(authority5)) {
			Administrator administrator = (Administrator) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			administrator = administratorService.findOne(actor.getId());

			result.addObject("actor", administrator);
		}
		result.addObject("socialProfiles", socialProfiles);
		}
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result = new ModelAndView();

		Actor actor = actorService.getByUserAccountId(LoginService
				.getPrincipal());

		result = createEditModelAndView(actor);

		return result;
	}
	
	// Save -----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Actor actor,
				final BindingResult binding) {

			ModelAndView result;
			Actor actorDB = actorService.findOne(actor.getId());

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
					if(actorDB.getUserAccount().getUsername() != actor.getUserAccount().getUsername() ||
							actorDB.getUserAccount().getPassword() != actorDB.getUserAccount().getPassword())
					result = new ModelAndView("j_spring_security_logout");
					else
						result = new ModelAndView("redirect:show.do");

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(actor,
							"actor.commit.error");
				}
			return result;
		}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdministrator")
	public ModelAndView save(@Valid final Administrator administrator,
			final BindingResult binding) {

		ModelAndView result;

		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// String pass = encoder.encodePassword(administrator.getUserAccount()
		// .getPassword(), null);
		// administrator.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(administrator);
		} else
			try {
				administratorService.save(administrator);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHandyWorker")
	public ModelAndView save(@Valid final HandyWorker handyWorker,
			final BindingResult binding) {

		ModelAndView result;

		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// String pass = encoder.encodePassword(handyWorker.getUserAccount()
		// .getPassword(), null);
		// handyWorker.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(handyWorker);
		} else
			try {
				handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCustomer")
	public ModelAndView save(@Valid final Customer customer,
			final BindingResult binding) {

		ModelAndView result;

		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// String pass = encoder.encodePassword(customer.getUserAccount()
		// .getPassword(), null);
		// customer.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(customer);
		} else
			try {
				customerService.save(customer);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveReferee")
	public ModelAndView save(@Valid final Referee referee,
			final BindingResult binding) {

		ModelAndView result;

		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// String pass = encoder.encodePassword(referee.getUserAccount()
		// .getPassword(), null);
		// referee.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(referee);
		} else
			try {
				refereeService.save(referee);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(referee,
						"actor.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView save(@Valid final Sponsor sponsor,
			final BindingResult binding) {

		ModelAndView result;

		// Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// String pass = encoder.encodePassword(sponsor.getUserAccount()
		// .getPassword(), null);
		// sponsor.getUserAccount().setPassword(pass);

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(sponsor);
		} else
			try {
				sponsorService.save(sponsor);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsor,
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

		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		Authority authority2 = new Authority();
		authority2.setAuthority("HANDYWORKER");
		
		Authority authority3 = new Authority();
		authority3.setAuthority("SPONSOR");

		Authority authority4 = new Authority();
		authority4.setAuthority("REFEREE");

		Authority authority5 = new Authority();
		authority5.setAuthority("ADMIN");

		result = new ModelAndView("actor/edit");

		if (actor.getUserAccount().getAuthorities().equals(authority)) {
			Customer customer = (Customer) actorService
					.getByUserAccountId(LoginService.getPrincipal());
			Collection<FixUpTask> fixUpTasks = customer.getFixUpTasks();

			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("actor", customer);
		}

		if (actor.getUserAccount().getAuthorities().equals(authority2)) {
			HandyWorker handyWorker = (HandyWorker) actorService
					.getByUserAccountId(LoginService.getPrincipal());

			Collection<Application> applications = new ArrayList<Application>();
			for (Application app : applications) {
				if (app.getHandyWorker().equals(handyWorker)) {
					applications.add(app);
					result.addObject("applications", applications);
				}
			}
		}

		result.addObject("actor", actor);
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("userAccount", userAccount);
		result.addObject("isSuspicious", isSuspicious);
		result.addObject("isBanned", isBanned);

		result.addObject("message", message);

		return result;
	}

}
