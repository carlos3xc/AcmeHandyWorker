package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;


@Service
@Transactional
public class ActorService {

	//Managed Repository -----
	@Autowired
	private ActorRepository actorRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public ActorService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Actor create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Actor res = new Actor();
		return res;
	}
	
	public Collection<Actor> findAll(){
		return actorRepository.findAll();
	}
	
	public Actor findOne(int Id){
		return actorRepository.findOne(Id);
	}
	
	public Actor save(Actor a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		actorRepository.save(a);
		return a;
	}
	
	public void delete(Actor a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		actorRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}
