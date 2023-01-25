package microservices.practical.gamification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservices.practical.gamification.domain.LeaderBoardRow;
import microservices.practical.gamification.domain.ScoreCard;

/**
 * 
 * coreCards
 *
 */

@Repository
public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

	/**
	 * 
	 * Gets the total score for a given user, being the sum of the scores of all
	 * his ScoreCards.
	 * 
	 * @param userId
	 *            the id of the user for which the total score shpould be
	 *            retrieved
	 * @Return the total score for the given user
	 */
	@Query("SELECT SUM(s.score) FROM microservices.practical.gamification.domain.ScoreCard s WHERE s.userID= :userId GROUP BY s.userId")
	int getTotalScoreForUser(@Param("userId") final Long userId);

	@Query("SELECT NEW microservices.practical.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score))"
			+ "FROM microservices.practical.gamification.domain.ScoreCard s" + "GROUP BY s.userId ORDER BY SUM(s.score) DESC")
	List<LeaderBoardRow> findFirst10();

	/**
	 * Retrieves all the ScoreCards for a given user, identified bty this user
	 * id.
	 * 
	 * @param userId
	 *            the id of the user
	 * @return a list containing all the ScoreCards for the given user, sorted
	 *         by most recent
	 */
	List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);

}
