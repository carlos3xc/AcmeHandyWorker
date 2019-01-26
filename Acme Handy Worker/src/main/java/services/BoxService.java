package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	@Autowired
	private BoxRepository boxRepository;

	public Box create(Actor actor) {

		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(actor.getUserAccount().equals(userAccount));

		Box box = new Box();

		box.setActor(actor);
		box.setSystemBox(false);
		box.setMessages(new ArrayList<Integer>());

		return box;
	}

	public Collection<Box> findAll() {
		return boxRepository.findAll();
	}

	public Box findOne(int Id) {
		return boxRepository.findOne(Id);
	}

	public Collection<Box> findByActorId(int actorId) {
		return boxRepository.findByActorId(actorId);
	}

	public Box save(Box box) {

		Box result;

		// UserAccount userAccount = LoginService.getPrincipal();
		// Assert.isTrue(box.getActor().getUserAccount().equals(userAccount));

		result = boxRepository.saveAndFlush(box);
		return result;
	}

	public void delete(Box box) {

		// Assert.isTrue(!b.getSystemBox());
		Assert.isTrue(box.getSystemBox().equals(false));

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(box.getActor().getUserAccount().equals(userAccount));

		boxRepository.delete(box);
	}

	// Other business methods -----

	public Box createUserBox(Actor actor) {

		Box box = this.create(actor);

		box.setName(box.getActor().getName());
		this.save(box);

		return box;
	}
	
	public void createSystemBoxes(Actor actor) {

		Box inbox = new Box(),outbox = new Box(),thrashbox = new Box(),spambox = new Box();
		inbox.setActor(actor); inbox.setName("In Box"); inbox.setSystemBox(true); inbox.setMessages(new ArrayList<Integer>());
		outbox.setActor(actor); outbox.setName("Out Box"); outbox.setSystemBox(true); outbox.setMessages(new ArrayList<Integer>());
		thrashbox.setActor(actor); thrashbox.setName("Thrash Box"); thrashbox.setSystemBox(true); thrashbox.setMessages(new ArrayList<Integer>());
		spambox.setActor(actor); spambox.setName("Spam Box"); spambox.setSystemBox(true); spambox.setMessages(new ArrayList<Integer>());


		this.save(inbox);
		this.save(outbox);
		this.save(thrashbox);
		this.save(spambox);
	}

	public void addMessageToBox(Box box, Message message) {
		List<Integer> aux = new ArrayList<>(box.getMessages());
		
		aux.add(0, message.getId()); // los mensajes nuevos siempre se ponen primero.
		box.setMessages(aux);
		this.save(box);
		
	}
	
	public Box findByActorAndName(Actor actor, String boxName){
		Assert.notNull(actor);
		return boxRepository.findByActorIdAndName(actor.getId(), boxName);
	}

}