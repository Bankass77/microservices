package microservices.practical.gamification.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
public final class ScoreCard implements Serializable {

	private static final long serialVersionUID = 1L;

	// the default score assigned to this card, if not specified.
	public static final int DEFAULT_SCORE = 10;

	@Id
	@GeneratedValue
	@Column(name = "CARD_ID")
	private final Long cardId;

	@Column(name = "USER_ID")
	private final Long userId;

	@Column(name = "ATTEMPT_ID")
	private final Long attemptId;

	@Column(name = "SCORE_TS")
	private final long scoreTimestamp;

	@Column(name = "SCORE")
	private final int score;

	// empty constructor for JSON and JPA
	public ScoreCard() {

		this(null, null, null, 0, 0);
	}

	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
	}

}
