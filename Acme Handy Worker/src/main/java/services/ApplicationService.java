package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed Repository -----
	@Autowired
	private ApplicationRepository applicationRepository;

	// Supporting Services -----

	// @Autowired
	private MessageService messageService;

	// Constructors -----
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods -----

	public Application create() {

		Application result = new Application();
		return result;
	}

	public Application save(Application a) {

		Application result;
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(a.getHandyWorker().getUserAccount().equals(userAccount));

		result = applicationRepository.save(a);
		return result;
	}

	public void delete(Application a) {

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(a.getHandyWorker().getUserAccount().equals(userAccount));

		applicationRepository.delete(a);
	}

	public Collection<Application> findAll() {
		return applicationRepository.findAll();
	}

	public Application findOne(int Id) {
		return applicationRepository.findOne(Id);
	}

	public Collection<Application> applicationByHandyWorker(
			HandyWorker handyWorker) {
		Collection<Application> res = new ArrayList<Application>();
		for (Application a : applicationRepository.findAll()) {
			if (a.getHandyWorker().equals(handyWorker)) {
				res.add(a);
			}
		}
		return res;
	}

	// Other business methods -----

	public void changeStatus(Application a, String status) {
		a.setStatus(status);
		messageService.sendSystemMessages(a);

		this.save(a);
	}
}