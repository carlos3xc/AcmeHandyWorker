package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.LoginService;
import security.UserAccount;
import domain.Section;


@Service
@Transactional
public class SectionService {

	//Managed Repository -----
	@Autowired
	private SectionRepository sectionRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public SectionService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Section create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Section res = new Section();
		return res;
	}
	
	public Collection<Section> findAll(){
		return sectionRepository.findAll();
	}
	
	public Section findOne(int Id){
		return sectionRepository.findOne(Id);
	}
	
	public Section save(Section a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		sectionRepository.save(a);
		return a;
	}
	
	public void delete(Section a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		sectionRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}