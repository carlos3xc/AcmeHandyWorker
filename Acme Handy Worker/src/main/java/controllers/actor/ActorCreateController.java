package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.CreditCardMakeService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller 
@RequestMapping("actor/")
public class ActorCreateController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors ------------------------------------------------------------

	public ActorCreateController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/createCustomer", method = RequestMethod.GET)
	public ModelAndView createCustomer() {

		ModelAndView result;

		result = this.createEditModelAndView("CUSTOMER");

		return result;
	}
	
	@RequestMapping(value = "/createHandyWorker", method = RequestMethod.GET)
	public ModelAndView createHandyWorker() {

		ModelAndView result;

		result = this.createEditModelAndView("HANDYWORKER");

		return result;
	}
	
	@RequestMapping(value = "/createSponsor", method = RequestMethod.GET)
	public ModelAndView createSponsor() {

		ModelAndView result;

		result = this.createEditModelAndView("SPONSOR");

		return result;
	}

	// SAVES-------------------------------------------------------
	// CUSTOMER
	@RequestMapping(value = "/createCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid final Customer customer , final BindingResult binding) {
		ModelAndView result;		

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView("CUSTOMER");
		} else
			try {
				UserAccount savedUA = userAccountService.save(customer.getUserAccount());
				customer.setUserAccount(savedUA);
				customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");
				
			} catch (final Throwable oops) {
				result = this.createEditModelAndView("actor.commit.error");
				System.out.println("EXCEPCION CAPTURADA!!!!!!: ");
				for (int i = 0; i < oops.getStackTrace().length; i++) {
					System.out.println(oops.getStackTrace()[i]);
				}
			}
		return result;
	}
	
	// SPONSOR
		@RequestMapping(value = "/createSponsor", method = RequestMethod.POST, params = "save")
		public ModelAndView saveSponsor(@Valid final Sponsor sponsor , final BindingResult binding) {
			ModelAndView result;
			
			System.out.println("saving the customer account: "+sponsor.getUserAccount().getUsername()+"  "+sponsor.getUserAccount().getPassword());
			

			if (binding.hasErrors()) {
				System.out.println(binding.getFieldErrors());
				result = this.createEditModelAndView("SPONSOR");
			} else
				try {
					UserAccount savedUA = userAccountService.save(sponsor.getUserAccount());
					sponsor.setUserAccount(savedUA);
					sponsorService.save(sponsor);
					result = new ModelAndView("redirect:/security/login.do");
					
				} catch (final Throwable oops) {
					result = this.createEditModelAndView("actor.commit.error");
					System.out.println("EXCEPCION CAPTURADA!!!!!!: ");
					for (int i = 0; i < oops.getStackTrace().length; i++) {
						System.out.println(oops.getStackTrace()[i]);
					}
				}
			return result;
		}
		
		// HANDYWORKER
		@RequestMapping(value = "/createHandyWorker", method = RequestMethod.POST, params = "save")
		public ModelAndView saveHandyWorker(@Valid final HandyWorker handyworker , final BindingResult binding) {
			ModelAndView result;
			
			System.out.println("saving the customer account: "+handyworker.getUserAccount().getUsername()+"  "+handyworker.getUserAccount().getPassword());
			

			if (binding.hasErrors()) {
				System.out.println(binding.getFieldErrors());
				result = this.createEditModelAndView("HANDYWORKER");
			} else
				try {
					UserAccount savedUA = userAccountService.save(handyworker.getUserAccount());
					handyworker.setUserAccount(savedUA);
					handyWorkerService.save(handyworker);
					result = new ModelAndView("redirect:/security/login.do");
					
				} catch (final Throwable oops) {
					result = this.createEditModelAndView("actor.commit.error");
					System.out.println("EXCEPCION CAPTURADA!!!!!!: ");
					for (int i = 0; i < oops.getStackTrace().length; i++) {
						System.out.println(oops.getStackTrace()[i]);
					}
				}
			return result;
		}
	

	protected ModelAndView createEditModelAndView(String type) {
		ModelAndView result;

		result = this.createEditModelAndView(type, null);

		return result;
	}

	private ModelAndView createEditModelAndView(String type, final String message) {

		ModelAndView result = new ModelAndView("redirect: index.do");
		

		if(type == "CUSTOMER"){
			result = new ModelAndView("actor/createCustomer");
			
			Customer customer = customerService.create();
			result.addObject("customer", customer);
		}
		
		if(type == "SPONSOR"){
			result = new ModelAndView("actor/createSponsor");
			
			Sponsor sponsor = sponsorService.create();
			result.addObject("sponsor", sponsor);
		}
		
		if(type == "HANDYWORKER"){
			result = new ModelAndView("actor/createHandyWorker");
			
			HandyWorker handy = handyWorkerService.create();
			result.addObject("handyworker", handy);
			//no te olvides del make aqui 
		}
		
		result.addObject("message", message);

		return result;
	}

}
