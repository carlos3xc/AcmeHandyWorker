package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerEndorsementRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorkerEndorsement;


@Service
@Transactional
public class HandyWorkerEndorsementService {

	//Managed Repository -----
	@Autowired
	private HandyWorkerEndorsementRepository handyWorkerEndorsementRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public HandyWorkerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public HandyWorkerEndorsement create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		HandyWorkerEndorsement res = new HandyWorkerEndorsement();
		return res;
	}
	
	public Collection<HandyWorkerEndorsement> findAll(){
		return handyWorkerEndorsementRepository.findAll();
	}
	
	public HandyWorkerEndorsement findOne(int Id){
		return handyWorkerEndorsementRepository.findOne(Id);
	}
	
	public HandyWorkerEndorsement save(HandyWorkerEndorsement a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		handyWorkerEndorsementRepository.save(a);
		return a;
	}
	
	public void delete(HandyWorkerEndorsement a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		handyWorkerEndorsementRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}