package services;

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
import java.util.Random;

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

        Assert.isTrue(fixUpTask.getCustomer().equals(customer));
        //Quolet cannot be edited by another Actor? No
        Assert.isTrue(customer.equals(customerService.findByPrincipal()));

        if (quolet.getId() == 0 && !isTickerCorrect(quolet)){
            quolet.setTicker(generateTicker());
        }else{
            /*Some Quolet attributes cannot change*/
            Assert.isTrue(saved.getTicker().equals(quolet));
            Assert.isTrue(saved.equals(customer));
                /* 1. Quolet is not going to be published
                *  2. Quolet is going to be published, it previously was not
                *  3. Quolet is already published so publicationMoments must match*/
                Assert.isTrue(quolet.getPublicationMoment() == null
                        || quolet.getPublicationMoment() != null && saved.getPublicationMoment() == null
                        || quolet.getPublicationMoment().equals(saved.getPublicationMoment()));

            //Quolet cannot be updated when is in final mode
            Assert.isTrue(saved.getPublicationMoment() != null);
        }

        Boolean isGoingToBePublished =
				saved.getPublicationMoment() == null && quolet.getPublicationMoment() != null;

        result = quoletRepository.save(quolet);

        if(isGoingToBePublished){
			/* -- Fix-Up Task Relationship -- */
			Collection<Quolet> fixUpTaskPublishedQuolets = fixUpTask.getPublishedQuolets();

			Assert.isTrue(quolet.getPublicationMoment()== null);
			fixUpTaskPublishedQuolets.add(quolet);

			fixUpTask.setPublishedQuolets(fixUpTaskPublishedQuolets);
			//TODO Control Check: Is it necessary to save fixUpTask?
			fixUpTaskService.save(fixUpTask);
			/* -- */
		}

        return result;
    }

    public void delete(Quolet quolet){
        Assert.isNull(quolet.getPublicationMoment());

        FixUpTask fixUpTask = fixUpTaskService.findOne((quolet.getFixUpTask().getId()));
        Assert.notNull(fixUpTask);

        /* -- Fix-Up Task Relationship -- */
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

    public Quolet publish(Quolet quolet){
        Quolet savedQuolet = findOne(quolet.getId());
        Long millis = System.currentTimeMillis() - 1000;

        if (quolet.getId() == 0){
            quolet.setPublicationMoment(new Date(millis));
        }else{
            Assert.isTrue(savedQuolet.getPublicationMoment() == null);
            quolet.setPublicationMoment(new Date(millis));
        }

        return save(quolet);
    }

    public Double getRatioPublishedQuoletsPerFixUpTask(){
        return quoletRepository.getRatioPublishedQuoletsPerFixUpTask();
    }

    public Double getRatioUnOublishedQuoletsPerFixUpTask(){
        return quoletRepository.getRatioUnpublishedQuoletsPerFixUpTask();
    }

    public Collection<Quolet> findPublishedByFixUpTask(FixUpTask fixUpTask){
        return quoletRepository.findPublishedByFixUpTaskId(fixUpTask.getId());
    }

    public Collection<Quolet> findQuoletsByCustomer(Customer customer){
    	return quoletRepository.findQuoletByCustomerId(customer.getId());
	}
    private String generateTicker(){
        String result;
        Date date = new Date();
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yy");
		SimpleDateFormat dateFormatMonthAndDay = new SimpleDateFormat("MMdd");

        result = dateFormatYear.format(date)
				+ '-' + randomString()
				+ '-' + dateFormatMonthAndDay.format(date);

        return result;
    }

	private String randomString(){
		String DICTIONARY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz1234567890";
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		String result;
		while(stringBuilder.length() < 4){
			int indexDictionary = random.nextInt()% DICTIONARY.length();
			stringBuilder.append(DICTIONARY.charAt(indexDictionary));
		}
		result = stringBuilder.toString();
		return result;
	}

    private Boolean isTickerCorrect(Quolet quolet){
        Boolean result;

        String currentTicker = quolet.getTicker();
        String newTicker = generateTicker();

        result = currentTicker.substring(0,3).equals(newTicker.substring(0,3))
				&& currentTicker.substring(8,12).equals(newTicker.substring(0,3));

        return result;
    }
}
