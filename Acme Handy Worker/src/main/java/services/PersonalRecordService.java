package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.PersonalRecord;


@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository -----
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	//Supporting Services -----
	
	@Autowired
	private CurriculaService curriculaService; 
	
	//Simple CRUD methods -----
	public PersonalRecord create(){

		PersonalRecord res = new PersonalRecord();
		return res;
	}
	
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int Id){
		return personalRecordRepository.findOne(Id);
	}
	
	public PersonalRecord save(PersonalRecord a){
		
		//si el HandyWorker tiene una curricula se le guarda/actualiza el PR, si no simplemente se guarda PR sin vincular.
		
		PersonalRecord res = null;
		boolean isInCurricula = false;
		boolean hasCurricula = false;
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		UserAccount logged = LoginService.getPrincipal();
		
		for (Curricula c : curriculaService.findAll()) {
			if(c.getPersonalRecord().equals(a)){//vemos si esta dentro de alguna curricula
				if(c.getHandyWorker().getUserAccount().equals(logged)){//y que el dueño es el logueado
					res = personalRecordRepository.saveAndFlush(a);//actualizamos la curricula
				}
					isInCurricula=true;
					//System.out.println("Se ha encontrado una curricula que contiene al personalRecord, se actualiza ese record.");
			}
		}
		if (!isInCurricula){// si no tiene curricula asignada
			for (Curricula cu : curriculaService.findAll()) {
				if(cu.getHandyWorker().getUserAccount().equals(logged)){
					res = personalRecordRepository.saveAndFlush(a);
					cu.setPersonalRecord(res);
					curriculaService.save(cu);
					hasCurricula = true;
					//System.out.println("se ha encontrado una curricula para el usuario logueado, se le asigna el personalrecord");
				}
			}
		}
			if(!hasCurricula){
				res = personalRecordRepository.saveAndFlush(a);
				//System.out.println("no existe una curricula para el handyworker logueado, se guarda el personal record sin asignar.");
			}
		Assert.notNull(res);
		return res;
	}
	
	public void delete(PersonalRecord a){
		
		Assert.isTrue(LoginService.hasRole("HANDYWORKER"));
		boolean hasCurricula = false;
		//vemos si existe la curricula, si, si llamamos al delete de curricula si no borramos el personalrecord
		for (Curricula c : curriculaService.findAll()) {
			if(c.getPersonalRecord().equals(a)){
				curriculaService.delete(c);
				hasCurricula = true;
			}
		}
		if(!hasCurricula){
			personalRecordRepository.delete(a);
		}
	}
	
	//Other business methods -----
	
	
}