package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import domain.SocialProfile;
import domain.Sponsor;


@Service
@Transactional
public class SponsorService {

	//Managed Repository -----
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	//Supporting Services -----
	
	@Autowired
	private UserAccountService uaService;
	
	//Simple CRUD methods -----
	public Sponsor create(){
		Sponsor res = new Sponsor();
		
		res.setIsBanned(false);
		res.setIsSuspicious(false);
		res.setSocialProfiles(new ArrayList<SocialProfile>());
		
		UserAccount ua = new UserAccount();
		Authority a = new Authority();
		a.setAuthority("SPONSOR");
		ua.getAuthorities().add(a);
		res.setUserAccount(uaService.save(ua));
		
		return res;
	}
	
	public Collection<Sponsor> findAll(){
		return sponsorRepository.findAll();
	}
	
	public Sponsor findOne(int Id){
		return sponsorRepository.findOne(Id);
	}
	
	public Sponsor save(Sponsor a){
		
		return sponsorRepository.save(a);
	}
	
	public void delete(Sponsor a){

		sponsorRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Sponsor findSponsorByUserAccount(UserAccount ua){
		return sponsorRepository.findSponsorByUserAccount(ua.getId());
	}
}