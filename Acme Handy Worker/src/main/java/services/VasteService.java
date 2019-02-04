package services;

import domain.Customer;
import domain.FixUpTask;
import domain.Vaste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.VasteRepository;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@Service
@Transactional
public class VasteService {

    //Managed Repository -----
    @Autowired
    private VasteRepository vasteRepository;

    //Supporting Services -----

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FixUpTaskService fixUpTaskService;


    //Simple CRUD methods -----

    public Vaste create(){
        Vaste result = new Vaste();

        result.setTicker(generateTicker());
        result.setCustomer(customerService.findByPrincipal());


        return  result;
    }

    public Vaste save(Vaste vaste){
        Vaste result, saved = vasteRepository.findOne(vaste.getId());
        Customer customer = customerService.findOne(vaste.getId());
        FixUpTask fixUpTask = fixUpTaskService.findOne((vaste.getId()));

        Assert.notNull(customer);
        Assert.notNull(fixUpTask);

        Assert.isTrue(fixUpTask.getCustomer().equals(customer));
        //Vaste cannot be edited by another Actor? No
        Assert.isTrue(customer.equals(customerService.findByPrincipal()));

        if (vaste.getId() == 0 && !isTickerCorrect(vaste)){
            vaste.setTicker(generateTicker());
        }else{
            /*Some Vaste attributes cannot change*/
            Assert.isTrue(saved.getTicker().equals(vaste));
            Assert.isTrue(saved.equals(customer));
                /* 1. Vaste is not going to be published
                *  2. Vaste is going to be published, it previously was not
                *  3. Vaste is already published so publicationMoments must match*/
                Assert.isTrue(vaste.getPublicationMoment() == null
                        || vaste.getPublicationMoment() != null && saved.getPublicationMoment() == null
                        || vaste.getPublicationMoment().equals(saved.getPublicationMoment()));

            //Vaste cannot be updated when is in final mode
            Assert.isTrue(saved.getPublicationMoment() != null);
        }

        Boolean isGoingToBePublished =
				saved.getPublicationMoment() == null && vaste.getPublicationMoment() != null;

        result = vasteRepository.save(vaste);

        if(isGoingToBePublished){
			/* -- Fix-Up Task Relationship -- */
			Collection<Vaste> fixUpTaskPublishedVastes = fixUpTask.getPublishedVastes();

			Assert.isTrue(vaste.getPublicationMoment()== null);
			fixUpTaskPublishedVastes.add(vaste);

			fixUpTask.setPublishedVastes(fixUpTaskPublishedVastes);
			//TODO Control Check: Is it necessary to save fixUpTask?
			fixUpTaskService.save(fixUpTask);
			/* -- */
		}

        return result;
    }

    public void delete(Vaste vaste){
        Assert.isNull(vaste.getPublicationMoment());

        FixUpTask fixUpTask = fixUpTaskService.findOne((vaste.getFixUpTask().getId()));
        Assert.notNull(fixUpTask);

        /* -- Fix-Up Task Relationship -- */
        /* -- */

        vasteRepository.delete(vaste);
    }

    public Vaste findOne(int quoletId){
        return vasteRepository.findOne(quoletId);
    }

    public Collection<Vaste> findAll(){
        return  vasteRepository.findAll();
    }

    //Other business methods -----

    public Vaste publish(Vaste vaste){
        Vaste savedVaste = findOne(vaste.getId());
        Long millis = System.currentTimeMillis() - 1000;

        if (vaste.getId() == 0){
            vaste.setPublicationMoment(new Date(millis));
        }else{
            Assert.isTrue(savedVaste.getPublicationMoment() == null);
            vaste.setPublicationMoment(new Date(millis));
        }

        return save(vaste);
    }

    public Double getRatioPublishedQuoletsPerFixUpTask(){
        return vasteRepository.getRatioPublishedQuoletsPerFixUpTask();
    }

    public Double getRatioUnOublishedQuoletsPerFixUpTask(){
        return vasteRepository.getRatioUnpublishedQuoletsPerFixUpTask();
    }

    public Collection<Vaste> findPublishedByFixUpTask(FixUpTask fixUpTask){
        return vasteRepository.findPublishedByFixUpTaskId(fixUpTask.getId());
    }

    public Collection<Vaste> findQuoletsByCustomer(Customer customer){
    	return vasteRepository.findQuoletByCustomerId(customer.getId());
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

    private Boolean isTickerCorrect(Vaste vaste){
        Boolean result;

        String currentTicker = vaste.getTicker();
        String newTicker = generateTicker();

        result = currentTicker.substring(0,3).equals(newTicker.substring(0,3))
				&& currentTicker.substring(8,12).equals(newTicker.substring(0,3));

        return result;
    }
}
