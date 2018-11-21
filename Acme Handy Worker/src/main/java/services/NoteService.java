package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;


@Service
@Transactional
public class NoteService {

	//Managed Repository -----
	@Autowired
	private NoteRepository noteRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public NoteService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Note create(){
		
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(Authority.REFEREE)|
				userAccount.getAuthorities().contains(Authority.CUSTOMER)|
				userAccount.getAuthorities().contains(Authority.HANDYWORKER));	
		Note res;
		res = new Note();
		return res;
	}
	
	public Collection<Note> findAll(){
		return noteRepository.findAll();
	}
	
	public Note findOne(int Id){
		return noteRepository.findOne(Id);
	}
	
	public Note save(Note a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		
		UserAccount userAccount = LoginService.getPrincipal();
		
		Assert.isTrue(userAccount.getAuthorities().contains(Authority.REFEREE)|
					userAccount.getAuthorities().contains(Authority.CUSTOMER)|
					userAccount.getAuthorities().contains(Authority.HANDYWORKER));
		
		if(userAccount.getAuthorities().contains(Authority.REFEREE)){
			Assert.isTrue(!(a.getRefereeComment() == ""));
		}else if(userAccount.getAuthorities().contains(Authority.CUSTOMER)){
			Assert.isTrue(!(a.getCustomerComment() == ""));
		}else if(userAccount.getAuthorities().contains(Authority.HANDYWORKER)){
			Assert.isTrue(!(a.getHandyWorkerComment() == ""));
		}
		Date current = new Date();
		a.setMoment(current);

		
		noteRepository.save(a);
		return a;
	}
	
	public void delete(Note a){
		
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(Authority.REFEREE)|
				userAccount.getAuthorities().contains(Authority.CUSTOMER)|
				userAccount.getAuthorities().contains(Authority.HANDYWORKER));
		
		//  BIDIRECCIONALIDAD, revisar si hay que borrarlo también de la lista de reports
		
		noteRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}