package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.CustomerEndorsement;
import domain.HandyWorker;
import domain.HandyWorkerEndorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})

@Transactional
public class HandyWorkerEndosementTest extends AbstractTest{
	
	// Service under test ---------------------------------------------------------	
			@Autowired
			private HandyWorkerEndorsementService handyWorkerEndorsementService;
			
			@Autowired
			private CustomerService customerService;
			
			@Autowired
			private HandyWorkerService handyWorkerService;
			
			
			// Tests ----------------------------------------------------------------------
			
			// CREATE ---------------------------------------------------------------------
			
			@Test
			public void testCreateHandyWorkerEndorsementService(){
				HandyWorkerEndorsement handyWorkerEndorsement;
				super.authenticate("handyworker1");
				handyWorkerEndorsement = handyWorkerEndorsementService.create();
				Assert.isNull(handyWorkerEndorsement.getMoment());
				Assert.isNull(handyWorkerEndorsement.getText());
				Assert.isNull(handyWorkerEndorsement.getCustomer());
				Assert.isNull(handyWorkerEndorsement.getHandyWorker());
				super.authenticate(null);
			}
			
			
			// SAVE -----------------------------------------------------------------------
			
			@Test 
			public void testSaveHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement, saved;
				HandyWorker hw;
				Customer c;
				Collection<HandyWorkerEndorsement> handyWorkerEndorsements;
				super.authenticate("handyworker1");						
				handyWorkerEndorsement = handyWorkerEndorsementService.create();					
				hw = handyWorkerService.findOne(14577);
				c = customerService.findOne(14572);
				
				Date current = new Date(System.currentTimeMillis() - 1000);
				
				handyWorkerEndorsement.setMoment(current);
				handyWorkerEndorsement.setText("Esto es un texto de prueba");
				handyWorkerEndorsement.setHandyWorker(hw);
				handyWorkerEndorsement.setCustomer(c);
				
				saved = handyWorkerEndorsementService.save(handyWorkerEndorsement);					

				handyWorkerEndorsements = handyWorkerEndorsementService.findAll();				

				Assert.isTrue(handyWorkerEndorsements.contains(saved));
				super.authenticate(null);
			}
			

			// UPDATE ---------------------------------------------------------------------
			
			@Test 
			public void testUpdateHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement, saved;
				super.authenticate("handyworker2");						
				handyWorkerEndorsement = handyWorkerEndorsementService.findOne(14641);				
				handyWorkerEndorsement.setText("Texto de prueba 2");	

				saved = handyWorkerEndorsementService.save(handyWorkerEndorsement);				

				super.authenticate(null);
			}
			
			// DELETE ---------------------------------------------------------------------

			@Test 
			public void testDeleteHandyWorkerEndorsement(){
				HandyWorkerEndorsement handyWorkerEndorsement;
				Collection<HandyWorkerEndorsement> handyWorkerEndorsements;
				super.authenticate("handyworker3");								

				handyWorkerEndorsement = handyWorkerEndorsementService.findOne(14643);			

				handyWorkerEndorsementService.delete(handyWorkerEndorsement);									
				handyWorkerEndorsements = handyWorkerEndorsementService.findAll();						
				Assert.isTrue(!handyWorkerEndorsements.contains(handyWorkerEndorsement));								
				super.authenticate(null);
			}

}
