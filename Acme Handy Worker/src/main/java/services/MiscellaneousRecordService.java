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
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -----
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	@Autowired
	private ActorService actorService;
	
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
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		linkCurricula(a); //COMPROBAR SIEMPRE EL ROL ANTES DE EJECUTAR.
		 
		return a;
	}
	
	public void delete(MiscellaneousRecord a){
		
		
		UserAccount owner = findowner(a);
		Assert.notNull(owner);
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
	private void linkCurricula(MiscellaneousRecord a){
		UserAccount ua = LoginService.getPrincipal();
		HandyWorker hw = (HandyWorker) actorService.getByUserAccountId(ua);
		System.out.println(hw.getUserAccount().getAuthorities());
		for (Curricula c : curriculaService.findAll()) {
			if(c.getHandyWorker().equals(hw)){ // de manera intrinseca ya comprueba que es el dueño del PersonalRecord.
				Collection<MiscellaneousRecord> aux = c.getMiscellaneousRecords();
				aux.add(a);
				c.setMiscellaneousRecords(aux);
				this.save(a);
				curriculaService.save(c);
			}
		}
	}
	
	
}