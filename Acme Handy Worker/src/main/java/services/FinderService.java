package services;

import java.util.Collection;
import java.util.HashSet;

import domain.FixUpTask;
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
		Assert.notNull(finder);
		if (finder.getId() != 0)
			Assert.isTrue(this.esDeActorActual(finder));
		Finder result;

		if(finder.getId()==0){

		}
		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(Finder finder){
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		this.finderRepository.delete(finder);
	}
	
	//Other business methods -----
	
	private Boolean esDeActorActual(final Finder finder) {
		Boolean result;

		final HandyWorker principal = this.handyWorkerService.findByPrincipal();
		final HandyWorker handyWorkerFromFinder = finderRepository.findOne(finder.getId()).getHandyWorker();

		result = principal.equals(handyWorkerFromFinder);
		return result;
	}
	
	
}