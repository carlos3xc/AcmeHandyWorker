package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import repositories.ComplaintRepository;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;


@Service
@Transactional
public class ComplaintService {

	//Managed Repository -----
	@Autowired
	private ComplaintRepository complaintRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public ComplaintService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Complaint create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Complaint res = new Complaint();
		return res;
	}
	
	public Collection<Complaint> findAll(){
		return complaintRepository.findAll();
	}
	
	public Complaint findOne(int Id){
		return complaintRepository.findOne(Id);
	}
	
	public Complaint save(Complaint a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		complaintRepository.save(a);
		return a;
	}
	
	public void delete(Complaint a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		complaintRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}