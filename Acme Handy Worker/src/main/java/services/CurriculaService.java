package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

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
		
		
		
		if (LoginService.hasRole("HANDYWORKER")) {
			HandyWorker hw  = (HandyWorker) actorService.getByUserAccountId(ua);
			c.setHandyWorker(hw);
		}

		c.setEducationRecords(new ArrayList<EducationRecord>());
		c.setEndorserRecords(new ArrayList<EndorserRecord>());
		c.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		c.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		c.setTicker(this.generateTicker());
		
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
	
	private String generateTicker(){
		Date date = new Date(); // your date
		Calendar n = Calendar.getInstance();
		n.setTime(date);
		String t = "";
		t = t + Integer.toString(n.get(Calendar.YEAR) - 2000)
				+ Integer.toString(n.get(Calendar.MONTH) +1)
				+ Integer.toString(n.get(Calendar.DAY_OF_MONTH))
				+ "-"+ randomWordAndNumber();

		return t;
	}
	
	private String randomWordAndNumber(){
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 6) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
	
}