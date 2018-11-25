package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	@Autowired
	private ActorService actorService;
	
	//Constructors -----
	public ProfessionalRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public ProfessionalRecord create(){
		ProfessionalRecord res = new ProfessionalRecord();
		
		Date start = new Date();
		start.setTime(start.getTime()-1000);
		res.setStartDate(start);
		
		return res;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int Id){
		return professionalRecordRepository.findOne(Id);
	}
	
	public ProfessionalRecord save(ProfessionalRecord a){
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		linkCurricula(a); //COMPROBAR SIEMPRE EL ROL ANTES DE EJECUTAR.

		return a;
	}
	
	public void delete(ProfessionalRecord a){
		
		UserAccount owner = findowner(a);
		Assert.notNull(owner);
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(owner.equals(userAccount));
		
		professionalRecordRepository.delete(a);
	}
	
	//Other business methods -----
	private UserAccount findowner(ProfessionalRecord a){
		
		Collection<Curricula> todas = curriculaService.findAll();
		UserAccount owner = null;
		for (Curricula c : todas) {
			for (ProfessionalRecord m : c.getProfessionalRecords()) {
				if(m.equals(a)){
					owner = c.getHandyWorker().getUserAccount();
				}
			}
		}
		return owner;
	}
	private void linkCurricula(ProfessionalRecord a){
		UserAccount ua = LoginService.getPrincipal();
		HandyWorker hw = (HandyWorker) actorService.getByUserAccountId(ua);
		System.out.println(hw.getUserAccount().getAuthorities());
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().equals(hw)){ // de manera intrinseca ya comprueba que es el dueño del PersonalRecord.
				Collection<ProfessionalRecord> aux = c.getProfessionalRecords();
				aux.add(a);
				c.setProfessionalRecords(aux);
				this.save(a);
				curriculaService.save(c);
			}
		}
	}
}