package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsorship;


@Service
@Transactional
public class SponsorshipService {

	//Managed Repository -----
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public SponsorshipService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Sponsorship create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Sponsorship res = new Sponsorship();
		return res;
	}
	
	public Collection<Sponsorship> findAll(){
		return sponsorshipRepository.findAll();
	}
	
	public Sponsorship findOne(int Id){
		return sponsorshipRepository.findOne(Id);
	}
	
	public Sponsorship save(Sponsorship a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		sponsorshipRepository.save(a);
		return a;
	}
	
	public void delete(Sponsorship a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		sponsorshipRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}