package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import security.LoginService;
import security.UserAccount;
import domain.Tutorial;


@Service
@Transactional
public class TutorialService {

	//Managed Repository -----
	@Autowired
	private TutorialRepository tutorialRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public TutorialService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Tutorial create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Tutorial res = new Tutorial();
		return res;
	}
	
	public Collection<Tutorial> findAll(){
		return tutorialRepository.findAll();
	}
	
	public Tutorial findOne(int Id){
		return tutorialRepository.findOne(Id);
	}
	
	public Tutorial save(Tutorial a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		tutorialRepository.save(a);
		return a;
	}
	
	public void delete(Tutorial a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		tutorialRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}