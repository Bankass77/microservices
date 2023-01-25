package microservices.practical.gamification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices.practical.gamification.domain.LeaderBoardRow;
import microservices.practical.gamification.repository.ScoreCardRepository;
import microservices.practical.gamification.service.LeaderBoardService;

@Slf4j
@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

	private ScoreCardRepository scoreCardRepository;

	@Autowired
	public LeaderBoardServiceImpl(ScoreCardRepository scoreCardRepository) {
		super();
		this.scoreCardRepository = scoreCardRepository;
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {

		return scoreCardRepository.findFirst10();
	}

}
