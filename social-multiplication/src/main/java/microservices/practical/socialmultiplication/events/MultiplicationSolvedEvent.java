package microservices.practical.socialmultiplication.events;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Event taht models the fact that a
 * {@link microservices.pratical.socialmultiplication.domain.Multiplication} has
 * been solved in the system. Provides some context information about
 * multiplication
 * 
 *
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationSolvedEvent  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final Long multiplicationResultAttemptId;
	private final Long userId;
	private final boolean correct;

}
