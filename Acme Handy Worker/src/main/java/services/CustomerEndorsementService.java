package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerEndorsementRepository;
import security.LoginService;
import security.UserAccount;
import domain.CustomerEndorsement;


@Service
@Transactional
public class CustomerEndorsementService {

	//Managed Repository -----
	@Autowired
	private CustomerEndorsementRepository customerEndorsementRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public CustomerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public CustomerEndorsement create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		CustomerEndorsement res = new CustomerEndorsement();
		return res;
	}
	
	public Collection<CustomerEndorsement> findAll(){
		return customerEndorsementRepository.findAll();
	}
	
	public CustomerEndorsement findOne(int Id){
		return customerEndorsementRepository.findOne(Id);
	}
	
	public CustomerEndorsement save(CustomerEndorsement a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerEndorsementRepository.save(a);
		return a;
	}
	
	public void delete(CustomerEndorsement a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerEndorsementRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}