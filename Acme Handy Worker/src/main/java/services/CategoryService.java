package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.LoginService;
import security.UserAccount;
import domain.Category;


@Service
@Transactional
public class CategoryService {

	//Managed Repository -----
	@Autowired
	private CategoryRepository categoryRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public CategoryService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Category create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Category res = new Category();
		return res;
	}
	
	public Collection<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Category findOne(int Id){
		return categoryRepository.findOne(Id);
	}
	
	public Category save(Category a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		categoryRepository.save(a);
		return a;
	}
	
	public void delete(Category a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		categoryRepository.delete(a);
	}
	
	//Other business methods -----
	
	
}