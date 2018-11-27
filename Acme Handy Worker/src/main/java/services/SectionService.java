package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Message;
import domain.Section;
import domain.Tutorial;


@Service
@Transactional
public class SectionService {

	//Managed Repository -----
	@Autowired
	private SectionRepository sectionRepository;
	
//Supporting Services -----
	
	
	//Constructors -----
	public SectionService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Section create(Tutorial tutorial){
		Section res = new Section();
		res.setTutorial(tutorial);
		return res;
	}
	
	public Collection<Section> findAll(){
		Collection<Section> result;
		result = this.sectionRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Section findOne(int Id){
		Section result;
		result = this.sectionRepository.findOne(Id);
		return result;
	}
	
	public Section save(Section section){			
		Assert.notNull(section);
		Section result;
		result = this.sectionRepository.save(section);
		return result;
	}
	
//	private TutorialService tutorialService;
	public void delete(Section section){		
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		this.sectionRepository.delete(section);
	}
	
	//Other business methods -----
	
	
}