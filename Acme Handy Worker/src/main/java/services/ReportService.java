package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Report;


@Service
@Transactional
public class ReportService {

	//Managed Repository -----
	@Autowired
	private ReportRepository reportRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public ReportService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Report create(){
		Report res = new Report();
		return res;
	}
	
	public Collection<Report> findAll(){
		return reportRepository.findAll();
	}
	
	public Report findOne(int Id){
		return reportRepository.findOne(Id);
	}
	
	public Report save(Report a){
		Authority e = new Authority();
		e.setAuthority("REFEREE");
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().contains(e));			
		reportRepository.save(a);
		return a;
	}
	
	public Report saveAut(Report a){	
		reportRepository.save(a);
		reportRepository.flush();
		return a;
	}
	
	
	public void delete(Report a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		reportRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}