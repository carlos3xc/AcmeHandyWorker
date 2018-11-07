package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Message;


@Service
@Transactional
public class MessageService {

	//Managed Repository -----
	@Autowired
	private MessageRepository messageRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public MessageService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Message create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Message res = new Message();
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
	
	
}