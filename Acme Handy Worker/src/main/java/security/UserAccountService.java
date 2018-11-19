package security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



@Service
@Transactional
public class UserAccountService {

	//Managed Repository -----
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public UserAccountService(){
		super();
	}
	
	//Simple CRUD methods -----
	public UserAccount create(String username, String hashedPassword, String authority){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Authority auth = new Authority();
		auth.setAuthority(authority);
		List<Authority> auths = new ArrayList<>();
		auths.add(auth);
		
		
		UserAccount res = new UserAccount();
		res.setAuthorities(auths);
		res.setUsername(username);
		res.setPassword(hashedPassword);
		return res;
	}
	
	public Collection<UserAccount> findAll(){
		return userAccountRepository.findAll();
	}
	
	public UserAccount findOne(int Id){
		return userAccountRepository.findOne(Id);
	}
	
	public UserAccount save(UserAccount a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		userAccountRepository.save(a);
		return a;
	}
	
	public void delete(UserAccount a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		userAccountRepository.delete(a);
	}
	
	//Other business methods -----
	public UserAccount register (String username, String hashedPassword, String type){
		//type can be either CUSTOMER, HANDYWORKER or SPONSOR(select)
		UserAccount nueva = this.create(username, hashedPassword, type);
		this.save(nueva);
		return nueva;
	}
	
}