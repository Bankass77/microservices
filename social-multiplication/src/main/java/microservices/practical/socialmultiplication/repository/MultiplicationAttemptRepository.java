package microservices.practical.socialmultiplication.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.practical.socialmultiplication.domain.MultiplicationResultAttempt;


@Repository
public interface MultiplicationAttemptRepository extends CrudRepository<MultiplicationResultAttempt, Long> {

	/**
	 * @return the latest 5 attempts for a given user, identified by their
	 *         alias.
	 */
	List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);

}
