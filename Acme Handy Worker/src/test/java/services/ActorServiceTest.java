package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.SocialProfile;

import security.UserAccount;
import security.UserAccountService;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService actorService;

	@Autowired
	private UserAccountService userAccountService;

	@Test
	public void testUpdate() {

		authenticate("handyworker3");

		Actor actor = actorService.findOne(14578);
		Actor result;

		actor.setName("Nombre modificado");
		actor.setSurname("Primer apellido modificado");
		actor.setMiddleName("Segundo apellido modificado");
		actor.setAddress("Direccion modificada");
		actor.setEmail("correomodificado@gmail.com");
		actor.setPhone("666444333");
		actor.setIsBanned(false);
		actor.setIsSuspicious(false);

		SocialProfile socialProfile = new SocialProfile();
		socialProfile.setNick("nick");
		socialProfile.setLink("https://www.google.es/twitter");
		socialProfile.setSocialNetwork("socialNetwork");

		actor.getSocialProfiles().add(socialProfile);

		UserAccount userAccount = userAccountService.findOne(14561);
		actor.setUserAccount(userAccount);

		result = actorService.save(actor);
		Assert.isTrue(actorService.findAll().contains(result));
		
		unauthenticate();
	}
	
	@Test
	public void testRegisterHandyWorker(){
		actorService.registerHandyWorker();
	}
	
	@Test
	public void testRegisterCutomer(){
		actorService.registerCustomer();
	}
	
	@Test
	public void testRegisterSponsor(){
		actorService.registerSponsor();
	}
}
