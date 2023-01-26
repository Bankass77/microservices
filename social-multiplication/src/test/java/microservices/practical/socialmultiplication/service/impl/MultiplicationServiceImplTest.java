
package microservices.practical.socialmultiplication.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import microservices.practical.socialmultiplication.domain.Multiplication;
import microservices.practical.socialmultiplication.domain.MultiplicationResultAttempt;
import microservices.practical.socialmultiplication.domain.User;
import microservices.practical.socialmultiplication.events.EventDispatcher;
import microservices.practical.socialmultiplication.repository.MultiplicationAttemptRepository;
import microservices.practical.socialmultiplication.repository.UserRepository;
import microservices.practical.socialmultiplication.service.RandomGeneratorService;
import static org.mockito.BDDMockito.given;

@SpringBootTest

@PropertySource("classpath:/src/main/resources/application-{profile}.properties")
class MultiplicationServiceImplTest {

	private MultiplicationServiceImpl multiplicationServiceImpl;

	@Mock
	private RandomGeneratorService randomGeneratorService;

	@Mock
	private MultiplicationAttemptRepository attemptRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private EventDispatcher eventDispatcher;

	@BeforeEach
	public void setUp() { // With this call to initMocks we tell Mockito to
							// process the annotations

		multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, eventDispatcher);
	}

	@Test
	public void createRandomMultiplicationTest() { // given (our mocked Random
													// Generator service will
													// return first 50, then 30)
		given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

		// when
		Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

		// then
		assertThat(multiplication.getFactorA()).isEqualTo(50);
		assertThat(multiplication.getFactorB()).isEqualTo(30);
	}

	@Test
	public void checkCorrectAttemptTest() { // given
		Multiplication multiplication = new Multiplication(50, 60);
		User user = new User("Fatoumata");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
		given(userRepository.findByAlias("Fatoumata")).willReturn(Optional.empty());

		// when boolean attemptResult =
		multiplicationServiceImpl.checkAttempt(attempt);

		// then assertThat(attemptResult).isTrue();
		verify(attemptRepository).save(verifiedAttempt);
	}

	@Test
	public void checkWrongAttemptTest() { // given
		Multiplication

		multiplication = new Multiplication(50, 60);
		User user = new User("Fatoumata");
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		given(userRepository.findByAlias("Fatoumata")).willReturn(Optional.empty());

		// when boolean attemptResult =
		multiplicationServiceImpl.checkAttempt(attempt);

		// then assertThat(attemptResult).isFalse();
		verify(attemptRepository.save(attempt));
	}

	@Test
	public void retrieveStatsTest() { // given
		Multiplication

		multiplication = new Multiplication(50, 60);
		User user = new User("Fatoumata");
		MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);
		List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
		given(userRepository.findByAlias("Fatoumata")).willReturn(Optional.empty());
		given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("Fatoumata")).willReturn(latestAttempts);

		// when
		List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("Fatoumata");

		// then
		assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
	}

}
