package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Configuration;


@Service
@Transactional
public class ConfigurationService {

	//Managed Repository -----
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	//Supporting Services -----
	
	//Simple CRUD methods -----
	public Configuration create(){
		Configuration res = new Configuration();
		return res;
	}
	
	public Collection<Configuration> findAll(){
		return configurationRepository.findAll();
	}
	
	public Configuration findOne(int Id){
		return configurationRepository.findOne(Id);
	}
	
	public Configuration save(Configuration a){
		Configuration saved;
		Authority n = new Authority();
		n.setAuthority("ADMIN");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(n));

		
		saved = configurationRepository.saveAndFlush(a);
		return saved;
	}
	
	public void delete(Configuration a){
		Authority n = new Authority();
		n.setAuthority("ADMIN");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(n));
		
		configurationRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}