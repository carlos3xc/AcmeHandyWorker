package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.FixUpTask;
import domain.SocialProfile;


@Service
@Transactional
public class CustomerService {

	//Managed Repository -----
	@Autowired
	private CustomerRepository customerRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	public Customer create(){
		Customer res = new Customer();
		res.setFixUpTasks(new ArrayList<FixUpTask>());
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		return res;
	}
	
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Customer findOne(int Id){
		return customerRepository.findOne(Id);
	}
	
	public Customer save(Customer c){
		Customer saved;
		Collection<Customer> customers;
		if(c.getId()==0){ c.setIsBanned(false); c.setIsSuspicious(false);  }
		saved = customerRepository.save(c);
		customers = customerRepository.findAll();
		Assert.isTrue(customers.contains(saved));
		return saved;
	}
/*	
	public void delete(Customer a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		customerRepository.delete(a);
	}*/
	
	//Other business methods -----
	
	public Customer findByUserAccountId(Integer Id){
		Customer c;
		c = customerRepository.findByUserAccountId(Id);
		return c;
	}
	
	public Map<Customer,Integer> TopThreeInComplaints(){
		List<Object> top;
	//	List<Customer> customers = new ArrayList<Customer>();
//		Collection<Integer> complaintsNumber = new ArrayList<Integer>();
		Map<Customer,Integer> n = new HashMap<Customer,Integer>();
		top = (List<Object>) customerRepository.TopThreeInComplaints();
		for(int i=0;i<=top.size();i=i+2){
			n.put((Customer)top.get(i),(Integer)top.get(i+1));
		}
		System.out.println(n);
		return n;
	}
	
}