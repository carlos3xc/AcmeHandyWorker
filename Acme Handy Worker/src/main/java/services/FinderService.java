package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import domain.Configuration;
import domain.FixUpTask;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.HandyWorker;


@Service
@Transactional
public class FinderService {

	//Managed Repository -----
	@Autowired
	private FinderRepository finderRepository;
	
	//Supporting Services -----
	
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ConfigurationService configurationService;
	
	//Constructors -----
	public FinderService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Finder create(){
		Finder result;
		result = new Finder();
		result.setFixUpTasks(new HashSet<FixUpTask>());
		return result;
	}

	
	public Collection<Finder> findAll(){
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}
	
	public Finder findOne(int finderId){
		Finder finder;
		finder = this.finderRepository.findOne(finderId);
		return finder;
	}
	
	public Finder save(Finder finder){
		Finder result;
		Assert.notNull(finder);
		if (finder.getId() != 0) {
			Assert.isTrue(this.esDeActorActual(finder));
			if(isVoid(finder)){
				finder.setMoment(null);
			}else{
				finder.setMoment(DateUtils.addMilliseconds(new Date(),-1));
			}

		}else{
			/*Checking that the HandyWorker has not a Finder already
				(Probably not necessary with '@OneToOne(optional = false)' annotation)
			 */
			Assert.isNull(findByHandyWorker(finder.getHandyWorker()));
			/*Checking that all attributes but handyWorker and moment are null*/
			Assert.isTrue(isVoid(finder));
			/*Checking that moment is null, no results are cached*/
			Assert.isNull(finder.getMoment());
		}
		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}

	public Finder findByHandyWorker(HandyWorker handyWorker){
		Assert.notNull(handyWorker);
		Finder result;

		result = finderRepository.findByHandyWorker(handyWorker.getId());

		return result;
	}

	public Finder findByPrincipal(){
		Finder result;
		HandyWorker principal = handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = findByHandyWorker(principal);

		return result;
	}
	
	//Other business methods -----
	
	private Boolean esDeActorActual(final Finder finder) {
		Boolean result;

		final HandyWorker principal = this.handyWorkerService.findByPrincipal();
		final HandyWorker handyWorkerFromFinder = finderRepository.findOne(finder.getId()).getHandyWorker();

		result = principal.equals(handyWorkerFromFinder);
		return result;
	}

	private Boolean isVoid(final Finder finder){
		Boolean result;

		result = finder.getMinPrice() == null
				&& finder.getMaxPrice() == null
				&& finder.getStartDate() == null
				&& finder.getEndDate() == null
				&& finder.getCategory() == null
				&& finder.getWarranty() == null;

		return result;
	}

	private Boolean isExpired(Finder finder){
		Boolean result;
		Configuration configuration = configurationService.find();
		Double cacheTimeInHours = configuration.getFinderCacheTime();
		Date expirationMoment =  new Date();
			/*Adding Hours*/
			expirationMoment= DateUtils.addHours(expirationMoment, cacheTimeInHours.intValue());
			/*Adding Hours*/
			expirationMoment = DateUtils.addMinutes(expirationMoment,
				Double.valueOf(60 * (cacheTimeInHours - cacheTimeInHours.intValue())).intValue());

		result = finder.getMoment().after(expirationMoment);

		return result;
	}

	//TODO: public Collection<FixUpTask> filterFixUpTasks();
	
	
}