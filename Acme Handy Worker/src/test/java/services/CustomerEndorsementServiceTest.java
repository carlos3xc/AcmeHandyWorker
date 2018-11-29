package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})

public class CustomerEndorsementServiceTest extends AbstractTest{
	
	// Service under test ---------------------------------------------------------	
		@Autowired
		private CustomerEndorsementService customerEndorsementService;
		
		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private HandyWorkerService handyWorkerService;
		
		
		// Tests ----------------------------------------------------------------------
		
		// CREATE ---------------------------------------------------------------------
		
		@Test
		public void testCreateCustomerEndorsementService(){
			CustomerEndorsement customerEndorsement;
			super.authenticate("customer1");
			customerEndorsement = customerEndorsementService.create();
			Assert.isNull(customerEndorsement.getText());
			Assert.isNull(customerEndorsement.getCustomer());
			Assert.isNull(customerEndorsement.getHandyWorker());
			super.authenticate(null);
		}
		
		
		// SAVE -----------------------------------------------------------------------
		
		@Test 
		public void testSaveCustomerEndorsement(){
			CustomerEndorsement customerEndorsement, saved;
			HandyWorker hw;
			Customer c;
			Collection<CustomerEndorsement> customerEndorsements;
			super.authenticate("customer1");						
			customerEndorsement = customerEndorsementService.create();					
			hw = handyWorkerService.findOne(15728);
			c = customerService.findOne(15722);
			
			Date current = new Date(System.currentTimeMillis() - 1000);
			
			customerEndorsement.setMoment(current);
			customerEndorsement.setText("Esto es un texto de prueba");
			customerEndorsement.setHandyWorker(hw);
			customerEndorsement.setCustomer(c);
			saved = customerEndorsementService.save(customerEndorsement);					

			customerEndorsements = customerEndorsementService.findAll();				

			Assert.isTrue(customerEndorsements.contains(saved));
			super.authenticate(null);
		}
		

		// UPDATE ---------------------------------------------------------------------
		
		@Test 
		public void testUpdateCustomerEndorsement(){
			CustomerEndorsement customerEndorsement = new CustomerEndorsement();
			super.authenticate("customer2");						
			Collection<CustomerEndorsement> all;
			Collection<HandyWorker> workers;
			HandyWorker haw = new HandyWorker();

			all = customerEndorsementService.findAll();
			for(CustomerEndorsement ca: all){
				if(ca.getText().equals("Es muy puntual")) customerEndorsement=ca;
			}
			workers = handyWorkerService.findAll();
			for(HandyWorker w: workers){
				if(w.getMake().equals("Juan Parra")) haw=w;
				break;
			}
			customerEndorsement.setHandyWorker(haw);
			customerEndorsement.setText("Texto de prueba 2");	

			customerEndorsementService.save(customerEndorsement);				

			super.authenticate(null);
		}
		
		// DELETE ---------------------------------------------------------------------

		@Test 
		public void testDeleteCustomerEndorsements(){
			CustomerEndorsement customerEndorsement; //= new CustomerEndorsement();
			Collection<CustomerEndorsement> customerEndorsements;
		//	Collection<CustomerEndorsement> all;
			super.authenticate("customer1");								

		/*	all = customerEndorsementService.findAll();
			for(CustomerEndorsement ca: all){
				if(ca.getText().equals("Es muy perfeccionista")){
					customerEndorsement=ca;
					break;
				}
	
			}
			System.out.println(customerEndorsement);*/
			customerEndorsement = customerEndorsementService.findOne(15788);
			customerEndorsementService.delete(customerEndorsement);									
			customerEndorsements = customerEndorsementService.findAll();						
			Assert.isTrue(!customerEndorsements.contains(customerEndorsement));								
			super.authenticate(null);
		}
}
