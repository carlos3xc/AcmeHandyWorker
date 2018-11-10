package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Message;


@Service
@Transactional
public class BoxService {

	//Managed Repository -----
	@Autowired
	private BoxRepository boxRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ActorService actorService; 
	
	//Constructors -----
	public BoxService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Box create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Box res = new Box();
		res.setName("");
		res.setSystemBox(false);
		res.setActor(actorService.create());
		res.setMessages(new ArrayList<Message>());
		return res;
	}
	
	public Collection<Box> findAll(){
		return boxRepository.findAll();
	}
	
	public Box findOne(int Id){
		return boxRepository.findOne(Id);
	}
	
	public Box save(Box b){
		
		//Assert.isTrue(b.getSystemBox().equals(false));
		
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(b.getActor().getUserAccount().equals(userAccount));
		
		boxRepository.save(b);
		return b;
	}
	
	public void delete(Box b){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		Assert.isTrue(b.getActor().getUserAccount().equals(userAccount));
		
		boxRepository.delete(b);
	}
	
	//Other business methods -----
	
	public Collection<Box> findByActorId (Integer actorId){
		return boxRepository.findByActorId(actorId);
	}
	
}