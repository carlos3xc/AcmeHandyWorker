package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;


@Service
@Transactional
public class AdministratorService {

	//Managed Repository -----
	@Autowired
	private AdministratorRepository administratorRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public AdministratorService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Administrator create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Administrator res = new Administrator();
		return res;
	}
	
	public Collection<Administrator> findAll(){
		return administratorRepository.findAll();
	}
	
	public Administrator findOne(int Id){
		return administratorRepository.findOne(Id);
	}
	
	public Administrator save(Administrator a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		administratorRepository.save(a);
		return a;
	}
	
	public void delete(Administrator a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		administratorRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}