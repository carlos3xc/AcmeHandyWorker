package services;

import com.sun.org.apache.xpath.internal.operations.Quo;
import domain.Customer;
import domain.FixUpTask;
import domain.Quolet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.QuoletRepository;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class QuoletService {

    //Managed Repository -----
    @Autowired
    private QuoletRepository quoletRepository;

    //Supporting Services -----

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FixUpTaskService fixUpTaskService;


    //Simple CRUD methods -----

    public Quolet create(){
        Quolet result = new Quolet();

        result.setTicker(generateTicker());
        result.setCustomer(customerService.findByPrincipal());


        return  result;
    }

    public Quolet save(Quolet quolet){
        Quolet result, saved = quoletRepository.findOne(quolet.getId());
        Customer customer = customerService.findOne(quolet.getId());
        FixUpTask fixUpTask = fixUpTaskService.findOne((quolet.getId()));

        Assert.notNull(customer);
        Assert.notNull(fixUpTask);

        /*Quolet is added to the fixUpTask of the same Customer*/
        Assert.isTrue(fixUpTask.getCustomer().equals(customer));
        //TODO Control Check: Quolet can be edited by another Actor?
        Assert.isTrue(customer.equals(customerService.findByPrincipal()));

        if (quolet.getId() == 0 && isTickerCorrect(quolet)){
            quolet.setTicker(generateTicker());
        }else{
            /*Some Quolet attributes cannot change*/
            Assert.isTrue(saved.getTicker().equals(quolet));
            Assert.isTrue(saved.equals(customer));

            //TODO Control Check: Quolet cannot be updated when is in final mode
            Assert.isTrue(quolet.getPublicationMoment() == null);
        }

        result = quoletRepository.save(quolet);
        /* -- Bidirecctional Relationships -- */
            Collection<Quolet> customerQuolets = customer.getQuolets(),
                    fixUpTaskQuolets = fixUpTask.getQuolets();

            customerQuolets.add(quolet);
            fixUpTaskQuolets.add(quolet);

            customer.setQuolets(customerQuolets);
            fixUpTask.setQuolets(fixUpTaskQuolets);
            //TODO Control Check: Is it necessary to save customer and fixUpTask?
        /* -- */
        return result;
    }

    public void delete(Quolet quolet){
        Assert.isNull(quolet.getPublicationMoment());
        Customer customer = customerService.findOne(quolet.getId());
        FixUpTask fixUpTask = fixUpTaskService.findOne((quolet.getId()));

        /* -- Bidirecctional Relationships -- */
            Collection<Quolet> customerQuolets = customer.getQuolets(),
                    fixUpTaskQuolets = fixUpTask.getQuolets();

            customerQuolets.remove(quolet);
            fixUpTaskQuolets.remove(quolet);

            customer.setQuolets(customerQuolets);
            fixUpTask.setQuolets(fixUpTaskQuolets);
            //TODO Control Check: Is it necessary to save customer and fixUpTask?
        /* -- */

        quoletRepository.delete(quolet);
    }

    public Quolet findOne(int quoletId){
        return quoletRepository.findOne(quoletId);
    }

    public Collection<Quolet> findAll(){
        return  quoletRepository.findAll();
    }

    //Other business methods -----

    public Double  getAvgQuoletsPerCustomer(){
        return quoletRepository.getAvgQuoletsPerCustomer();
    }

    public Double getStddevQuoletsPerCustomer(){
        return  quoletRepository.getStddevQuoletsPerCustomer();
    }

    public Double getRatioPublishedQuoletsPerFixUpTask(){
        return getRatioPublishedQuoletsPerFixUpTask();
    }

    public Double getRatioUnOublishedQuoletsPerFixUpTask(){
        return quoletRepository.getRatioUnpublishedQuoletsPerFixUpTask();
    }

    public Collection<Quolet> findPublishedByFixUpTask(FixUpTask fixUpTask){
        return quoletRepository.findPublishedByFixUpTaskId(fixUpTask.getId());
    }
    private String generateTicker(){
        String result;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");

        result = dateFormat.format(date);
        return result;
    }

    private Boolean isTickerCorrect(Quolet quolet){
        Boolean result;
        String currentTicker = quolet.getTicker(), newTicker;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        newTicker = dateFormat.format(new Date());

        result = currentTicker.equals(newTicker);

        return result;
    }
}
