package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WordRepository;
import security.Authority;
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
	

	
	//Simple CRUD methods -----
	
	public Word create(){
		Word res = new Word();
		
		return res;
	}
	
	public Collection<Word> findAll(){
		return wordRepository.findAll();
	}
	
	public Word findOne(int Id){
		return wordRepository.findOne(Id);
	}
	
	public Word save(Word a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		return wordRepository.saveAndFlush(a);
	}
	
	public void delete(Word a){
		UserAccount userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		wordRepository.delete(a);
	}
	
	//Other business methods -----
	
	public Collection<Word> findSpamWords(){
		return wordRepository.findSpamWords();
	}
	
	public Collection<Word> findPositiveWords(){
		return wordRepository.findPositiveWords();
	}
	
	public Collection<Word> findNegativeWords(){
		return wordRepository.findNegativeWords();
	}
	
}