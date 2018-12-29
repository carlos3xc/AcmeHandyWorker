package repositories;

import domain.FixUpTask;
import domain.HandyWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;

import java.util.Collection;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer>{

	@Query("select fix from FixUpTask fix where (:keyword is not null and :keyword not like '' and fix.ticker like %:keyword% and fix.description like %:keyword% and fix.address like %:keyword%)" +
			"and (:minPrice is not null and fix.maxPrice >= :minPrice) and (:maxPrice is not null and fix.maxPrice <= :maxPrice)")
	Collection<FixUpTask> filterFixUpTasks(@Param("keyword") String keyword, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

	@Query("select f from Finder f where f.handyWorker.id = :handyWorkerId")
	HandyWorker findByHandyWorker(@Param("handyWorkerId") Integer handyWorkerId);
}
