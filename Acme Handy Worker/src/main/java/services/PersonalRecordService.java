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
import domain.HandyWorker;
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
	
	@Autowired
	private ActorService actorService;
	
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
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		linkCurricula(a); //COMPROBAR SIEMPRE EL ROL ANTES DE EJECUTAR.		

		return a;
	}
	
	public void delete(PersonalRecord a){
		
		UserAccount owner = findowner(a);
		Assert.notNull(owner);
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
					System.out.println("se encuentra al dueño del pesonal Record.");
				}
			}
		return owner;
	}
	
	private void linkCurricula(PersonalRecord a){
		UserAccount ua = LoginService.getPrincipal();
		HandyWorker hw = (HandyWorker) actorService.getByUserAccountId(ua);
		System.out.println(hw.getUserAccount().getAuthorities());
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().equals(hw)){ // de manera intrinseca ya comprueba que es el dueño del PersonalRecord.
				c.setPersonalRecord(a);
				this.save(a);
				curriculaService.save(c);
			}
		}
	}
}