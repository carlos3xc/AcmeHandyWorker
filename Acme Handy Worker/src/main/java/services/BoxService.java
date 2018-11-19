package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
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
	public Box create(Actor a){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Box res = new Box();
		res.setName("");
		res.setSystemBox(false);
		res.setActor(a);
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
		//comprobar que el box no sea system box antes de borrarlo.
		Assert.isTrue(!b.getSystemBox());
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		// comprobar el dueño.
		Assert.isTrue(b.getActor().getUserAccount().equals(userAccount));
		
		boxRepository.delete(b);
	}
	
	//Other business methods -----
	
	public Collection<Box> findByActorId (Integer actorId){
		return boxRepository.findByActorId(actorId);
	}
	
	public Box createUserBox(Actor a, String name){
		Box res = this.create(a);
		res.setName(name);
		this.save(res);
		
		return res;
	}
	public void addMessageToBox(Box b, Message s){
		List<Message> aux = new ArrayList<>(b.getMessages());
		aux.add(0,s); // los mensajes nuevos siempre se ponen primero.
		b.setMessages(aux);
		
		this.save(b);
		
	}
	
	
}