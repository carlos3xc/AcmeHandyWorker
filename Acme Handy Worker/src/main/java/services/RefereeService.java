package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Referee;
import domain.SocialProfile;


@Service
@Transactional
public class RefereeService {

	//Managed Repository -----
	@Autowired
	private RefereeRepository refereeRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	//Simple CRUD methods -----
	public Referee create(){
		Referee res = new Referee();
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		UserAccount ua = userAccountService.create();
		res.setUserAccount(ua);
		return res;
	}
	
	public Collection<Referee> findAll(){
		return refereeRepository.findAll();
	}
	
	public Referee findOne(int Id){
		return refereeRepository.findOne(Id);
	}
	
	public Referee save(Referee r){
		Authority e = new Authority();
		Authority p = new Authority();
		UserAccount savedUa;
		e.setAuthority("ADMIN");
		Collection<Referee> referees;

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));	

		Referee saved;
		if(r.getId()==0){
			r.setIsBanned(false);
			r.setIsSuspicious(false);
		}
		
		p.setAuthority("REFEREE");
		
		UserAccount ua = r.getUserAccount();
		ua.getAuthorities().add(p);
		savedUa = userAccountService.save(ua);
		r.setUserAccount(savedUa);
		saved = refereeRepository.saveAndFlush(r);
		referees = refereeRepository.findAll();
		Assert.isTrue(referees.contains(saved));
		return saved;
	}
	/*
	public void delete(Referee a){
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		refereeRepository.delete(a);
	}*/
	
	//Other business methods -----
	
	public Referee findByUserAccountId(Integer Id){
		Referee r;
		r = refereeRepository.findByUserAccountId(Id);
		return r;
	}
	
}