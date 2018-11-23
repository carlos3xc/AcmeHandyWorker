package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.LoginService;
import security.UserAccount;
import domain.Referee;


@Service
@Transactional
public class RefereeService {

	//Managed Repository -----
	@Autowired
	private RefereeRepository refereeRepository;
	
	//Simple CRUD methods -----
	public Referee create(){
		Referee res = new Referee();
		return res;
	}
	
	public Collection<Referee> findAll(){
		return refereeRepository.findAll();
	}
	
	public Referee findOne(int Id){
		return refereeRepository.findOne(Id);
	}
	
	public Referee save(Referee a){
		
		UserAccount userAccount = LoginService.getPrincipal();
		refereeRepository.save(a);
		return a;
	}
	
	public void delete(Referee a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		refereeRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Referee findByUserAccountId(Integer Id){
		Referee r;
		r = refereeRepository.findByUserAccountId(Id);
		return r;
	}
	
}