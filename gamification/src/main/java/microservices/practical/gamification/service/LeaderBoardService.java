package microservices.practical.gamification.service;

import java.util.List;

import microservices.practical.gamification.domain.LeaderBoardRow;

public interface LeaderBoardService {

	/**
	 * Retrieves the current leader board with the top score users
	 * 
	 * @return the users with the highest score.
	 */
	List<LeaderBoardRow> getCurrentLeaderBoard();

}
