package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Box;
import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class BoxServiceTest extends AbstractTest {

	@Autowired
	private BoxService boxService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MessageService messageService;

	@Test
	public void testCreate() {

		authenticate("admin1"); // 14570

		Actor actor = actorService.findOne(14570);

		Box box = boxService.create(actor);

		Assert.isTrue(box.getActor().equals(actor));
		Assert.isTrue(box.getSystemBox().equals(false));
		Assert.isTrue(box.getMessages().isEmpty());
		Assert.isTrue(box.getName() != "");

		unauthenticate();
	}

	@Test
	public void testSave() {

		authenticate("admin1"); // 14570

		Actor actor = actorService.findOne(14570);

		Box result;
		Box box = boxService.create(actor);

		box.setName("Name box");

		result = boxService.save(box);

		Assert.isTrue(boxService.findByActorId(14570).contains(result));
		Assert.isTrue(boxService.findAll().contains(result));
	}

	@Test
	public void testDelete() {
		authenticate("admin1"); // 14570

		Actor actor = actorService.findOne(14570);

		Box result;
		Box box = boxService.create(actor);

		box.setName("Name box");

		result = boxService.save(box);

		Assert.isTrue(boxService.findByActorId(14570).contains(result));
		Assert.isTrue(boxService.findAll().contains(result));

		boxService.delete(result);

		Assert.isTrue(!boxService.findByActorId(14570).contains(result));
		Assert.isTrue(!boxService.findAll().contains(result));
	}

	@Test
	public void testCreateUserBox() {

		authenticate("admin1"); // 14570

		Actor actor = actorService.findOne(14570);

		Box box = boxService.createUserBox(actor);

		Assert.isTrue(box.getName().equals(actor.getName()));

	}
	
	@Test
	public void testMessageToBox(){
		
		Actor sender = actorService.findOne(14580); // HandyWorker5
		Actor recipient = actorService.findOne(14570); // Admin1
		
		Box box = boxService.create(recipient);
		
		Message message = messageService.create(sender, recipient);
		
		boxService.addMessageToBox(box, message);
		
		Assert.isTrue(box.getMessages().contains(message));
	}
}
