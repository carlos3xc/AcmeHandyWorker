package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;
import domain.Sponsor;


@Service
@Transactional
public class ActorService {

	//Managed Repository -----
	@Autowired
	private ActorRepository actorRepository;
	
	//Supporting Services -----
	
	@Autowired
	private BoxService boxService;
	
	//Constructors -----
	public ActorService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Actor create(UserAccount ua){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Actor res = new Actor();
		res.setName("");
		res.setSurname("");
		res.setMiddleName("");
		res.setPhoto("http://www.aphoto.com");
		res.setEmail("email@domain.com");
		res.setPhone("+34678123123");
		res.setAddress("");
		res.setIsSuspicious(false);
		res.setIsBanned(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		//TODO: puede que no sea valido por no tener username y password en el constructor de userAccount
		res.setUserAccount(ua);
		this.createSystemBoxes(res);
		
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
		Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		actorRepository.save(a);
		return a;
	}
	
	public void delete(Actor a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		actorRepository.delete(a);
	}
	
	//Other business methods -----
	public Actor getByUserAccountId(Integer uaId){
		return actorRepository.findByUserAccountId(uaId);
	}
	
	private void createSystemBoxes(Actor a){
		Box inbox, outbox, spambox, trashbox;
		inbox = boxService.create(a);
		outbox = boxService.create(a);
		spambox = boxService.create(a);
		trashbox = boxService.create(a);
		
		inbox.setName("inbox");
		outbox.setName("outbox");
		spambox.setName("spambox");
		trashbox.setName("trashbox");
		
		inbox.setSystemBox(true);
		outbox.setSystemBox(true);
		spambox.setSystemBox(true);
		trashbox.setSystemBox(true);
		
		boxService.save(inbox);
		boxService.save(outbox);
		boxService.save(spambox);
		boxService.save(trashbox);
		
	}
	
	public Collection<Actor> findAdministrators(){
		return actorRepository.findAdministrators();
	}
	
	public void register(String type,UserAccount ua){
		//Solo pueden registrarse nuevos actores como customer o como HandyWorker.
		Actor nuevo = this.create(ua);
		
		if (type.equals("CUSTOMER")){
			
			Customer aux = new Customer();
			aux = (Customer) nuevo;
		}
		if (type.equals("HANDYWORKER")){
			HandyWorker aux = new HandyWorker();
			aux =(HandyWorker) nuevo;
		}
		if (type.equals("SPONSOR")){
			Sponsor aux = new Sponsor();
			aux =(Sponsor) nuevo;
		}
		this.save(nuevo);
	}
	
	
}
