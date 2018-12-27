
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import forms.CategoryForm;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;


	// Tree of category

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories = new ArrayList<>();
		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/administrator/list.do");
		result.addObject("categories", categories);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Category category = this.categoryService.create();
		final CategoryForm categoryForm = new CategoryForm();
		categoryForm.setId(category.getId());
		categoryForm.setVersion(category.getVersion());
		categoryForm.setName(category.getName());
		categoryForm.setHijos(new ArrayList<Category>());

		result = this.createEditModelAndView(categoryForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		category = this.categoryService.findOne(categoryId);

		if (category != null && !(category.getName().equals("CATEGORY"))) {
			final CategoryForm categoryForm = new CategoryForm();
			categoryForm.setId(category.getId());
			categoryForm.setVersion(category.getVersion());
			categoryForm.setName(category.getName());
			categoryForm.setCategoryFather(category.getParentCategory());
			result = this.createEditModelAndView(categoryForm);
		} else
			result = this.list();
		return result;
	}
	@RequestMapping(value = "/edit", params = "save", method = RequestMethod.POST)
	public ModelAndView edit(@Valid final CategoryForm categoryForm, final BindingResult bindingResult) {
		ModelAndView result;
		final Category category = this.categoryService.create();
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(categoryForm);
		else
			try {

				category.setId(categoryForm.getId());
				category.setVersion(categoryForm.getVersion());
				category.setName(categoryForm.getName());
				category.setParentCategory(categoryForm.getCategoryFather());

				if (category.getName().equals("CATEGORY"))
					result = this.createEditModelAndView(categoryForm, "category.commit.error");
				else {

					if (category.getId() == 0) {
						final Category categoryFather = categoryForm.getCategoryFather();
						//final Collection<Category> categories = categoryFather.getCategories();
						final Collection<Category> categories = categoryForm.getHijos();
						
						final String nombre = category.getName();
						for (final Category c : categories)
							Assert.isTrue(!(c.getName().equals(nombre)));

						categories.add(category);
						//categoryFather.setCategories(categories);
						this.categoryService.save(categoryFather);

					} else
						this.categoryService.save(category);
					result = new ModelAndView("redirect:list.do");

				}

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(categoryForm, "category.commit.error");

			}

		return result;
	}
	@RequestMapping(value = "/edit", params = "delete", method = RequestMethod.POST)
	public ModelAndView delete(final CategoryForm categoryForm, final BindingResult bindingResult) {
		ModelAndView result;

		final Category category = this.categoryService.create();
		category.setId(categoryForm.getId());
		category.setVersion(categoryForm.getVersion());
		category.setName(categoryForm.getName());
		category.setParentCategory(category.getParentCategory());

		final boolean a = !(category.getName().trim().toString().equals(this.categoryService.getCategoryByName("category").getName().trim().toString()));

		if (bindingResult.hasErrors())

			result = this.createEditModelAndView(categoryForm);
		else
			try {
				if (a)
					this.categoryService.delete(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(categoryForm, "category.commit.error");

			}

		return result;
	}
	private ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;
		result = this.createEditModelAndView(categoryForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final CategoryForm categoryForm, final String message) {
		ModelAndView result;
		if (categoryForm.getId() == 0)
			result = new ModelAndView("category/administrator/create");
		else
			result = new ModelAndView("category/administrator/edit");
		final Collection<Category> categories = this.categoryService.findAll();
		final boolean nuevo = categoryForm.getId() == 0;
		result.addObject("categoryForm", categoryForm);
		result.addObject("nuevo", nuevo);
		result.addObject("categories", categories);
		result.addObject("message", message);

		return result;
	}

}
