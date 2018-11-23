package services;

import java.util.Calendar;
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
import domain.Report;


@Service
@Transactional
public class NoteService {

	//Managed Repository -----
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ReportService reportService;
	
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

		Report r = n.getReport();
		Report saved;
		
		r.getNotes().remove(n);
		saved = reportService.saveAut(r);	
		noteRepository.delete(n);
		
		Assert.isTrue(!saved.getNotes().contains(n));			// Comprobamos que la nota se ha borrado de la lista 
	}
	
	//Other business methods -----
	

	
}