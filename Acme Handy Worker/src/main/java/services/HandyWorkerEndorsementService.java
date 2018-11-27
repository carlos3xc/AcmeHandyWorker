package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerEndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorker;
import domain.HandyWorkerEndorsement;


@Service
@Transactional
public class HandyWorkerEndorsementService {

	//Managed Repository -----
	@Autowired
	private HandyWorkerEndorsementRepository handyWorkerEndorsementRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Constructors -----
	public HandyWorkerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public HandyWorkerEndorsement create(){
		HandyWorkerEndorsement res = new HandyWorkerEndorsement();
		res.setCustomer(new Customer());
		res.setHandyWorker(new HandyWorker());
		return res;
	}
	
	public Collection<HandyWorkerEndorsement> findAll(){
		return handyWorkerEndorsementRepository.findAll();
	}
	
	public HandyWorkerEndorsement findOne(int HandyWorkerEndorsementId){
		return handyWorkerEndorsementRepository.findOne(HandyWorkerEndorsementId);
	}
	
	public HandyWorkerEndorsement save(HandyWorkerEndorsement handyWorkerEndorsement){
		
		HandyWorkerEndorsement saved;
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		HandyWorker hw = handyWorkerService.findByUserAccountId(userAccount.getId());
		Customer c = customerService.findByUserAccountId(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		
		handyWorkerEndorsement.setMoment(current);
		handyWorkerEndorsement.setText("Texto de prueba 2");
		handyWorkerEndorsement.setHandyWorker(hw);
		handyWorkerEndorsement.setCustomer(c);
		saved = handyWorkerEndorsementRepository.save(handyWorkerEndorsement);
		
		return saved;
	}
	
	public void delete(HandyWorkerEndorsement handyWorkerEndorsement){
		
		Authority e = new Authority();
		e.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		this.handyWorkerEndorsementRepository.delete(handyWorkerEndorsement);		
	}
	
	//Other business methods -----
	
	
}