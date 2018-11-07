package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer>{

	// no es necesario viene por defecto esta como referencia
	@Query("select a from Note a where a.id = ?1") 
	Note findOne(Integer Id);
}
