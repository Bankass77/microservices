package microservices.practical.gamification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.practical.gamification.domain.LeaderBoardRow;
import microservices.practical.gamification.service.LeaderBoardService;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {

	private final LeaderBoardService leaderBoardService;

	@Autowired
	public LeaderBoardController(final LeaderBoardService leaderBoardService) {
		super();
		this.leaderBoardService = leaderBoardService;
	}

	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard() {

		return leaderBoardService.getCurrentLeaderBoard();
	}

}
