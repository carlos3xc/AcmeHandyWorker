package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Customer;
import domain.Finder;
import domain.HandyWorker;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private BoxService boxService;

	// Simple CRUD methods -----

	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor a) {
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(a.getUserAccount().equals(userAccount));

		actorRepository.save(a);
		return a;
	}

	public void delete(Actor a) {
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(a.getUserAccount().equals(userAccount));

		actorRepository.delete(a);
	}

	// Other business methods -----
	
	  public Actor getByUserAccountId(UserAccount ua){ return
	  actorRepository.findByUserAccountId(ua); }
	/*  
	  public Actor findByFinderId(final int finderId) {
		Assert.notNull(finderId);
		Actor result;
		result = this.actorRepository.findByFinderId(finderId);
		return result;
	} */

	private void createSystemBoxes(Actor a) {
		Box inbox, outbox, spambox, trashbox;
		inbox = boxService.create(a);
		outbox = boxService.create(a);
		spambox = boxService.create(a);
		trashbox = boxService.create(a);

		inbox.setName("inbox");
		outbox.setName("outbox");
		spambox.setName("spambox");
		trashbox.setName("trashbox");

		inbox.setSystemBox(true);
		outbox.setSystemBox(true);
		spambox.setSystemBox(true);
		trashbox.setSystemBox(true);

		boxService.save(inbox);
		boxService.save(outbox);
		boxService.save(spambox);
		boxService.save(trashbox);

	}

//	public void register(String type, UserAccount ua) {
//		// Solo pueden registrarse nuevos actores como customer o como
//		// HandyWorker.
//		Actor nuevo = this.create(ua);
//
//		if (type.equals("CUSTOMER")) {
//
//			Customer aux = new Customer();
//			aux = (Customer) nuevo;
//		}
//		if (type.equals("HANDYWORKER")) {
//			HandyWorker aux = new HandyWorker();
//			aux = (HandyWorker) nuevo;
//		}
//		if (type.equals("SPONSOR")) {
//			Sponsor aux = new Sponsor();
//			aux = (Sponsor) nuevo;
//		}
//		this.save(nuevo);
//	}

}