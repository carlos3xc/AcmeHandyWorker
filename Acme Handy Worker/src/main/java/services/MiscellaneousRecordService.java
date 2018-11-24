package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.MiscellaneousRecord;


@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -----
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Constructors -----
	public MiscellaneousRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public MiscellaneousRecord create(){
		MiscellaneousRecord res = new MiscellaneousRecord();
		return res;
	}
	
	public Collection<MiscellaneousRecord> findAll(){
		return miscellaneousRecordRepository.findAll();
	}
	
	public MiscellaneousRecord findOne(int Id){
		return miscellaneousRecordRepository.findOne(Id);
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord a){
		
		//Find the owner of the record:

		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		miscellaneousRecordRepository.save(a);
		return a;
	}
	
	public void delete(MiscellaneousRecord a){
		
		UserAccount owner = findowner(a);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		miscellaneousRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	private UserAccount findowner(MiscellaneousRecord a){
		
		Collection<Curricula> todas = curriculaService.findAll();
		UserAccount owner = null;
		for (Curricula c : todas) {
			for (MiscellaneousRecord m : c.getMiscellaneousRecords()) {
				if(m.equals(a)){
					owner = c.getHandyWorker().getUserAccount();
				}
			}
		}
		return owner;
	}
	
	
}