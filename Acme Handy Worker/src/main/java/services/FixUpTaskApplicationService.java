package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.FixUpTaskApplication;


@Service
@Transactional
public class FixUpTaskApplicationService {

	//Managed Repository -----
	@Autowired
	private FixUpTaskApplicationRepository fixUpTaskApplicationRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public FixUpTaskApplicationService(){
		super();
	}
	
	//Simple CRUD methods -----
	public FixUpTaskApplication create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		FixUpTaskApplication res = new FixUpTaskApplication();
		return res;
	}
	
	public Collection<FixUpTaskApplication> findAll(){
		return fixUpTaskApplicationRepository.findAll();
	}
	
	public FixUpTaskApplication findOne(int Id){
		return fixUpTaskApplicationRepository.findOne(Id);
	}
	
	public FixUpTaskApplication save(FixUpTaskApplication a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		fixUpTaskApplicationRepository.save(a);
		return a;
	}
	
	public void delete(FixUpTaskApplication a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		fixUpTaskApplicationRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}