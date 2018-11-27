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

import utilities.AbstractTest;
import domain.Finder;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class FinderServiceTest extends AbstractTest {

	// Service under test --------------------

	@Autowired
	private FinderService	finderService;


	// Tests --------------------

	@Test
	public void testCreate() {
		this.authenticate("Francisco Pozo Nevado");
		Finder finder;
		finder = this.finderService.create();
		finder.setKeyword("xxx");
		finder.setMaxPrice(100.0);
		finder.setMinPrice(50.0);
		final Date fecha = new Date(01/01/2018);
		finder.setStartDate(fecha);
		finder.setEndDate(fecha);
		finder.setCategory(0);
		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		finder.setFixUpTasks(fixUpTasks);
		Finder saved = finderService.save(finder);
		Collection<Finder> finders = finderService.findAll();
		Assert.isTrue(finders.contains(saved), "----- Fallo metodo create -----");
	}

	@Test
	public void testFindAll() {
		Collection<Finder> finders = this.finderService.findAll();
		Assert.isTrue(finders.size() > 0, "----- Fallo metodo findAll -----");
	}

	@Test
	public void testSave() {
		this.authenticate("Francisco Pozo Nevado");
		final Finder finderPrueba = this.finderService.findOne(14633);
		finderPrueba.setMaxPrice(150.0);
		Finder saved = this.finderService.save(finderPrueba);
		Collection<Finder> finders = this.finderService.findAll();
		Assert.isTrue(finders.contains(saved), "----- Fallo metodo save -----");
	}

	@Test
	public void testFindOne() {
		Finder f = this.finderService.findOne(14634);
		Assert.isTrue(!f.equals(null), "----- Fallo metodo findOne -----");
	}

	@Test
	public void testDelete() {
		this.authenticate("handyworker3");
		final Finder finder = this.finderService.findOne(14635);
		this.finderService.delete(finder);
	}

}
