package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		UserAccount administrator = userAccountService.create();

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		administrator.getAuthorities().add(authority);

		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/createReferee", method = RequestMethod.GET)
	public ModelAndView createReferee() {

		ModelAndView result;
		UserAccount referee = userAccountService.create();

		Authority authority = new Authority();
		authority.setAuthority("REFEREE");

		referee.getAuthorities().add(authority);

		result = this.createEditModelAndView(referee);

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int userAccountId) {

		ModelAndView result;

		UserAccount userAccount = userAccountService.findOne(userAccountId);

		result = createEditModelAndView(userAccount);

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserAccount userAccount,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(userAccount);
		} else
			try {
				userAccountService.save(userAccount);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(userAccount,
						"admin.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(UserAccount userAccount) {
		ModelAndView result;

		result = this.createEditModelAndView(userAccount, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final UserAccount userAccount,
			final String message) {

		ModelAndView result;

		result = new ModelAndView("userAccount/edit");

		result.addObject("userAccount", userAccount);
		result.addObject("message", message);

		return result;
	}
}
