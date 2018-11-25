package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerEndorsementRepository;
import domain.HandyWorkerEndorsement;


@Service
@Transactional
public class HandyWorkerEndorsementService {

	//Managed Repository -----
	@Autowired
	private HandyWorkerEndorsementRepository handyWorkerEndorsementRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public HandyWorkerEndorsementService(){
		super();
	}
	
	//Simple CRUD methods -----
	public HandyWorkerEndorsement create(){
		HandyWorkerEndorsement res = new HandyWorkerEndorsement();
		return res;
	}
	
	public Collection<HandyWorkerEndorsement> findAll(){
		return handyWorkerEndorsementRepository.findAll();
	}
	
	public HandyWorkerEndorsement findOne(int HandyWorkerEndorsementId){
		return handyWorkerEndorsementRepository.findOne(HandyWorkerEndorsementId);
	}
	
	public HandyWorkerEndorsement save(HandyWorkerEndorsement handyWorkerEndorsement){
		Assert.notNull(handyWorkerEndorsement);	
		HandyWorkerEndorsement res;
		res = handyWorkerEndorsementRepository.save(handyWorkerEndorsement);
		return res;
	}
	
	public void delete(HandyWorkerEndorsement handyWorkerEndorsement){
		Assert.notNull(handyWorkerEndorsement);
		Assert.isTrue(handyWorkerEndorsement.getId() != 0);
		this.handyWorkerEndorsementRepository.delete(handyWorkerEndorsement);	
	}
	
	//Other business methods -----
	
	
}