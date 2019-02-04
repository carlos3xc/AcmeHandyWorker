package repositories;

import domain.Vaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface VasteRepository extends JpaRepository<Vaste, Integer> {


    /*a1. Published vastes per Customer: AVG and STDDEV*/

    @Query("select avg(fix.publishedVastes.size) from FixUpTask fix")
    Double getAvgPublishedVastesPerFixUpTask();

    @Query("select sqrt((sum(fix.publishedVastes.size)*sum(fix.publishedVastes.size)*1.0)/coalesce(count(fix)*1.0,1) - avg(fix.publishedVastes.size)*avg(fix.publishedVastes.size))*1.0 from FixUpTask fix")
    Double getStddevPublishedVastesPerFixUpTask();

    /*b. Ratio of published Quolets per FixUpTask*/

    @Query("select count(vas)*1.0/(select coalesce(count(vas),1) from FixUpTask vas) from Vaste vas where vas.publicationMoment is not null")
    Double getRatioPublishedQuoletsPerFixUpTask();

    /*c. Ratio of unpublished Quolets per FixUpTask*/

    @Query("select count(vas)*1.0/(select coalesce(count(vas),1) from FixUpTask vas) from Vaste vas where vas.publicationMoment is null")
    Double getRatioUnpublishedQuoletsPerFixUpTask();

    /*AUX Queries ---------- */

    @Query("select vas from Vaste vas where vas.fixUpTask.id = ?1 and vas.publicationMoment is not null")
    Collection<Vaste> findPublishedByFixUpTaskId(int fixUpTaskId);

    @Query("select vas from Vaste vas where vas.customer.id = ?1")
    Collection<Vaste> findQuoletByCustomerId(int customerId);

}
