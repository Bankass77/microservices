package microservices.practical.socialmultiplication.domain;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Multiplication}.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class MultiplicationResultAttempt implements Serializable{
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private final User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "MULTIPLICATION_ID")
	private final Multiplication multiplication;
	private final int resultAttempt;

	private final boolean correct;

	// Empty constructor for JSON/JPA
	MultiplicationResultAttempt() {
		user = null;
		multiplication = null;
		resultAttempt = -1;
		correct = false;
	}

}
