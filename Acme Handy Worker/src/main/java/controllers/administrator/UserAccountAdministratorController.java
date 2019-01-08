package controllers.administrator;

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

import controllers.AbstractController;

@Controller
@RequestMapping("/userAccount/admin")
public class UserAccountAdministratorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	// Constructors ------------------------------------------------------------

	public UserAccountAdministratorController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/createAdmin", method = RequestMethod.GET)
	public ModelAndView createAdmin() {

		ModelAndView result;
		// UserAccount administrator = userAccountService.create();

		// Authority authority = new Authority();
		// authority.setAuthority("ADMIN");

		// administrator.getAuthorities().add(authority);

		result = this.createEditModelAndView();

		return result;
	}

	// @RequestMapping(value = "/createReferee", method = RequestMethod.GET)
	// public ModelAndView createReferee() {
	//
	// ModelAndView result;
	// UserAccount referee = userAccountService.create();
	//
	// Authority authority = new Authority();
	// authority.setAuthority("REFEREE");
	//
	// referee.getAuthorities().add(authority);
	//
	// result = this.createEditModelAndView(referee);
	//
	// return result;
	// }

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserAccount userAccount,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView();
		} else
			try {
				userAccountService.save(userAccount);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView("admin.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView() {
		ModelAndView result;

		result = this.createEditModelAndView(null);

		return result;
	}

	private ModelAndView createEditModelAndView(final String message) {

		ModelAndView result;

		result = new ModelAndView("userAccount/createAdmin");

		UserAccount administrator = userAccountService.create();

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		administrator.getAuthorities().add(authority);

		UserAccount referee = userAccountService.create();

		Authority authority2 = new Authority();
		authority2.setAuthority("REFEREE");

		referee.getAuthorities().add(authority2);

		result.addObject("userAccountAdmin", administrator);
		result.addObject("userAccountReferee", referee);
		result.addObject("message", message);

		return result;
	}
}
