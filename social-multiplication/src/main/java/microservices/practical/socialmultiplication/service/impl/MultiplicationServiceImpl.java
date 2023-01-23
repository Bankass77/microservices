package microservices.practical.socialmultiplication.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import jakarta.transaction.Transactional;
import microservices.practical.socialmultiplication.domain.Multiplication;
import microservices.practical.socialmultiplication.domain.MultiplicationResultAttempt;
import microservices.practical.socialmultiplication.domain.User;
import microservices.practical.socialmultiplication.events.EventDispatcher;
import microservices.practical.socialmultiplication.events.MultiplicationSolvedEvent;
import microservices.practical.socialmultiplication.repository.MultiplicationAttemptRepository;
import microservices.practical.socialmultiplication.repository.UserRepository;
import microservices.practical.socialmultiplication.service.MultiplicationService;
import microservices.practical.socialmultiplication.service.RandomGeneratorService;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

	private RandomGeneratorService randomGeneratorService;
	private MultiplicationAttemptRepository attemptRepository;
	private UserRepository userRepository;
	private EventDispatcher eventDispatcher;

	@Autowired
	public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService, final MultiplicationAttemptRepository attemptRepository,
			final UserRepository userRepository, EventDispatcher eventDispatcher) {
		this.randomGeneratorService = randomGeneratorService;
		this.attemptRepository = attemptRepository;
		this.userRepository = userRepository;
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	public Multiplication createRandomMultiplication() {
		int factorA = randomGeneratorService.generateRandomFactor();
		int factorB = randomGeneratorService.generateRandomFactor();
		return new Multiplication(factorA, factorB);
	}

	@Transactional
	@Override
	public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
		// Check if the user already exists for that alias
		Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

		// Avoids 'hack' attempts
		Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

		// Check if the attempt is correct
		boolean isCorrect = attempt.getResultAttempt() == attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();

		MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(attempt.getUser()), attempt.getMultiplication(),
				attempt.getResultAttempt(), isCorrect);

		// Stores the attempt
		attemptRepository.save(checkedAttempt);
		// communicate the result via Event

		eventDispatcher.send(new MultiplicationSolvedEvent(checkedAttempt.getId(), checkedAttempt.getUser().getId(), checkedAttempt.isCorrect()));
		return isCorrect;
	}

	@Override
	public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
		return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
	}

}
