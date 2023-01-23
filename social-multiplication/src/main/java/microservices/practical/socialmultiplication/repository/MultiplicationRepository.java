package microservices.practical.socialmultiplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import microservices.practical.socialmultiplication.domain.Multiplication;

@Repository
public interface MultiplicationRepository  extends CrudRepository<Multiplication, Long>{

}
