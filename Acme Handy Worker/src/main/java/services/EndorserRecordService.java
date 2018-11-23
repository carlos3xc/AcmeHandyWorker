package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;


@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Constructors -----
	public EndorserRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public EndorserRecord create(){
	
		EndorserRecord res = new EndorserRecord();
		return res;
	}
	
	public Collection<EndorserRecord> findAll(){
		return endorserRecordRepository.findAll();
	}
	
	public EndorserRecord findOne(int Id){
		return endorserRecordRepository.findOne(Id);
	}
	
	public EndorserRecord save(EndorserRecord a){
		
		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		endorserRecordRepository.save(a);
		return a;
	}
	
	public void delete(EndorserRecord a){
		
		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		endorserRecordRepository.delete(a);
	}
	
	//Other business methods -----
	private UserAccount findowner(EndorserRecord a){
		
		Collection<Curricula> todas = curriculaService.findAll();
		UserAccount owner = null;
		for (Curricula c : todas) {
			for (EndorserRecord m : c.getEndorserRecords()) {
				if(m.equals(a)){
					owner = c.getHandyWorker().getUserAccount();
				}
			}
		}
		return owner;
	}
	
}