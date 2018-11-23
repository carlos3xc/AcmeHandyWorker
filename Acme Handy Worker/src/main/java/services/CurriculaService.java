package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;


@Service
@Transactional
public class CurriculaService {

	//Managed Repository -----
	@Autowired
	private CurriculaRepository curriculaRepository;
	
	//Supporting Services -----
	
	@Autowired
	private HandyWorkerService hwService; 
	
	@Autowired
	private ActorService actorService;
	
	//Constructors -----
	public CurriculaService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Curricula create(){

		
		UserAccount ua = LoginService.getPrincipal();
		Curricula c = new Curricula();
		
		//Solo le asignamos un handyWorker si 
		
		if (LoginService.hasRole("HANDYWORKER")) {
			HandyWorker hw  = (HandyWorker) actorService.getByUserAccountId(ua);
			c.setHandyWorker(hw);
		}

		c.setEducationRecords(new ArrayList<EducationRecord>());
		c.setEndorserRecords(new ArrayList<EndorserRecord>());
		c.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		c.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		
		return c;
	}
	
	public Collection<Curricula> findAll(){
		return curriculaRepository.findAll();
	}
	
	public Curricula findOne(int Id){
		return curriculaRepository.findOne(Id);
	}
	
	public Curricula save(Curricula c){
		
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(c.getHandyWorker().getUserAccount().equals(userAccount));
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		
		curriculaRepository.save(c);
		return c;
	}
	
	public void delete(Curricula c){
		
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(c.getHandyWorker().getUserAccount().equals(userAccount));
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		
		curriculaRepository.delete(c);
	}
	
	//Other business methods -----
	
	
}