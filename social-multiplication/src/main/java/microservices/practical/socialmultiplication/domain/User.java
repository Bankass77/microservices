package microservices.practical.socialmultiplication.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Stores information to identify the user.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class User implements Serializable{

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;

	private final String alias;

	// Empty constructor for JSON/JPA
	protected User() {
		alias = null;
	}

}
