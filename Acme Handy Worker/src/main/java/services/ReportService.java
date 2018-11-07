package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
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
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
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
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		reportRepository.save(a);
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