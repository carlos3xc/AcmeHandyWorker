package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;


@Service
@Transactional
public class CurriculaService {

	//Managed Repository -----
	@Autowired
	private CurriculaRepository curriculaRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public CurriculaService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Curricula create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Curricula res = new Curricula();
		return res;
	}
	
	public Collection<Curricula> findAll(){
		return curriculaRepository.findAll();
	}
	
	public Curricula findOne(int Id){
		return curriculaRepository.findOne(Id);
	}
	
	public Curricula save(Curricula a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		curriculaRepository.save(a);
		return a;
	}
	
	public void delete(Curricula a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		curriculaRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}