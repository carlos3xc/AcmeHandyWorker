package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerEndorsementRepository;
import security.LoginService;
import security.UserAccount;
import domain.CustomerEndorsement;
import domain.Tutorial;


@Service
@Transactional
public class CustomerEndorsementService {

	//Managed Repository -----
	@Autowired
	private CustomerEndorsementRepository customerEndorsementRepository;
	
	//Supporting Services -----
	
	//Constructors -----
	public CustomerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CustomerEndorsement create(){
		CustomerEndorsement res = new CustomerEndorsement();
		return res;
	}
	
	public Collection<CustomerEndorsement> findAll(){
		return customerEndorsementRepository.findAll();
	}
	
	public CustomerEndorsement findOne(int customerEndorsementId){
		return customerEndorsementRepository.findOne(customerEndorsementId);
	}
	
	public CustomerEndorsement save(CustomerEndorsement customerEndorsement){
		Assert.notNull(customerEndorsement);	
		CustomerEndorsement res;
		res = customerEndorsementRepository.save(customerEndorsement);
		return res;
	}
	
	public void delete(CustomerEndorsement customerEndorsement){
		Assert.notNull(customerEndorsement);
		Assert.isTrue(customerEndorsement.getId() != 0);
		this.customerEndorsementRepository.delete(customerEndorsement);	
	}
	
	//Other business methods -----
	
	
}