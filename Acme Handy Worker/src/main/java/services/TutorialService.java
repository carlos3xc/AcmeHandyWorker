package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;
import domain.Report;
import domain.Section;
import domain.Tutorial;


@Service
@Transactional
public class TutorialService {

	//Managed Repository -----
	@Autowired
	private TutorialRepository tutorialRepository;
	
	//Supporting Services -----
	
//	@Autowired
//	private SectionService sectionService; 
	
	//Constructors -----
	public TutorialService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Tutorial create(){
		Tutorial res;
		Authority authority = new Authority();
		authority.setAuthority("HANDYWORKER");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));
		res = new Tutorial();
		return res;
	}
	
	public Collection<Tutorial> findAll(){
		return tutorialRepository.findAll();
	}
	
	public Tutorial findOne(int tutorialId){
		return tutorialRepository.findOne(tutorialId);
	}
	
	public Tutorial save(Tutorial tutorial){
		Assert.notNull(tutorial);
		Tutorial result;
		result = this.tutorialRepository.save(tutorial);
		return result;
	}
	
	public void delete(Tutorial tutorial){
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		this.tutorialRepository.delete(tutorial);
	}
	
	//Other business methods -----
	
	
}