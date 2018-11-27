package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class SponsorServiceTest extends AbstractTest{
	
	// Services under test
	
	@Autowired
	private SponsorService sponsorService;
	
	// Test
	
	@Test
	public void testCreate(){
		Sponsor res = sponsorService.create();
		
		Assert.isTrue(!res.getIsBanned());
		Assert.isTrue(!res.getIsSuspicious());
		Assert.isTrue(res.getSocialProfiles().isEmpty());
		Authority a = new Authority();
		a.setAuthority("SPONSOR");
		Assert.isTrue(res.getUserAccount().getAuthorities().contains(a));
		Assert.isNull(res.getAddress());
		Assert.isNull(res.getEmail());
		Assert.isNull(res.getMiddleName());
		Assert.isNull(res.getName());
		Assert.isNull(res.getSurname());
		Assert.isNull(res.getPhone());
		Assert.isNull(res.getPhoto());
	}
	
	@Test
	public void testSave(){
		
		Sponsor res = sponsorService.create();
		
		res.setAddress("Calle locura masima");
		res.setEmail("email@gmai.com");
		res.setMiddleName("Rodriguez");
		res.setName("Rodrigo");
		res.setPhone("673577161");
		res.setPhoto("https://fotosperfil.com");
		res.setSurname("Bermujo");
		
		Sponsor saved = sponsorService.save(res);
		Assert.isTrue(sponsorService.findAll().contains(saved));
	}
	
	@Test
	public void testFindSponsor(){
		
		Sponsor res = (Sponsor) sponsorService.findAll().toArray()[1];
		Sponsor sponsor = sponsorService.findSponsorByUserAccount(res.getUserAccount());
		
		Assert.isTrue(res.equals(sponsor));
	}

}
