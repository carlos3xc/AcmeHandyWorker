package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class NoteServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private ReportService reportService;
	
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
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
	
	@Test
	public void testCustomerCreateNotes(){
		Note note;
		super.authenticate("customer1");
		note = noteService.create();	
		Assert.isNull(note.getCustomerComment());
		Assert.isNull(note.getHandyWorkerComment());
		Assert.isNull(note.getRefereeComment());
		Assert.isNull(note.getMoment());
		super.authenticate(null);
	}
	
	@Test
	public void testHandyWorkerCreateNotes(){
		Note note;
		super.authenticate("handyworker1");
		note = noteService.create();	
		Assert.isNull(note.getCustomerComment());
		Assert.isNull(note.getHandyWorkerComment());
		Assert.isNull(note.getRefereeComment());
		Assert.isNull(note.getMoment());
		super.authenticate(null);
	}
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testRefereeSaveNotes(){
		Note note,saved;
		Report report;
		Collection<Note> notes;
		super.authenticate("referee1");						// Nos autenticamos como Referee
		note = noteService.create();						// Creamos la nota
		report = reportService.findOne(14829);				// Recuperamos el report al que queremos asociar la nota
		
		note.setRefereeComment("Referee comment");			// Completamos los atributos de note
		note.setCustomerComment("");
		note.setHandyWorkerComment("");
		note.setReport(report);
		
		saved = noteService.save(note);						// Guardamos la nota	
		
		report.getNotes().add(saved);						// A�adimos la nota guardada a la lista de notas del report en concreto
		reportService.saveAut(report);							// y guardamos
		notes = noteService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(notes.contains(saved));
		
		super.authenticate(null);
	}
	
	@Test 
	public void testCustomerSaveNotes(){
		Note note,saved;
		Report report;
		Collection<Note> notes;
		super.authenticate("customer1");					// Nos autenticamos como Customer
		note = noteService.create();						// Creamos la nota
		report = reportService.findOne(14829);				// Recuperamos el report al que queremos asociar la nota
		
		note.setRefereeComment("");							// Completamos los atributos de note
		note.setCustomerComment("Customer comment");
		note.setHandyWorkerComment("Hello");
		note.setReport(report);
		
		saved = noteService.save(note);					// Guardamos la nota	
		
		report.getNotes().add(saved);						// A�adimos la nota guardada a la lista de notas del report en concreto
		reportService.saveAut(report);							// y guardamos
		notes = noteService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(notes.contains(saved));
		
		super.authenticate(null);
	}
	
	@Test 
	public void testHandyWorkerSaveNotes(){
		Note note,saved;
		Report report;
		Collection<Note> notes;
		super.authenticate("handyworker1");					// Nos autenticamos como handy worker
		note = noteService.create();						// Creamos la nota
		report = reportService.findOne(14829);				// Recuperamos el report al que queremos asociar la nota
		
		note.setRefereeComment("");							// Completamos los atributos de note
		note.setCustomerComment("");
		note.setHandyWorkerComment("Hello");
		note.setReport(report);
		
		saved = noteService.save(note);						// Guardamos la nota	
		
		report.getNotes().add(saved);						// A�adimos la nota guardada a la lista de notas del report en concreto
		reportService.saveAut(report);							// y guardamos
		notes = noteService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(notes.contains(saved));
		
		super.authenticate(null);
	}

	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testHandyWorkerUpdateNotes(){
		Note note,saved;
		Collection<Note> notes;
		super.authenticate("handyworker1");					// Nos autenticamos como handy worker
		note = noteService.findOne(14833);					// Recuperamos la nota

		note.setHandyWorkerComment("Hello");
		
		saved = noteService.save(note);						// Guardamos la nota	
		
		notes = noteService.findAll();						// Comprobamos que la nota se ha guardado correctamente en el archivo de notas
		Assert.isTrue(notes.contains(saved));

		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------
	
	@Test 
	public void testRefereeDeleteNotes(){
		Note note;
		Report report, saved;
		Collection<Note> notes;
		super.authenticate("referee1");								// Nos autenticamos como referee

		report = reportService.findOne(14829);						// Recuperamos el report al que queremos eliminar la nota
		note = noteService.findOne(14833);							// Recuperamos la nota a eliminar
		
		Assert.isTrue(!(note.getRefereeComment().equals("")));		// Comprobamos que el comentario referee no sea vac�o, es decir, que lo cre� un referee
		
		report.getNotes().remove(note);								// Eliminamos la nota de la colecci�n de notas del reporte
		saved = reportService.saveAut(report);						// y guardamos el reporte
		
		noteService.delete(note);									// Eliminamos la nota	
		
		notes = noteService.findAll();						
		Assert.isTrue(!saved.getNotes().contains(note));			// Comprobamos que la nota se ha borrado de la lista 
		Assert.isTrue(!notes.contains(note));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}
	
	@Test 
	public void testCustomerDeleteNotes(){
		Note note;
		Report report, saved;
		Collection<Note> notes;
		super.authenticate("customer1");							// Nos autenticamos como customer

		report = reportService.findOne(14830);						// Recuperamos el report al que queremos eliminar la nota
		note = noteService.findOne(14834);							// Recuperamos la nota a eliminar
		
		Assert.isTrue(!(note.getCustomerComment().equals("")));		// Comprobamos que el comentario customer no sea vac�o, es decir, que lo cre� un customer
		
		report.getNotes().remove(note);								// Eliminamos la nota de la colecci�n de notas del reporte
		saved = reportService.saveAut(report);						// y guardamos el reporte
			
		noteService.delete(note);									// Eliminamos la nota	
		
		notes = noteService.findAll();						
		Assert.isTrue(!saved.getNotes().contains(note));			// Comprobamos que la nota se ha borrado de la lista 
		Assert.isTrue(!notes.contains(note));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}
	
	@Test 
	public void testHandyWorkerDeleteNotes(){
		Note note;
		Report report, saved;
		Collection<Note> notes;
		super.authenticate("handyworker1");							// Nos autenticamos como handy worker

		report = reportService.findOne(14832);						// Recuperamos el report al que queremos eliminar la nota
		note = noteService.findOne(14836);							// Recuperamos la nota a eliminar
		
		Assert.isTrue(!(note.getHandyWorkerComment().equals("")));  // Comprobamos que el comentario handyworker no sea vac�o, es decir, que lo cre� un handy worker
		
		report.getNotes().remove(note);								// Eliminamos la nota de la colecci�n de notas del reporte
		saved = reportService.saveAut(report);						// y guardamos el reporte
		
		noteService.delete(note);									// Eliminamos la nota	
		
		notes = noteService.findAll();						
		Assert.isTrue(!saved.getNotes().contains(note));			// Comprobamos que la nota se ha borrado de la lista 
		Assert.isTrue(!notes.contains(note));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
			
		super.authenticate(null);
	}
	

}