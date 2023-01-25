package microservices.practical.gamification.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class LeaderBoardRow implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Long userId;

	private final Long totalScore;

	
	// Empty constructor for JPA and Json
	public LeaderBoardRow() {

		this(0L, 0L);
	}
}
