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

public class CustomerEndorsementTest extends AbstractTest{
	
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
			CustomerEndorsement customerEndorsement, saved;
			super.authenticate("customer2");						
			customerEndorsement = customerEndorsementService.findOne(15789);				
			customerEndorsement.setText("Texto de prueba 2");	

			saved = customerEndorsementService.save(customerEndorsement);				

			super.authenticate(null);
		}
		
		// DELETE ---------------------------------------------------------------------

		@Test 
		public void testDeleteCustomerEndorsements(){
			CustomerEndorsement customerEndorsement;
			Collection<CustomerEndorsement> customerEndorsements;
			super.authenticate("customer1");								

			customerEndorsement = customerEndorsementService.findOne(15790);			

			customerEndorsementService.delete(customerEndorsement);									
			customerEndorsements = customerEndorsementService.findAll();						
			Assert.isTrue(!customerEndorsements.contains(customerEndorsement));								
			super.authenticate(null);
		}
}
