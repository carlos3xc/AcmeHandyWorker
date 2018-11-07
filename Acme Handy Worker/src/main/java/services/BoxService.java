package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;


@Service
@Transactional
public class BoxService {

	//Managed Repository -----
	@Autowired
	private BoxRepository boxRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public BoxService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Box create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Box res = new Box();
		return res;
	}
	
	public Collection<Box> findAll(){
		return boxRepository.findAll();
	}
	
	public Box findOne(int Id){
		return boxRepository.findOne(Id);
	}
	
	public Box save(Box a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		boxRepository.save(a);
		return a;
	}
	
	public void delete(Box a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		boxRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}