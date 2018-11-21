package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class NoteServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private NoteService noteService;
	
	// Tests ----------------------------------------------------------------------
	
	@Test
	public void testRefereeCreateNotes(){
		Note note;
		super.authenticate("referee1");
		note = noteService.create();	
		
		Assert.isNull(note.getCustomerComment());
		Assert.isNull(note.getHandyWorkerComment());
		Assert.isNull(note.getRefereeComment());
		Assert.isNull(note.getMoment());
		super.authenticate(null);
	}
}
