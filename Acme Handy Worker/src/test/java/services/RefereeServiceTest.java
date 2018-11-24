package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Note;
import domain.Referee;
import domain.Report;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class RefereeServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private RefereeService refereeService;
	
	@Autowired
		
	// Tests ----------------------------------------------------------------------

	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateReferee(){
		Referee referee;
		super.authenticate("admin1");
		referee = refereeService.create();	
		Assert.isNull(referee.getAddress());
		Assert.isNull(referee.getEmail());
		Assert.isNull(referee.getIsBanned());
		Assert.isNull(referee.getIsSuspicious());
		Assert.isNull(referee.getMiddleName());
		Assert.isNull(referee.getName());
		Assert.isNull(referee.getPhone());
		Assert.isNull(referee.getPhoto());
		Assert.isNull(referee.getSurname());
		Assert.isTrue(referee.getSocialProfiles().isEmpty());
		Assert.notNull(referee.getUserAccount());
							
		super.authenticate(null);
	}

	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveReferee(){
		Referee referee,saved;
		Collection<Referee> referees;
		super.authenticate("admin1");						// Nos autenticamos como Referee
		referee = refereeService.create();						// Creamos la nota
		
		referee.setName("Juan");
		referee.setSurname("Serna");
		referee.setEmail("juaparser@gmail.com");
		referee.setPhone("678534953");
		referee.setAddress("Calle de la Chincheta nº10");
		referee.setMiddleName("Parra");
		referee.setPhoto("http://www.linkedIn.com");

		SocialProfile socialProfile = new SocialProfile();
		socialProfile.setLink("http://www.twitter.com/Juan");
		socialProfile.setNick("juaparser");
		socialProfile.setSocialNetwork("Twitter");
		referee.getSocialProfiles().add(socialProfile);

		UserAccount userAccount = referee.getUserAccount();
		userAccount.setUsername("referee12");
		userAccount.setPassword("referee12");
		referee.setUserAccount(userAccount);

		saved = refereeService.save(referee);
		
		System.out.println(saved.getSocialProfiles() + " " + saved.getUserAccount().getAuthorities() + " " + saved.getUserAccount().getPassword());
		referees = refereeService.findAll();
		Assert.isTrue(referees.contains(saved));
		
		
		super.authenticate(null);
	}
	

	// UPDATE ---------------------------------------------------------------------
/*
	@Test 
	public void testUpdateReferee(){
		Note note,saved;
		Collection<Note> notes;
		super.authenticate("handyworker1");					// Nos autenticamos como handy worker
		note = noteService.findOne(14833);					// Recuperamos la nota

		note.setHandyWorkerComment("Hello");
		
		saved = noteService.save(note);						// Guardamos la nota	
		
		notes = noteService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(notes.contains(saved));

		super.authenticate(null);
	}*/
	
	// DELETE ---------------------------------------------------------------------

	

}