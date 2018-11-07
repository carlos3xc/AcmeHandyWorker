package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {

	//Managed Repository -----
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public ProfessionalRecordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public ProfessionalRecord create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		ProfessionalRecord res = new ProfessionalRecord();
		return res;
	}
	
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int Id){
		return professionalRecordRepository.findOne(Id);
	}
	
	public ProfessionalRecord save(ProfessionalRecord a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		professionalRecordRepository.save(a);
		return a;
	}
	
	public void delete(ProfessionalRecord a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		professionalRecordRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}