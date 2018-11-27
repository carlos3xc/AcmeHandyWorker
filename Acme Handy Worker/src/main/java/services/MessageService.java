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
import domain.Administrator;
import domain.Application;
import domain.Box;
import domain.Message;


@Service
@Transactional
public class MessageService {

	//Managed Repository -----
	@Autowired
	private MessageRepository messageRepository;
	
	//Supporting Services -----
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private BoxService boxService;
	
	//Constructors -----
	public MessageService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Message create(Actor sender, Actor recipient){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		
		Date d = new Date();// se asigna la fecha del milisegundo en la que fue creada se le restan unos cuantos milisegundos para que no de problemas
		d.setTime(d.getTime()-1000);//se le resta 1 segundo.
		
		Message res = new Message();
		res.setBody("");
		res.setFlagSpam(false);
		res.setMoment(d);
		res.setPriority("NEUTRAL");
		res.setRecipient(recipient);
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
		
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		messageRepository.save(a);
		return a;
	}
	
	public void delete(Message m){

		//El mensaje se movera a la trashbox, si el mensaje ya estaba en la trashbox se elimina del sistema.
		Assert.isTrue(true);
		UserAccount userAccount = LoginService.getPrincipal();
		Actor r  = m.getRecipient();
		Actor s = m.getSender();
		Actor logged = null;
		if (r.getUserAccount().equals(userAccount)){
			logged = r;
		}
		if(s.getUserAccount().equals(userAccount)){
			logged = s;
		}
		Box trash = null;
		Collection <Box> loggedBoxes = boxService.findByActorId(logged.getId());
		Collection <Box> otherboxes = new ArrayList<Box>();
		
		for (Box box : loggedBoxes) {
			if (box.getName().equals("trashbox"))
				trash = box;
			else{
				otherboxes.add(box);
			}
		}

		if(trash.getMessages().contains(m)){
			Collection<Message> aux = trash.getMessages();
			aux.remove(m);
			messageRepository.delete(m);
			boxService.save(trash);
		}else{
			for (Box b : otherboxes) {
				if(b.getMessages().contains(m)){
					Collection<Message> aux = b.getMessages();
					aux.remove(m);
					Collection<Message> t = trash.getMessages();
					t.add(m);
					
					boxService.save(trash);
					boxService.save(b);
			}
		}	
	}

		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		
	}
	
	//Other business methods -----
	
	public void sendSystemMessages(Application a){
		Administrator sender = (Administrator) adminService.findAll().toArray()[0];
		Actor hw = a.getHandyWorker();
		Actor  customer =  a.getFixUpTask().getCustomer();
		Message m1 = this.create(sender,hw);
		Message m2 = this.create(sender, customer);
	
		m1.setBody("The status of the fix-up Task described as: \n"
				+ a.getFixUpTask().getDescription()
				+ "\n has changed you shoud revise it in the system. \n\n\n" +

				"El estado de la tarea de arreglo descrita como:\n"
				+ a.getFixUpTask().getDescription()
				+ "\n ha cambiado deberia revisar los cambios en el sistema.");

		m2.setBody("The status of the fix-up Task described as: \n"
				+ a.getFixUpTask().getDescription()
				+ "\n has changed you shoud revise it in the system. \n\n\n" +

				"El estado de la tarea de arreglo descrita como:\n"
				+ a.getFixUpTask().getDescription()
				+ "\n ha cambiado deberia revisar los cambios en el sistema.");

		this.save(m1);
		this.save(m2);
		
		Collection<Box> senderBoxes = boxService.findByActorId(sender.getId());
		Collection<Box> hwBoxes = boxService.findByActorId(hw.getId());
		Collection<Box> customerBoxes = boxService.findByActorId(customer.getId());
		
		for (Box b : hwBoxes) {
			if(b.getName().equals("inbox")){
				boxService.addMessageToBox(b, m1);
			}
		}
		
		for (Box b: customerBoxes){
			if(b.getName().equals("inbox")){
				boxService.addMessageToBox(b, m2);
			}
		}
		
		for (Box b: senderBoxes){
			if(b.getName().equals("outbox")){
				boxService.addMessageToBox(b, m2);
				boxService.addMessageToBox(b, m1);
			}
		}	
	}
	
	public void addMesageToBoxes(Message m){
		// No se hara en el momento de la creacion si no a posteriori 
		// cuando el mensaje este completamente escrito para evitar
		// guardar un mensaje diferente en la Box que lo que se queria mandar.
		
		Actor r = m.getRecipient();
		Actor s = m.getSender();
		
		Collection<Box> recieverBoxes = boxService.findByActorId(r.getId());
		Collection<Box> senderBoxes = boxService.findByActorId(s.getId());
		
		for (Box b : recieverBoxes) {
			if(b.getName().equals("inbox")){
				boxService.addMessageToBox(b, m);
			}
		}
		
		for (Box b : senderBoxes) {
			if(b.getName().equals("outbox")){
				boxService.addMessageToBox(b, m);
			}
		}
	}
	
}