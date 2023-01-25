package microservices.practical.gamification.client;

import org.hibernate.sql.Alias;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * 
 * Identifies the attempt from a user to solve a multiplication
 *
 */
@ToString
@Getter
@EqualsAndHashCode
@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
@RequiredArgsConstructor
public final class MultiplicationResultAttempt {

	private final String userAlias;
	private final int multplicationFactorA;
	private final int multiplicationFactorB;
	private final int resultAttempt;
	private final boolean correct;

	// Empty constructor for JSON and JPA
	public MultiplicationResultAttempt() {
		super();
		userAlias = null;
		multiplicationFactorB = -1;
		multplicationFactorA = -1;
		resultAttempt = -1;
		correct = false;
	}

}
