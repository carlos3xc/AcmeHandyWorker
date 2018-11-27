package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;

import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})

@Transactional
public class HandyWorkerTest extends AbstractTest{
	// Service under test ---------------------------------------------------------

		@Autowired
		private HandyWorkerService handyWorkerService;
		
		@Autowired
		private SocialProfileService socialProfileService;
		// Tests ----------------------------------------------------------------------

		
		// CREATE ---------------------------------------------------------------------
		
		@Test
		public void testCreateHandyWorker(){
			HandyWorker handyWorker;
			handyWorker = handyWorkerService.create();	
			Assert.isNull(handyWorker.getAddress());
			Assert.isNull(handyWorker.getEmail());
			Assert.isNull(handyWorker.getIsBanned());
			Assert.isNull(handyWorker.getIsSuspicious());
			Assert.isNull(handyWorker.getMiddleName());
			Assert.isNull(handyWorker.getName());
			Assert.isNull(handyWorker.getPhone());
			Assert.isNull(handyWorker.getPhoto());
			Assert.isNull(handyWorker.getSurname());
			Assert.isTrue(handyWorker.getSocialProfiles().isEmpty());
			Assert.notNull(handyWorker.getUserAccount());
								
			super.authenticate(null);
		}

		
		// SAVE -----------------------------------------------------------------------
		
		@Test 
		public void testSaveHandyWorker(){
			HandyWorker handyWorker,saved;
			Collection<HandyWorker> handyWorkers;
			handyWorker = handyWorkerService.create();						
			
			handyWorker.setName("Francisco");
			handyWorker.setSurname("Cordero");
			handyWorker.setEmail("juaparser@gmail.com");
			handyWorker.setPhone("678534953");
			handyWorker.setAddress("Calle San Jacinto Nº10");
			handyWorker.setMiddleName("Parra");
			handyWorker.setPhoto("http://www.linkedIn.com");

			SocialProfile savedpr;
			SocialProfile socialProfile = socialProfileService.create();
			socialProfile.setLink("http://www.twitter.com/Fran");
			socialProfile.setNick("FranC");
			socialProfile.setSocialNetwork("Twitter");
			savedpr = socialProfileService.save(socialProfile);
			handyWorker.getSocialProfiles().add(savedpr);

			UserAccount userAccount = handyWorker.getUserAccount();
			userAccount.setUsername("handyWorker12");
			userAccount.setPassword("handyWorker12");
			handyWorker.setUserAccount(userAccount);

			saved = handyWorkerService.save(handyWorker);
			
			handyWorkers = handyWorkerService.findAll();
			//Assert.isTrue(handyWorkers.contains(saved));
			
			
			super.authenticate(null);
		}
		

		// UPDATE ---------------------------------------------------------------------

		@Test 
		public void testUpdateHandyWorker(){
			HandyWorker handyWorker, saved;
			Collection<HandyWorker> handyWorkers;
			super.authenticate("handyWorker1");						
			handyWorker = handyWorkerService.findOne(14577);
			handyWorker.setName("Roberto");
			
			saved = handyWorkerService.save(handyWorker);
			
			handyWorkers = handyWorkerService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
			//Assert.isTrue(handyWorkers.contains(saved));

			super.authenticate(null);
		}
		
		// DELETE ---------------------------------------------------------------------
}
