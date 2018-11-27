package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;

@Service
@Transactional
public class HandyWorkerService {

	// Managed Repository -----
	@Autowired
	private HandyWorkerRepository handyWorkerRepository;

	// Supporting Services -----

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private SocialProfileService socialProfileService;

	// Constructors -----
	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods -----
	public HandyWorker create() {
		HandyWorker res = new HandyWorker();
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		UserAccount ua = userAccountService.create();
		res.setUserAccount(ua);
		return res;
	}

	public Collection<HandyWorker> findAll() {
		return handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(int handyWorkerId) {
		return handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(HandyWorker hw) {
		Authority p = new Authority();
		UserAccount ua;
		UserAccount savedUa;
		Collection<HandyWorker> handyWorkers;

		if (hw.getId() != 0) {
			UserAccount userAccount = LoginService.getPrincipal();
			Assert.isTrue(userAccount.equals(hw.getUserAccount()));
		}
		HandyWorker saved;
		if (hw.getId() == 0) {
			hw.setIsBanned(false);
			hw.setIsSuspicious(false);
			p.setAuthority("HANDYWORKER");
			ua = hw.getUserAccount();
			ua.getAuthorities().add(p);
			savedUa = userAccountService.save(ua);
			hw.setUserAccount(savedUa);
		}
		saved = handyWorkerRepository.saveAndFlush(hw);
		handyWorkers = handyWorkerRepository.findAll();
		Assert.isTrue(handyWorkers.contains(saved));
		return saved;
	}

//	public void delete(HandyWorker handyWorker) {
//		Assert.notNull(handyWorker);
//		Assert.isTrue(handyWorker.getId() != 0);
//
//		Collection<SocialProfile> socialprofiles = handyWorker
//				.getSocialProfiles();
//
//		// Borrar los identities de ese handy worker
//		for (SocialProfile sp : socialprofiles) {
//			socialProfileService.delete(sp);
//		}
//		this.handyWorkerRepository.delete(handyWorker);
//	}

	// Other business methods -----
	
	public HandyWorker findByUserAccountId(Integer Id){
		HandyWorker hw;
		hw = handyWorkerRepository.findByUserAccountId(Id);
		return hw;
	}

	public HandyWorker findByPrincipal() {
		HandyWorker hw;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		hw = handyWorkerRepository.findByPrincipal(userAccount.getId());
		return hw;
	}

	public HandyWorker findByFinderId(final int finderId) {
		Assert.notNull(finderId);
		HandyWorker result;
		result = this.handyWorkerRepository.findByFinderId(finderId);
		return result;
	}
	

}