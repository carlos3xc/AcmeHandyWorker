package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanPhaseRepository;
import security.LoginService;
import security.UserAccount;
import domain.WorkPlanPhase;


@Service
@Transactional
public class WorkPlanPhaseService {

	//Managed Repository -----
	@Autowired
	private WorkPlanPhaseRepository workPlanPhaseRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Simple CRUD methods -----
	public WorkPlanPhase create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario a�adir atributos consistentes con la entity.
		WorkPlanPhase res = new WorkPlanPhase();
		return res;
	}
	
	public Collection<WorkPlanPhase> findAll(){
		return workPlanPhaseRepository.findAll();
	}
	
	public WorkPlanPhase findOne(int Id){
		return workPlanPhaseRepository.findOne(Id);
	}
	
	public WorkPlanPhase save(WorkPlanPhase a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el due�o
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		workPlanPhaseRepository.save(a);
		return a;
	}
	
	public void delete(WorkPlanPhase wp){
		
		workPlanPhaseRepository.delete(wp);
	}
	
	//Other business methods -----
	
	public WorkPlanPhase findByFixUpTaskId(Integer Id){
		WorkPlanPhase res;
		res = workPlanPhaseRepository.findByFixUpTaskId(Id);
		return res;
	}
	
	
}