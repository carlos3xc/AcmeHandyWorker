package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.HandyWorker;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Test
	public void testCreate() {
		Application application = applicationService.create();
		Assert.state(application.getStatus() != "",
				"El estado de la aplicación no debe ser nulo");
	}

	@Test
	public void testSave() {
		authenticate("handyworker1");

		Application application, result;
		application = applicationService.create();

		Date current = new Date(System.currentTimeMillis() - 1000);
		Date fecha = new Date();

		// https://codechi.com/dev-tools/date-to-millisecond-calculators/
		// Añadir una f al final para indicar que es un long
		fecha.setTime(1608591600000l);

		application.setMoment(current);
		application.setPrice(68.5);
		application.setStatus("PENDING");
		application.setHandyWorkerComment("");
		application.setCustomerComment("");

		CreditCard credit = new CreditCard();
		credit.setBrand("brand1");
		credit.setCVV(773);
		credit.setExpirationDate(fecha);
		credit.setHolder("holder1");
		credit.setNumber("1234567891234567");

		application.setCreditCard(credit);

		FixUpTask fixUpTask = fixUpTaskService.findOne(14796);
		application.setFixUpTask(fixUpTask);

		HandyWorker handyWorker = handyWorkerService.findOne(14576);
		application.setHandyWorker(handyWorker);

		result = applicationService.save(application);
		Assert.isTrue(applicationService.findAll().contains(result));
		unauthenticate();
	}

	@Test
	public void testUpdateHandyWorker() {
		authenticate("handyWorker1");
		Application application = applicationService.findOne(14811);
		Application result;

		application.setHandyWorkerComment("Comentario trabajador modificado");

		result = applicationService.save(application);
		Assert.isTrue(applicationService.findAll().contains(result));
		unauthenticate();
	}

	@Test
	public void testUpdateCustomer() {
		authenticate("customer1");
		Application application = applicationService.findOne(14822);
		Application result;
		// Customer 1 -> 14571
		// FixUpTask 8 -> 14803
		// Application 12 -> 14822
		application.setCustomerComment("Comentario cliente modificado");

		result = applicationService.save(application);
		Assert.isTrue(applicationService.findAll().contains(result));
		unauthenticate();
	}

	@Test
	public void testDelete() {
		authenticate("handyWorker2");
		Application application = applicationService.findOne(14815);
		applicationService.delete(application);
		Assert.isTrue(!applicationService.findAll().contains(application));
		unauthenticate();
	}

	@Test
	public void testApplicationByHandyWorker() {
		Collection<Application> res = new ArrayList<Application>();
		// HandyWorker handyWorker = handyWorkerService.findOne(14670);
		res = applicationService.applicationByHandyWorker(14578);
		Assert.isTrue(res.size() == 4);
	}

	@Test
	public void testChangeStatus() {
		authenticate("customer1"); // 14578

		// String a = "PENDING";
		// String b = "ACCEPTED";
		String c = "REJECTED";

		Application application = applicationService.findOne(14822);
		applicationService.changeStatus(application, c);

		unauthenticate();
	}

}
