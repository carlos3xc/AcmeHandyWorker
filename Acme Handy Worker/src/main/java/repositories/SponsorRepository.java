package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import security.UserAccount;
=======
>>>>>>> 24df8365044bf859bca0f2c5a25065ad2c644294
import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{
	
	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findSponsorByUserAccount(int id);
}
