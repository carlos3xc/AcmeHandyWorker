package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;
import domain.Referee;
import domain.Report;


@Service
@Transactional
public class ReportService {

	//Managed Repository -----
	@Autowired
	private ReportRepository reportRepository;
	
	// Support repositories -------
	
	@Autowired
	private RefereeRepository refereeRepository;
	
	//Simple CRUD methods -----
	public Report create(){
		Report res = new Report();
		res.setAttachments(new ArrayList<String>());
		res.setNotes(new ArrayList<Note>());
		return res;
	}
	
	public Collection<Report> findAll(){
		return reportRepository.findAll();
	}
	
	public Report findOne(int Id){
		return reportRepository.findOne(Id);
	}
	
	public Report save(Report r){
		Report saved;
		Report rDatabase = reportRepository.findOne(r.getId());
		Authority e = new Authority();
		e.setAuthority("REFEREE");
		UserAccount userAccount = LoginService.getPrincipal();
		Referee rf = refereeRepository.findByUserAccountId(userAccount.getId());
		Assert.isTrue(userAccount.getAuthorities().contains(e));	
		
		Date current = new Date(System.currentTimeMillis() - 1000);
		
		r.setMoment(current);
		if(r.getIsDraft()==null) r.setIsDraft(true);
		if(rDatabase!=null) {
			Assert.isTrue(rDatabase.getIsDraft().equals(true));			// Comprobamos que el draft sea false. En el caso de que fuese true, no se podría actualizar
		}
			if(r.getReferee()==null)r.setReferee(rf);
		saved = reportRepository.save(r);
		return saved;
	}
	
	public Report saveAut(Report a){	
		Report saved;
		saved = reportRepository.save(a);
		reportRepository.flush();
		return saved;
	}
	
	
	public void delete(Report a){
		Authority e = new Authority();
		e.setAuthority("REFEREE");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));
		
		reportRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}