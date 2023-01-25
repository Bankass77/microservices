package microservices.practical.gamification.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public final class GameStats implements Serializable {

	private final Long userId;
	private final int score;
	private final List<Badge> badges;

	// empty Constructor for JPA and JSON

	public GameStats() {
		this.userId = 0L;
		this.badges = new ArrayList<>();
		this.score = 0;
	}

	/**
	 * Factory method to build an empty instance(Zero points and no badge.
	 * @param userId  the user's id
	 * @return a {@link GameStats} object with zero score and no badges
	 */
	 public  static  GameStats  emptyStats(final Long userId) {
		 
		 return new GameStats(userId,0, Collections.emptyList());
	 }
	 
	 
	 /**
	  * 
	  * @return an unmodifiable view of the badge cards list
	  */
	 private List<Badge> getBages(){
		 
		 return Collections.unmodifiableList(badges);
	 }
}
