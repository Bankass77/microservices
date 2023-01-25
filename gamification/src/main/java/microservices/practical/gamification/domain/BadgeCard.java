package microservices.practical.gamification.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
public final class BadgeCard implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BADGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private final Long badgeId;
	private final Long userId;
	private final long badgeTimestamp;
	private final Badge badge;

	// Empty constructor for JSON and JPA
	public BadgeCard() {
		this(null, null, 0, null);
	}

	public BadgeCard(final Long userId, final Badge badge) {

		this(null, userId, System.currentTimeMillis(), badge);
	}

}
