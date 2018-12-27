
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;


	// Tree of category

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories = new ArrayList<>();
		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/list.do");
		result.addObject("categories", categories);

		return result;
	}

}
