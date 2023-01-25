package microservices.practical.gamification.client.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import microservices.practical.gamification.client.MultiplicationResultAttempt;

/**
 * 
 * this implementation of MultiplicationResultAttemptClient interface connect to
 * the Multiplication microservices via REST
 *
 */
@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

	private final RestTemplate restTemplate;
	private final String multiplicationHost;

	public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate, @Value("${multiplicationHost}") String multiplicationHost) {
		super();
		this.restTemplate = restTemplate;
		this.multiplicationHost = multiplicationHost;
	}

	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(Long multiplicationResultAttemptId) {

		return restTemplate.getForObject(multiplicationHost + "/results/" + multiplicationResultAttemptId, MultiplicationResultAttempt.class);
	}

}
