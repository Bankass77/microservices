package microservices.practical.gamification.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.practical.gamification.domain.BadgeCard;


/**
 * 
 * 
 * Handles data operations with BadgeCards
 *
 */
@Repository
public interface BadgeCardRepository extends CrudRepository<BadgeCard, Long> {

	/*
	 * Retrieves all badgeCards for a given user.
	 * 
	 * @param userId the id of the user to look for BadgeCards
	 * 
	 * @Return the list of BadgeCards, stored by most recent.
	 */
	List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final Long userId);

}
