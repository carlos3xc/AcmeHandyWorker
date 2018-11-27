package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Referee;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private ActorService actorService;

	// Simple CRUD methods -----

	public Administrator createAdministrator() {

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		UserAccount user = new UserAccount();
		user.addAuthority(authority);

		Administrator administrator = new Administrator();
		administrator.setUserAccount(user);
		administrator.setSocialProfiles(new ArrayList<SocialProfile>());

		return administrator;
	}

	public Referee createReferee() {

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		Authority authority2 = new Authority();
		authority2.setAuthority("REFEREE");

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		UserAccount user = new UserAccount();
		user.addAuthority(authority2);

		Referee referee = new Referee();
		referee.setUserAccount(user);
		referee.setSocialProfiles(new ArrayList<SocialProfile>());

		return referee;
	}

	public Administrator save(Administrator administrator) {

		Administrator result;

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		result = administratorRepository.save(administrator);
		return result;
	}

	public void delete(Administrator administrator) {

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(authority));

		administratorRepository.delete(administrator);
	}

	public Collection<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	public Administrator findOne(int Id) {
		return administratorRepository.findOne(Id);
	}

	public Collection<Actor> findSuspicious() {
		Collection<Actor> result = new ArrayList<Actor>();
		result = administratorRepository.findSuspicious();
		return result;
	}

	public Actor ban(Actor actor) {

		Assert.isTrue(actor.getIsSuspicious().equals(true)
				&& actor.getIsBanned().equals(false));

		actor.setIsBanned(true);

		return actorService.save(actor);
	}

	public Actor unBan(Actor actor) {

		Assert.isTrue(actor.getIsBanned().equals(true));

		actor.setIsBanned(false);

		return actorService.save(actor);
	}

}