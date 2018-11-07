package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardMakeRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCardMake;


@Service
@Transactional
public class CreditCardMakeService {

	//Managed Repository -----
	@Autowired
	private CreditCardMakeRepository creditCardMakeRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public CreditCardMakeService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CreditCardMake create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		CreditCardMake res = new CreditCardMake();
		return res;
	}
	
	public Collection<CreditCardMake> findAll(){
		return creditCardMakeRepository.findAll();
	}
	
	public CreditCardMake findOne(int Id){
		return creditCardMakeRepository.findOne(Id);
	}
	
	public CreditCardMake save(CreditCardMake a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		creditCardMakeRepository.save(a);
		return a;
	}
	
	public void delete(CreditCardMake a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		creditCardMakeRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}