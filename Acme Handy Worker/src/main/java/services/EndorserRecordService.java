package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.EndorserRecord;


@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public EndorserRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public EndorserRecord create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		EndorserRecord res = new EndorserRecord();
		return res;
	}
	
	public Collection<EndorserRecord> findAll(){
		return endorserRecordRepository.findAll();
	}
	
	public EndorserRecord findOne(int Id){
		return endorserRecordRepository.findOne(Id);
	}
	
	public EndorserRecord save(EndorserRecord a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		endorserRecordRepository.save(a);
		return a;
	}
	
	public void delete(EndorserRecord a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		endorserRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}