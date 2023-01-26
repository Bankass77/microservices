package microservices.practical.gamification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.practical.gamification.client.MultiplicationResultAttempt;
import microservices.practical.gamification.client.dto.MultiplicationResultAttemptClient;
import microservices.practical.gamification.domain.Badge;
import microservices.practical.gamification.domain.BadgeCard;
import microservices.practical.gamification.domain.GameStats;
import microservices.practical.gamification.domain.ScoreCard;
import microservices.practical.gamification.repository.BadgeCardRepository;
import microservices.practical.gamification.repository.ScoreCardRepository;
import microservices.practical.gamification.service.GameService;

@Service
@Slf4j
public class GameServiceImpl implements GameService {

	private ScoreCardRepository scoreCardRepository;
	private BadgeCardRepository badgeCardRepository;
	private MultiplicationResultAttemptClient multiplicationResultAttemptClient;
	
	private static final int LUCKY_NUMBER=42;

	@Autowired
	public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository,MultiplicationResultAttemptClient multiplicationResultAttemptClient) {
		super();
		this.scoreCardRepository = scoreCardRepository;
		this.badgeCardRepository = badgeCardRepository;
		this.multiplicationResultAttemptClient=multiplicationResultAttemptClient;
	}

	@Override
	public GameStats newAttemptForUser(final Long userId, final Long attemptId, final boolean correct) {

		// For the first version we'll give points only if it's correct

		if (correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepository.save(scoreCard);
			log.info("User with id {} scored {} points for attemps id {}", userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);

			return new GameStats(userId, scoreCard.getScore(), badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));

		}
		return GameStats.emptyStats(userId);
	}

	/**
	 * Check the total score and the different score score cards obtained to
	 * give new badges in case their conditions are met
	 * 
	 * @param userId
	 * @param attemptId
	 * @return
	 */
	private List<BadgeCard> processForBadges(Long userId, Long attemptId) {

		List<BadgeCard> badgeCards = new ArrayList<>();

		int totalScore = scoreCardRepository.getTotalScoreForUser(userId);
		log.info("New Score for user {} is {}", userId, totalScore);

		List<ScoreCard> scoreCards = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCards2 = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		// Badges depending on score
		CheckGiveBadgeBasedOnScore(badgeCards2, Badge.BRONZE_MULTIPLICATOR, totalScore, 500, userId).ifPresent(badgeCards::add);

		CheckGiveBadgeBasedOnScore(badgeCards2, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId).ifPresent(badgeCards::add);

		CheckGiveBadgeBasedOnScore(badgeCards2, Badge.GOLD_MULTIPLICATOR, totalScore, 500, userId).ifPresent(badgeCards::add);

		// First won badge

		if (scoreCards.size() == 1 && !containsBadge(badgeCards2, Badge.FIRST_WON)) {

			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
			badgeCards.add(firstWonBadge);
		}
		
		// lucky number badge
		MultiplicationResultAttempt multiplicationResultAttempt=multiplicationResultAttemptClient.retrieveMultiplicationResultAttemptById(attemptId);
		if (!containsBadge(badgeCards2, Badge.LUCKY_NUMBER) && (LUCKY_NUMBER==multiplicationResultAttempt.getMultplicationFactorA()|| LUCKY_NUMBER==multiplicationResultAttempt.getMultiplicationFactorB())) {
			
			BadgeCard luckyNumberBadge= giveBadgeToUser(Badge.LUCKY_NUMBER, userId);
			badgeCards.add(luckyNumberBadge);
		}

		return badgeCards;
	}

	/**
	 * Assigns a new Badge to a given user
	 * 
	 * @param firstWon
	 * @param userId
	 * @return
	 */
	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {

		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepository.save(badgeCard);
		log.info("User with id {} won a new Badge:{}", userId, badge);
		return badgeCard;
	}

	/**
	 * check if the passed list of badges includes the one being checked
	 * 
	 * @param badgeCards
	 * @param badge
	 * @return
	 */
	private boolean containsBadge(List<BadgeCard> badgeCards, Badge badge) {

		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

	/**
	 * Convienence method to check the current score againsty the different
	 * thresholds to gain badges. It also assigns badges to user if the
	 * conditiont are met
	 * 
	 * @param badgeCards
	 * @param badge
	 * @param totalScore
	 * @param i
	 *            the total score of a given user
	 * @param userId
	 *            user's id
	 * @return
	 */
	private Optional<BadgeCard> CheckGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge, final int score,
			final int scoreThreshold, final Long userId) {

		if (score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		return Optional.empty();
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		int score = scoreCardRepository.getTotalScoreForUser(userId);

		List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

		return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

}
