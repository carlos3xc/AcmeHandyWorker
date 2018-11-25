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
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	@Autowired
	private ActorService actorService;
	
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
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		linkCurricula(a); //COMPROBAR SIEMPRE EL ROL ANTES DE EJECUTAR.

		return a;
	}
	
	public void delete(EndorserRecord a){
		
		UserAccount owner = findowner(a);
		Assert.notNull(owner);
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
	private void linkCurricula(EndorserRecord a){
		UserAccount ua = LoginService.getPrincipal();
		HandyWorker hw = (HandyWorker) actorService.getByUserAccountId(ua);
		System.out.println(hw.getUserAccount().getAuthorities());
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().equals(hw)){ // de manera intrinseca ya comprueba que es el dueño del PersonalRecord.
				Collection<EndorserRecord> aux = c.getEndorserRecords();
				aux.add(a);
				c.setEndorserRecords(aux);
				this.save(a);
				curriculaService.save(c);
			}
		}
	}
	
}