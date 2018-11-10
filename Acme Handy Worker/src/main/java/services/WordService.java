package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Word;


@Service
@Transactional
public class WordService {

	//Managed Repository -----
	@Autowired
	private WordRepository wordRepository;
	
	//Supporting Services -----
	
	//@Autowired
	//private SomeService serviceName 
	
	//Constructors -----
	public WordService(){
		super();
	}
	
	//Simple CRUD methods -----
	public Word create(){
		//Metodo general para todas los servicios, es probable 
		//que sea necesario añadir atributos consistentes con la entity.
		Word res = new Word();
		res.setWord("");
		res.setType("SPAM");
		return res;
	}
	
	public Collection<Word> findAll(){
		return wordRepository.findAll();
	}
	
	public Word findOne(int Id){
		return wordRepository.findOne(Id);
	}
	
	public Word save(Word a){
		//puede necesitarse control de versiones por concurrencia del objeto.
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		wordRepository.save(a);
		return a;
	}
	
	public void delete(Word a){
		//puede necesitarse comprobar que el usuario que va a guardar el objeto es el dueño
		Assert.isTrue(true);//modificar para condiciones especificas.(data constraint)
		
		UserAccount userAccount = LoginService.getPrincipal();
		// modificar para aplicarlo a la entidad correspondiente.
		//Assert.isTrue(a.getUserAccount().equals(userAccount));
		
		wordRepository.delete(a);
	}
	
	//Other business methods -----
	public void createSpamWord(String s){
		Word w = this.create();
		w.setType("SPAM");
		w.setWord(s);
		this.save(w);
		
	}
	
	public Collection<Word> findSpamWords(){
		return wordRepository.findSpamWords();
	}
	
}