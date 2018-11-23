package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Referee;


@Service
@Transactional
public class CustomerService {

	//Managed Repository -----
	@Autowired
	private CustomerRepository customerRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public CustomerService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Customer create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Customer res = new Customer();
		return res;
	}
	
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Customer findOne(int Id){
		return customerRepository.findOne(Id);
	}
	
	public Customer save(Customer a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerRepository.save(a);
		return a;
	}
	
	public void delete(Customer a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Customer findByUserAccountId(Integer Id){
		Customer c;
		c = customerRepository.findByUserAccountId(Id);
		return c;
	}
	
}