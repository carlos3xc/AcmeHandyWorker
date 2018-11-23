package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.PersonalRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Constructors -----
	public PersonalRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public PersonalRecord create(){

		PersonalRecord res = new PersonalRecord();
		return res;
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int Id){
		return personalRecordRepository.findOne(Id);
	}
	
	public PersonalRecord save(PersonalRecord a){
		
		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		personalRecordRepository.save(a);
		return a;
	}
	
	public void delete(PersonalRecord a){
		
		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		personalRecordRepository.delete(a);
	}
	
	//Other business methods -----
	private UserAccount findowner(PersonalRecord a){
		
		Collection<Curricula> todas = curriculaService.findAll();
		UserAccount owner = null;
		for (Curricula c : todas) {
				if(c.getPersonalRecord().equals(a)){
					owner = c.getHandyWorker().getUserAccount();
				}
			}
		return owner;
	}
	
}