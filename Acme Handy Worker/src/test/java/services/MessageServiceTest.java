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
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private BoxService boxService;

	@Test
	public void testCreate() {

		Actor sender = actorService.findOne(14580); // HandyWorker5
		Actor recipient = actorService.findOne(14570); // Admin1

		Message message = messageService.create(sender, recipient);

		Assert.isTrue(message.getPriority() != "");
		Assert.isTrue(message.getTags().isEmpty());
		Assert.isTrue(message.getSender().equals(sender));
		Assert.isTrue(message.getRecipient().equals(recipient));
	}

	@Test
	public void testSave() {
		Actor sender = actorService.findOne(14580); // HandyWorker5
		Actor recipient = actorService.findOne(14570); // Admin1

		Message message = messageService.create(sender, recipient);
		Message result;

		message.setBody("Cuerpo del mensaje");
		message.setSubject("Asunto del mensaje");
		message.getTags().add("tag");

		result = messageService.save(message);
		Assert.isTrue(messageService.findAll().contains(result));
	}

	@Test
	public void testDelete() {

		authenticate("customer3");

		// Message 1 -> 14793; Sender -> 14571 Customer1; Recipient -> 14572
		// Customer2
		// Message 2 -> 14794; Sender -> 14572 Customer2; Recipient -> 14571
		// Customer1
		// Message 3 -> 14795; Sender -> 14573 Customer3; Recipient -> 14576
		// HandyWorker1

		Message message = messageService.findOne(14795);
		messageService.delete(message);
		// Assert.isTrue(!messageService.findAll().contains(message) ||
		// boxService.);

		unauthenticate();
	}

	@Test
	public void testSystemMessage() {

	}

	// Administrator 1; Box 21 (Spam Box) -> 14753
	// Administrator 1; Box 22 (In Box) -> 14754
	// Administrator 1; Box 23 (Out Box) -> 14755
	// Administrator 1; Box 24 (Trash Box) -> 14756

	// Customer 5; Box 17 (Spam Box) -> 14749
	// Customer 5; Box 18 (In Box) -> 14750
	// Customer 5; Box 19 (Out Box) -> 14751
	// Customer 5; Box 20 (Trash Box) -> 14752

	@Test
	public void testMessageToBox() {

		Actor sender = actorService.findOne(14575); // Customer5
		Actor recipient = actorService.findOne(14570); // Admin1

		Box senderBox = boxService.findOne(14751);
		Box recipientBox = boxService.findOne(14754);

		Message message = messageService.create(sender, recipient);

		messageService.addMesageToBoxes(message);

		Assert.isTrue(senderBox.getMessages().contains(message));
		Assert.isTrue(recipientBox.getMessages().contains(message));
	}
}
