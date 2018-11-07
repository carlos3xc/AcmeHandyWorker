package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WelcomeMessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.WelcomeMessage;


@Service
@Transactional
public class WelcomeMessageService {

	//Managed Repository -----
	@Autowired
	private WelcomeMessageRepository welcomeMessageRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public WelcomeMessageService(){
		super();
	}
	
	//Simple CRUD methods -----
	public WelcomeMessage create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		WelcomeMessage res = new WelcomeMessage();
		return res;
	}
	
	public Collection<WelcomeMessage> findAll(){
		return welcomeMessageRepository.findAll();
	}
	
	public WelcomeMessage findOne(int Id){
		return welcomeMessageRepository.findOne(Id);
	}
	
	public WelcomeMessage save(WelcomeMessage a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		welcomeMessageRepository.save(a);
		return a;
	}
	
	public void delete(WelcomeMessage a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		welcomeMessageRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}