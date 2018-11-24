package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;


@Service
@Transactional
public class HandyWorkerService {

	//Managed Repository -----
	@Autowired
	private HandyWorkerRepository handyWorkerRepository;
	
	//Supporting Services -----
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	//Constructors -----
	public HandyWorkerService(){
		super();
	}
	
	//Simple CRUD methods -----
	public HandyWorker create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		HandyWorker res = new HandyWorker();
		return res;
	}
	
	public Collection<HandyWorker> findAll(){
		return handyWorkerRepository.findAll();
	}
	
	public HandyWorker findOne(int Id){
		return handyWorkerRepository.findOne(Id);
	}
	
	public HandyWorker save(HandyWorker a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		handyWorkerRepository.save(a);
		return a;
	}
	
	public void delete(HandyWorker a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		handyWorkerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = handyWorkerRepository.findByPrincipal(userAccount.getId());
		return hw;
	}
	
	
}