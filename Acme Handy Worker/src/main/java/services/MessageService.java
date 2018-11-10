package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Message;


@Service
@Transactional
public class MessageService {

	//Managed Repository -----
	@Autowired
	private MessageRepository messageRepository;
	
	//Supporting Services -----
	
	@Autowired
	private ActorService actorService;
	
	//Constructors -----
	public MessageService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Message create(Actor sender, Actor reciever){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		
		Date d = new Date();// se asigna la fecha del milisegundo en la que fue creada se le restan unos cuantos milisegundos para que no de problemas
		d.setTime(d.getTime()-1000);//se le resta 1 segundo.
		
		Message res = new Message();
		res.setBody("");
		res.setFlagSpam(false);
		res.setMoment(d);
		res.setPriority("NEUTRAL");
		res.setReceiver(reciever);
		res.setSender(sender);
		res.setSubject("");
		res.setTags(new ArrayList<String>());
		return res;
	}
	
	public Collection<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public Message findOne(int Id){
		return messageRepository.findOne(Id);
	}
	
	public Message save(Message a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		messageRepository.save(a);
		return a;
	}
	
	public void delete(Message a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		messageRepository.delete(a);
	}
	
	//Other business methods -----
	
	public void sendSystemMessages(Application a){
		Message m1 = this.create((Actor)actorService.findAdministrators().toArray()[0],a.getHandyWorker());
		Message m2 = this.create((Actor)actorService.findAdministrators().toArray()[0],a.getFixUpTask().getCustomer());
	
		this.save(m1);
		this.save(m2);
	}
}