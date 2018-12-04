package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;

import domain.EndorserRecord;



@Service
@Transactional
public class EndorserRecordService {

	//Managed Repository -----
	@Autowired
	private EndorserRecordRepository endorserRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 

	//Simple CRUD methods -----
	public EndorserRecord create(){
	
		EndorserRecord res = new EndorserRecord();
		return res;
	}
	
	public Collection<EndorserRecord> findAll(){
		return endorserRecordRepository.findAll();
	}
	
	public EndorserRecord findOne(int Id){
		return endorserRecordRepository.findOne(Id);
	}
	
	public EndorserRecord save(EndorserRecord a){
		
		//si el HandyWorker tiene una curricula se le guarda/actualiza el ER, si no simplemente se guarda ER sin vincular.
				boolean hasCurricula = false;
				EndorserRecord res = null;
				Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
				UserAccount logged = LoginService.getPrincipal();
				
				for (Curricula c : curriculaService.findAll()) {
					if(c.getHandyWorker().getUserAccount().equals(logged)){
						if(c.getEndorserRecords().contains(a)){
						//ya existe en un endorser record
						res = endorserRecordRepository.saveAndFlush(a);
						}else{
						//exite la curricula del handyworker.
						
						res = endorserRecordRepository.saveAndFlush(a);
						Collection<EndorserRecord> aux = c.getEndorserRecords();
						aux.add(res);
						curriculaService.save(c);
						}
						hasCurricula = true;
					}
				}
				if(!hasCurricula){
					res = endorserRecordRepository.saveAndFlush(a);
				}
				Assert.notNull(res);
				return res;
	}
	
	public void delete(EndorserRecord a) {
		// probar si necesita borrarse de la lista de curricula manualmente.
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		for (Curricula c : curriculaService.findAll()) {
			if (c.getEndorserRecords().contains(a)
					&& c.getHandyWorker().getUserAccount().equals(logged)) {
				c.getEndorserRecords().remove(a);
				curriculaService.save(c);
				endorserRecordRepository.delete(a);
				//System.out.println("se borra el endorserRecord");
			}
		}
	}
	
	//Other business methods -----

}