package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.LoginService;
import security.UserAccount;
import domain.Warranty;


@Service
@Transactional
public class WarrantyService {

	//Managed Repository -----
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Simple CRUD methods -----
	public Warranty create(){
		Warranty res = new Warranty();
		res.setLaws(new ArrayList<String>());
		return res;
	}
	
	public Collection<Warranty> findAll(){
		return warrantyRepository.findAll();
	}
	
	public Warranty findOne(int Id){
		return warrantyRepository.findOne(Id);
	}
	
	public Warranty save(Warranty w){
		Warranty saved;
		saved = warrantyRepository.save(w);
		return saved;
	}
	
	public void delete(Warranty a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		warrantyRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}