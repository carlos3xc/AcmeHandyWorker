package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	
	//Simple CRUD methods -----
	public Note create(){
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
	
	public Note save(Note n){
		//puede necesitarse control de versiones por concurrencia del objeto.
		Note saved;
		Authority a= new Authority();
		Authority b= new Authority();
		Authority c= new Authority();
		a.setAuthority("REFEREE");
		b.setAuthority("HANDYWORKER");
		c.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(a)|
				userAccount.getAuthorities().contains(b)|
				userAccount.getAuthorities().contains(c));	
		
		if(userAccount.getAuthorities().contains(a)){
			Assert.isTrue(!(n.getRefereeComment() == ""));
		}else if(userAccount.getAuthorities().contains(c)){
			Assert.isTrue(!(n.getCustomerComment() == ""));
		}else if(userAccount.getAuthorities().contains(b)){
			Assert.isTrue(!(n.getHandyWorkerComment() == ""));
		}
		Date current = new Date();
		long millis;
		millis = System.currentTimeMillis() - 1000;
		current = new Date(millis);
		
		n.setMoment(current);

		saved = noteRepository.save(n);
		return saved;
	}
	
	public void delete(Note n){
		
		Authority a= new Authority();
		Authority b= new Authority();
		Authority c= new Authority();
		a.setAuthority("REFEREE");
		b.setAuthority("HANDYWORKER");
		c.setAuthority("CUSTOMER");
		UserAccount userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().contains(a)|
				userAccount.getAuthorities().contains(b)|
				userAccount.getAuthorities().contains(c));	

			
		noteRepository.delete(n);
	}
	
	//Other business methods -----
	
	
}