package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private BoxService boxService;

	@Test
	public void testCreate() {

		Actor sender = actorService.findOne(15730); // HandyWorker5
		Actor recipient = actorService.findOne(15720); // Admin1

		Message message = messageService.create(sender, recipient);

		// System.out.println("Sender" + message.getSender());
		// System.out.println("Recipient" + message.getRecipient());

		Assert.isTrue(message.getPriority() != "");
		Assert.isTrue(message.getTags().isEmpty());
		Assert.isTrue(message.getSender().equals(sender));
		Assert.isTrue(message.getRecipient().equals(recipient));
	}

	@Test
	public void testSave() {
		Actor sender = actorService.findOne(15730); // HandyWorker5
		Actor recipient = actorService.findOne(15720); // Admin1

		Message message = messageService.create(sender, recipient);

		// System.out.println("Sender" + message.getSender());
		// System.out.println("Recipient" + message.getRecipient());

		Message result;

		message.setBody("Cuerpo del mensaje");
		message.setSubject("Asunto del mensaje");
		message.getTags().add("tag");

		result = messageService.save(message);
		Assert.isTrue(messageService.findAll().contains(result));
	}

	// Box 7
	// Box 11

	@Test
	public void testDelete() {

		authenticate("handyWorker1");

		// Message 1 -> 15943; Sender -> 15721 Customer 1; Recipient -> 15722
		// Customer 2
		// Message 2 -> 15944; Sender -> 15722 Customer 2; Recipient -> 15721
		// Customer 1
		// Message 3 -> 15945; Sender -> 15723 Customer 3; Recipient -> 15726
		// HandyWorker 1

		// HandyWorker 1; Box 25 (Spam Box) -> 15907
		// HandyWorker 1; Box 26 (In Box) -> 15908
		// HandyWorker 1; Box 27 (Out Box) -> 15909
		// HandyWorker 1; Box 28 (Trash Box) -> 15910

		// Actor actor = actorService.findOne(15726);

		Box box = boxService.findOne(15907);
		Box box2 = boxService.findOne(15908);
		Box box3 = boxService.findOne(15909);
		Box box4 = boxService.findOne(15910);

		Collection<Box> boxes = boxService.findAll();

		Message message = messageService.findOne(15945);
		messageService.delete(message);

		Assert.isTrue(!boxes.contains(message));
		Assert.isTrue((!box.getMessages().contains(message)
				&& !box2.getMessages().contains(message)
				&& !box3.getMessages().contains(message) && box4.getMessages()
				.contains(message))
				|| (!box.getMessages().contains(message)
						&& !box2.getMessages().contains(message)
						&& !box3.getMessages().contains(message) && !box4
						.getMessages().contains(message)));

		unauthenticate();
	}

	// Application 5 -> 15975; HandyWorker 2 -> 15727;
	// FixUpTask 4 -> 15949; Customer 4 -> 15724

	// HandyWorker 2; Box 29 (Spam Box) -> 15911
	// HandyWorker 2; Box 30 (In Box) -> 15912
	// HandyWorker 2; Box 31 (Out Box) -> 15913
	// HandyWorker 2; Box 32 (Trash Box) -> 15914

	// Customer 4; Box 13 (Spam Box) -> 15895
	// Customer 4; Box 14 (In Box) -> 15896
	// Customer 4; Box 15 (Out Box) -> 15897
	// Customer 4; Box 16 (Trash Box) -> 15898

	@Test
	public void testSystemMessage() {

		Application application = applicationService.findOne(15975);

		Customer customer = application.getFixUpTask().getCustomer();
		HandyWorker handyWorker = application.getHandyWorker();

		Assert.isTrue(customer.getId() == 15724);
		Assert.isTrue(handyWorker.getId() == 15727);

		messageService.sendSystemMessages(application);

		Box customerBox = boxService.findOne(15896);
		Box handyWorkerBox = boxService.findOne(15912);
		Box administratorBox = boxService.findOne(15905);

		Assert.isTrue(customerBox.getMessages().size() == 1);
		Assert.isTrue(handyWorkerBox.getMessages().size() == 1);
		Assert.isTrue(administratorBox.getMessages().size() == 2);
	}

	// Administrator 1; Box 21 (Spam Box) -> 15903
	// Administrator 1; Box 22 (In Box) -> 15904
	// Administrator 1; Box 23 (Out Box) -> 15905
	// Administrator 1; Box 24 (Trash Box) -> 15906

	// Customer 5; Box 17 (Spam Box) -> 15899
	// Customer 5; Box 18 (In Box) -> 15900
	// Customer 5; Box 19 (Out Box) -> 15901
	// Customer 5; Box 20 (Trash Box) -> 15902

	@Test
	public void testMessageToBox() {

		// authenticate("admin1");
		// authenticate("customer5");

		Actor sender = actorService.findOne(15725); // Customer5
		Actor recipient = actorService.findOne(15720); // Admin1

		Box senderBox = boxService.findOne(15901);
		Box recipientBox = boxService.findOne(15904);

		Message message = messageService.create(sender, recipient);

		messageService.addMesageToBoxes(message);

		Assert.isTrue(senderBox.getMessages().contains(message));
		Assert.isTrue(recipientBox.getMessages().contains(message));

		// unauthenticate();
		// unauthenticate();
	}
}
