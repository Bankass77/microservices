package microservices.practical.socialmultiplication.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.practical.socialmultiplication.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);
}
