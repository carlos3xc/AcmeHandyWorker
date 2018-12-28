package controllers.handyworker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.CurriculaService;
import services.HandyWorkerService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.Actor;
import domain.Curricula;
import domain.PersonalRecord;

@Controller
@RequestMapping("curricula/handyworker")
public class CurriculaController extends AbstractController {

	// Services

	@Autowired
	CurriculaService curriculaService;

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private ActorService actorService;

	// Constructors
	// ------------------------------------------------------------------

	public CurriculaController() {
		super();
	}

	// Curricula----
	// Show----------------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Curricula c = null;

		Actor logged = actorService.getByUserAccountId(LoginService
				.getPrincipal());

		for (Curricula cu : curriculaService.findAll()) {
			if (cu.getHandyWorker().equals(logged)) {
				c = cu;
			}
		}

		res = new ModelAndView("curricula/show");
		res.addObject("curricula", c);

		return res;
	}

	// Delete
	@RequestMapping(value = "/deleteCurricula", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int curriculaId) {
		ModelAndView res;
		Curricula c = curriculaService.findOne(curriculaId);

		try {
			curriculaService.delete(c);
			res = new ModelAndView("redirect:show.do");
		} catch (Throwable e) {
			res = new ModelAndView("curricula/show");
			res.addObject("curricula", c);
			res.addObject("message", "message.commit.error");
		}
		return res;
	}

	// PERSONAL RECORD
	// ----------------------------------------------------------------

	// Create--------------------------------------------------------------------------
	@RequestMapping(value = "/editPersonalRecord", method = RequestMethod.GET)
	public ModelAndView create() {
		// its the same as creating a personal record, saving it, and assigning
		// it to the curricula later on,
		// then creating a curricula, asssigning the PR to it then saving the
		// curricula

		ModelAndView res;
		PersonalRecord pr = personalRecordService.create();

		res = this.createEditPersonalRecordModelAndView(pr);
		return res;
	}

	@RequestMapping(value = "/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView savePersonalRecord(@Valid PersonalRecord pr,
			BindingResult binding) {
		// we check if the logged in actor has a curricula, if not create one
		// and bind the PR to it (save the record first)
		ModelAndView res;
		System.out.println(binding);
		System.out.println(pr.getFullName() + " " + pr.getEmail()
				+ pr.getLinkedInUrl());

		if (binding.hasErrors()) {
			res = createEditPersonalRecordModelAndView(pr);
		} else {
			try {
				System.out
						.println("tries to save the personal record / create curricula");
				personalRecordService.save(pr);// this already checks for the
												// existance of a curricula. and
												// if it doesnt exist it does it
												// all.
				res = new ModelAndView("redirect:show.do");
			} catch (Throwable e) {
				res = createEditPersonalRecordModelAndView(pr,
						"message.commit.error");
				System.out.println(e);
			}
		}
		return res;
	}

	// PERSONAL RECORD
	// ----------------------------------------------------------------

	// Create--------------------------------------------------------------------------
	@RequestMapping(value = "/editPersonalRecord", method = RequestMethod.GET)
	public ModelAndView create() {
		// its the same as creating a personal record, saving it, and assigning
		// it to the curricula later on,
		// then creating a curricula, asssigning the PR to it then saving the
		// curricula

		ModelAndView res;
		PersonalRecord pr = personalRecordService.create();

		res = this.createEditPersonalRecordModelAndView(pr);
		return res;
	}

	//Save
	@RequestMapping(value = "/editPersonalRecord", method = RequestMethod.POST, params = "save")
	public ModelAndView savePersonalRecord(@Valid PersonalRecord pr,
			BindingResult binding) {
		// we check if the logged in actor has a curricula, if not create one
		// and bind the PR to it (save the record first)
		ModelAndView res;
		System.out.println(binding);
		System.out.println(pr.getFullName() + " " + pr.getEmail()
				+ pr.getLinkedInUrl());

		if (binding.hasErrors()) {
			res = createEditPersonalRecordModelAndView(pr);
		} else {
			try {
				System.out
						.println("tries to save the personal record / create curricula");
				personalRecordService.save(pr);// this already checks for the
												// existance of a curricula. and
												// if it doesnt exist it does it
												// all.
				res = new ModelAndView("redirect:show.do");
			} catch (Throwable e) {
				res = createEditPersonalRecordModelAndView(pr,
						"message.commit.error");
				System.out.println(e);
			}
		}
		return res;
	}

	// Edit-------------------------------------------------------------
	// @RequestMapping(value="/edit", method=RequestMethod.GET)
	// public ModelAndView edit(@RequestParam int boxId){
	// ModelAndView res;
	// Box box = boxService.findOne(boxId);
	//
	// Assert.notNull(box);
	// res = this.createEditModelAndView(box);
	// return res;
	// }

	// Save-------------------------------------------------------------
	// @RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	// public ModelAndView save(@Valid Box box, BindingResult binding){
	// ModelAndView res;
	//
	//
	// if(binding.hasErrors()){
	// res = createEditModelAndView(box);
	// }else{
	// try {
	// boxService.save(box);
	// res = new ModelAndView("redirect:list.do");
	// } catch (Throwable e) {
	// res = createEditModelAndView(box, "message.commit.error");
	//
	// //TODO: añadir el commit error al box.properties de message.
	// }
	// }
	// return res;
	// }

	// Delete-----------------------------------------------------------
	// @RequestMapping(value="/delete", method=RequestMethod.GET)
	// public ModelAndView delete(@RequestParam int boxId){
	// ModelAndView res;
	// Box box = boxService.findOne(boxId);
	//
	// try {
	// boxService.delete(box);
	// res = new ModelAndView("redirect:list.do");
	// } catch (Throwable e) {
	// res = createEditModelAndView(box, "message.commit.error");
	// }
	// return res;
	// }

	// Helper methods---------------------------------------------------
	protected ModelAndView createEditPersonalRecordModelAndView(
			PersonalRecord pr) {
		ModelAndView res;
		res = createEditPersonalRecordModelAndView(pr, null);
		return res;
	}

	protected ModelAndView createEditPersonalRecordModelAndView(
			PersonalRecord pr, String messageCode) {
		ModelAndView res;

		res = new ModelAndView("curricula/editPersonalRecord");
		res.addObject("personalRecord", pr);
		res.addObject("message", messageCode);

		return res;
	}

}