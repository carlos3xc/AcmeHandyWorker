package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;


@Service
@Transactional
public class FixUpTaskService {

	//Managed Repository -----
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ApplicationService applicationService;
	
	//Simple CRUD methods -----
	public FixUpTask create(){
		// SIN PROBAR
		FixUpTask res = new FixUpTask();
		res.setApplications(new ArrayList<Application>());
		res.setComplaints(new ArrayList<Complaint>());
		
		return res;
	}
	
	public Collection<FixUpTask> findAll(){
		return fixUpTaskRepository.findAll();
	}
	
	public FixUpTask findOne(int Id){
		return fixUpTaskRepository.findOne(Id);
	}
	
	public FixUpTask save(FixUpTask fx){
		// SIN PROBAR
		FixUpTask saved;
		Collection<FixUpTask> fixUpTasks;
		Assert.isTrue( fx.getId()==0 || fx.getId() != 0  &&
				fx.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		if(fx.getId()==0){
			fx.setCustomer(customerService.findByUserAccountId(LoginService.getPrincipal().getId()));
			fx.setMoment(current);
			fx.setTicker(generateTicker());
		}
		saved = fixUpTaskRepository.save(fx);
		fixUpTasks = fixUpTaskRepository.findAll();
		Assert.isTrue(fixUpTasks.contains(saved));
		return saved;
	}
	
	public void delete(FixUpTask fx){
		// SIN PROBAR
		Assert.isTrue(fx.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));	
		Collection<Complaint> complaints = fx.getComplaints();
		Collection<Application> applications = fx.getApplications();
		Customer c = fx.getCustomer();
		for(Complaint co: complaints) complaintService.delete(co);
		for(Application a: applications) applicationService.delete(a);
		c.getFixUpTasks().remove(fx);
		customerService.save(c);
		
		fixUpTaskRepository.delete(fx);
	}
	
	//Other business methods -----
	
	private String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		t = t + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ Integer.toString(n.get(Calendar.MONTH) +1)
				+ Integer.toString(n.get(Calendar.DAY_OF_MONTH))
				+ "-"+randomWordAndNumber();

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
	
}