package services;

import java.sql.Date;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
										"classpath:spring/config/packages.xml"})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {
	
	// Service under test ---------------------------------------------------------	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private WarrantyService warrantyService;
		
	// Tests ----------------------------------------------------------------------
	
	// CREATE ---------------------------------------------------------------------
	
	@Test
	public void testCreateFixUpTask(){
		FixUpTask fixUpTask;
		super.authenticate("customer1");
		fixUpTask = fixUpTaskService.create();
		Assert.isTrue(fixUpTask.getApplications().isEmpty());
		Assert.isNull(fixUpTask.getTicker());
		Assert.isNull(fixUpTask.getCategory());
		Assert.isNull(fixUpTask.getMoment());
		Assert.isNull(fixUpTask.getDescription());
		Assert.isNull(fixUpTask.getAddress());
		Assert.isTrue(fixUpTask.getComplaints().isEmpty());
		Assert.isNull(fixUpTask.getCustomer());
		Assert.isNull(fixUpTask.getEndMoment());
		Assert.isNull(fixUpTask.getStartMoment());
		Assert.isNull(fixUpTask.getMaxPrice());
		Assert.isNull(fixUpTask.getWarranty());
		super.authenticate(null);
	}
	
	
	// SAVE -----------------------------------------------------------------------
	
	@Test 
	public void testSaveFixUpTask(){
		FixUpTask fixUpTask, saved;
		Collection<FixUpTask> fixUpTasks;
		Category category;

		super.authenticate("customer1");						// Nos autenticamos como Referee
		fixUpTask = fixUpTaskService.create();					// Creamos el reporte
		
		category = categoryService.findOne(14666);
		fixUpTask.setDescription("Description fixuptask test");
		fixUpTask.setAddress("Address test");
		fixUpTask.setCategory(category);
		fixUpTask.setMaxPrice(600d);
		fixUpTask.setStartMoment(Date.valueOf("2018-05-12"));
		fixUpTask.setEndMoment(Date.valueOf("2019-01-13"));
		
		Warranty warranty, Wsaved;
		warranty= warrantyService.create();
		warranty.setTitle("Warranty title test");
		warranty.setTerms("Terms warranty test");
		warranty.getLaws().add("Law test warranty");
		warranty.setIsDraft(true);
		Wsaved = warrantyService.save(warranty);
		
		fixUpTask.setWarranty(Wsaved);
		saved = fixUpTaskService.save(fixUpTask);					// Guardamos el reporte	
		
		fixUpTasks = fixUpTaskService.findAll();					// Comprobamos que el reporte se ha guardado correctamente en el archivo de reportes

		Assert.isTrue(fixUpTasks.contains(saved));
		super.authenticate(null);
	}
	
/*
	// UPDATE ---------------------------------------------------------------------
	
	@Test 
	public void testUpdateComplaints(){
		Complaint complaint,saved;
		super.authenticate("customer1");						// Nos autenticamos como referee
		complaint = complaintService.findOne(14823);			// Recuperamos el reporte
		complaint.getAttachments().add("Attachment test 2");	// Modificamos algunos atributos
		complaint.setDescription("Description test");
		saved = complaintService.save(complaint);				// Guardamos el reporte	

		Assert.isTrue(saved.getAttachments().contains("Attachment test 2"));
		Assert.isTrue(saved.getDescription().equals("Description test"));
		super.authenticate(null);
	}
	
	// DELETE ---------------------------------------------------------------------

	@Test 
	public void testDeleteComplaints(){
		Complaint complaint;
		Collection<Complaint> complaints;
		super.authenticate("customer1");								// Nos autenticamos como referee

		complaint = complaintService.findOne(14823);						// Recuperamos el report al que queremos eliminar la nota
		
		complaintService.delete(complaint);									// Eliminamos la nota	
		complaints = complaintService.findAll();						
		Assert.isTrue(!complaints.contains(complaint));						// Comprobamos que la nota se ha eliminado correctamente en el archivo de notas
		
		super.authenticate(null);
	}*/
	
}