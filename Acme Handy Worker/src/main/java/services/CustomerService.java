package services;

import java.util.ArrayList;
import java.util.Collection;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;


@Service
@Transactional
public class CustomerService {

	//Managed Repository -----
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	public Customer create(){

		Authority authority = new Authority();
		authority.setAuthority("CUSTOMER");

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		Customer customer = new Customer();
		customer.setUserAccount(user);
		customer.setSocialProfiles(new ArrayList<SocialProfile>());
		customer.setFixUpTasks(new ArrayList<FixUpTask>());
		customer.setIsBanned(false);
		customer.setIsSuspicious(false);

		/*Control Check*/
		customer.setQuolets(new ArrayList<Quolet>());

		return customer;
	}
	
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public Customer findOne(int Id){
		return customerRepository.findOne(Id);
	}
	
	public Customer save(Customer c){
		Customer result;
		
		result = customerRepository.saveAndFlush(c);
		boxService.createSystemBoxes(result);
		
		return result;
		
	}

	
	//Other business methods -----
	
	public Customer findByUserAccountId(Integer Id){
		Customer c;
		c = customerRepository.findByUserAccountId(Id);
		return c;
	}

	public Customer findByPrincipal(){
		return findByUserAccountId(LoginService.getPrincipal().getId());
	}
	
	public Collection<Customer> getCustomersByHandyWorkerTasks(int handyWorkerId){
		Collection<Customer> res;
		res = customerRepository.getCustomersByHandyWorkerTasks(handyWorkerId);
		return res;
	}
	
	public Collection<Customer> TopThreeInComplaints(){
//		List<Customer> top = new ArrayList<>();
//		Map<Customer, Integer> numberofcomplaints = new HashMap<>();
//		
//		for (Customer cu : this.findAll()) {
//			numberofcomplaints.put(cu, 0);
//		}
//		
//		for (Complaint c : complaintService.findAll()) {
//			numberofcomplaints.put(c.getCustomer(), numberofcomplaints.get(c.getCustomer())+1);
//		}
//		
//		for (Entry<Customer, Integer> entry : numberofcomplaints.entrySet()) {
//			
//			
//		}
		Collection<Customer> top = customerRepository.topThreeInComplaints();

		return top;
	}
	
	public Collection<Customer> getCustomersWMoreTasksThanAvg(){
		Collection<Customer> res;
		res = customerRepository.getCustomersWMoreTasksThanAvg();
		return res;
	}
	
}