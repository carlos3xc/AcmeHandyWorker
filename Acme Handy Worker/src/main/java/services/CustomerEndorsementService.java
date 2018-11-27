package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerEndorsementRepository;
import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;


@Service
@Transactional
public class CustomerEndorsementService {

	//Managed Repository -----
	@Autowired
	private CustomerEndorsementRepository customerEndorsementRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Constructors -----
	public CustomerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CustomerEndorsement create(){
		CustomerEndorsement res = new CustomerEndorsement();
		res.setCustomer(new Customer());
		res.setHandyWorker(new HandyWorker());
		return res;
	}
	
	public Collection<CustomerEndorsement> findAll(){
		return customerEndorsementRepository.findAll();
	}
	
	public CustomerEndorsement findOne(int customerEndorsementId){
		return customerEndorsementRepository.findOne(customerEndorsementId);
	}
	
	public CustomerEndorsement save(CustomerEndorsement customerEndorsement){
		CustomerEndorsement saved;
		Authority e = new Authority();
		e.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();
		Customer c = customerService.findByUserAccountId(userAccount.getId());
		HandyWorker hw = handyWorkerService.findByUserAccountId(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		
		customerEndorsement.setMoment(current);
		customerEndorsement.setText("hduhsid");
		customerEndorsement.setCustomer(c);
		customerEndorsement.setHandyWorker(hw);
		saved = customerEndorsementRepository.save(customerEndorsement);
		return saved;
	}
	
	

	public void delete(CustomerEndorsement customerEndorsement){
		Authority e = new Authority();
		e.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		this.customerEndorsementRepository.delete(customerEndorsement);	
		
	}
	
	//Other business methods -----
	
}