package microservices.practical.gamification.client.dto;

import microservices.practical.gamification.client.MultiplicationResultAttempt;

public interface MultiplicationResultAttemptClient {
	
	MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationId);

}
