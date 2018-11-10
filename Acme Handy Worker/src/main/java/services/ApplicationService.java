package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;


@Service
@Transactional
public class ApplicationService {

	//Managed Repository -----
	@Autowired
	private ApplicationRepository applicationRepository;
	
	//Supporting Services -----
	
	//@Autowired
	private MessageService messageService; 
	
	//Constructors -----
	public ApplicationService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Application create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Application res = new Application();
		return res;
	}
	
	public Collection<Application> findAll(){
		return applicationRepository.findAll();
	}
	
	public Application findOne(int Id){
		return applicationRepository.findOne(Id);
	}
	
	public Application save(Application a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//TODO:modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		applicationRepository.save(a);
		return a;
	}
	
	public void delete(Application a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		applicationRepository.delete(a);
	}
	
	//Other business methods -----
	
	public void changeStatus(Application a, String status){
		a.setStatus(status);
		messageService.sendSystemMessages(a);
		
		this.save(a);
	}
}