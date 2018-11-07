package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;


@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -----
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public ConfigurationService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Configuration create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Configuration res = new Configuration();
		return res;
	}
	
	public Collection<Configuration> findAll(){
		return configurationRepository.findAll();
	}
	
	public Configuration findOne(int Id){
		return configurationRepository.findOne(Id);
	}
	
	public Configuration save(Configuration a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		configurationRepository.save(a);
		return a;
	}
	
	public void delete(Configuration a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		configurationRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}