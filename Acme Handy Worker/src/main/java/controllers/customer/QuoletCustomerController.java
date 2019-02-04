package controllers.customer;

import controllers.AbstractController;
import domain.FixUpTask;
import domain.Quolet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CustomerService;
import services.QuoletService;

import javax.jws.WebParam;
import javax.naming.Binding;
import javax.validation.Valid;
import java.util.BitSet;
import java.util.Collection;

@Controller
@RequestMapping("/quolet/customer")
public class QuoletCustomerController extends AbstractController {

    @Autowired
    private QuoletService quoletService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView result;

        Collection<Quolet> quolets = customerService.findByPrincipal().getQuolets();

        result = new ModelAndView("quolet/list");
        result.addObject("quolets", quolets);
        result.addObject("requestURI", "quolet/customer/list.do");

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(){
        ModelAndView result;

        Quolet quolet = quoletService.create();
        result = createEditModelAndView(quolet);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam int quoletId){
        ModelAndView result;
        Quolet quolet;

        quolet = quoletService.findOne(quoletId);
        Assert.notNull(quolet);
        result = createEditModelAndView(quolet);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Quolet quolet, BindingResult binding){
        ModelAndView result;

        if (binding.hasErrors()){
            result = createEditModelAndView(quolet);
        }else{
            try{
                quoletService.save(quolet);
                result = new ModelAndView("redirect: list.do");
            }catch (Throwable oops){
                result = createEditModelAndView(quolet, "quolet.commit.error");
            }
        }

        result = createEditModelAndView(quolet);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "publish")
    public ModelAndView publish(@Valid Quolet quolet, BindingResult binding){
        ModelAndView result;

        if (binding.hasErrors()){
            result = createEditModelAndView(quolet);
        }else{
            try{
                quoletService.publish(quolet);
                result = new ModelAndView("redirect: list.do");
            }catch (Throwable oops){
                result = createEditModelAndView(quolet, "quolet.commit.error");
            }
        }

        result = createEditModelAndView(quolet);

        return result;
    }
    

    protected ModelAndView createEditModelAndView(Quolet quolet){
        ModelAndView result;

        result = createEditModelAndView(quolet, null);
         return result;
    }

    // Ancillary methods ----------------------------------------------------------

    protected ModelAndView createEditModelAndView(Quolet quolet, String messageCode){
        ModelAndView result;

        /*Dropdown collections*/
        Collection<FixUpTask> fixUpTasks = customerService.findByPrincipal().getFixUpTasks();

        result = new ModelAndView("quolet/edit");
        result.addObject("fixUpTasks", fixUpTasks);

        return result;
    }
}
