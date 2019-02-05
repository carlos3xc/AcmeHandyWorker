package controllers.customer;

import controllers.AbstractController;
import domain.FixUpTask;
import domain.Vaste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;
import services.VasteService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/vaste/customer")
public class VasteCustomerController extends AbstractController {

    @Autowired
    private VasteService vasteService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView result;

        Collection<Vaste> vastes =
                vasteService.findQuoletsByCustomer(customerService.findByPrincipal());

        result = new ModelAndView("vaste/list");
        result.addObject("vastes", vastes);
        result.addObject("requestURI", "vaste/customer/list.do");

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(){
        ModelAndView result;

        Vaste vaste = vasteService.create();
        result = createEditModelAndView(vaste);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int vasteId){
        ModelAndView result;
        Vaste vaste;

        vaste = vasteService.findOne(vasteId);
        Assert.notNull(vaste);
        result = createEditModelAndView(vaste);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Vaste vaste, BindingResult binding){
        ModelAndView result;

        if (binding.hasErrors()){
            result = createEditModelAndView(vaste);
        }else{
            try{
                vasteService.save(vaste);
                result = new ModelAndView("redirect: list.do");
            }catch (Throwable oops){
                result = createEditModelAndView(vaste, "vaste.commit.error");
            }
        }

        result = createEditModelAndView(vaste);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "publish")
    public ModelAndView publish(@Valid Vaste vaste, BindingResult binding){
        ModelAndView result;

        if (binding.hasErrors()){
            result = createEditModelAndView(vaste);
        }else{
            try{
                vasteService.publish(vaste);
                result = new ModelAndView("redirect: list.do");
            }catch (Throwable oops){
                result = createEditModelAndView(vaste, "vaste.commit.error");
            }
        }

        result = createEditModelAndView(vaste);

        return result;
    }


    protected ModelAndView createEditModelAndView(Vaste vaste){
        ModelAndView result;

        result = createEditModelAndView(vaste, null);
         return result;
    }

    // Ancillary methods ----------------------------------------------------------

    protected ModelAndView createEditModelAndView(Vaste vaste, String messageCode){
        ModelAndView result;

        /*Dropdown collections*/
        Collection<FixUpTask> fixUpTasks = customerService.findByPrincipal().getFixUpTasks();

        result = new ModelAndView("vaste/edit");
        result.addObject("fixUpTasks", fixUpTasks);

        return result;
    }
}
