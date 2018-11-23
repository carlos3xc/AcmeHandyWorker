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


import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Referee;
import domain.Report;


@Service
@Transactional
public class ComplaintService {

	//Managed Repository -----
	@Autowired
	private ComplaintRepository complaintRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	//Constructors -----
	public ComplaintService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Complaint create(){
		// SIN PROBAR
		Complaint res = new Complaint();
		res.setAttachments(new ArrayList<String>());
		return res;
	}
	
	public Collection<Complaint> findAll(){
		return complaintRepository.findAll();
	}
	
	public Complaint findOne(int Id){
		return complaintRepository.findOne(Id);
	}
	
	public Complaint save(Complaint c){
		// SIN PROBAR
		Complaint saved;
		Collection<Complaint> complaints;
		Assert.isTrue(c.getId() != 0  ||
				c.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));
		Date current = new Date(System.currentTimeMillis() - 1000);
		Customer customer = customerService.findByUserAccountId(LoginService.getPrincipal().getId());
		c.setMoment(current);

		if(c.getId()==0){
			c.setCustomer(customer);
			c.setMoment(current);
			c.setTicker(generateTicker());
		}
		
		complaints = complaintRepository.findAll();
		saved = complaintRepository.save(c);
		Assert.isTrue(complaints.contains(saved));
		return saved;
	}
	
	public void delete(Complaint c){
		// SIN PROBAR
		Assert.isTrue(c.getCustomer().getUserAccount().equals(LoginService.getPrincipal()));
		Collection<Complaint> complaints;
		FixUpTask fx= c.getFixUpTask();
		FixUpTask saved;
		
		fx.getComplaints().remove(c);
		saved = fixUpTaskService.save(fx);
		
		complaints = complaintRepository.findAll();
		
		complaintRepository.delete(c);
		
		Assert.isTrue(!(saved.getComplaints().contains(c)));
		Assert.isTrue(!(complaints.contains(c)));
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
				+ randomWordAndNumber();

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