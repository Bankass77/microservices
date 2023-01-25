package microservices.practical.gamification.events;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import microservices.practical.gamification.service.GameService;

/**
 * 
 * This class receives the events and triggers the associated business logic
 *
 */
@Component
@Slf4j
public class GamificationEventHandler {

	private GameService gameService;

	public GamificationEventHandler(final GameService gameService) {
		super();
		this.gameService = gameService;
	}

	@RabbitListener(queues = "${multiplication.queue}")
	public void handlerMultiplicationSolved(final MultiplicationSolvedEvent event) {

		log.info("Multiplication solved Event received: {}", event.getMultiplicationResultAttemptId());

		try {
			gameService.newAttemptForUser(event.getUserId(), event.getMultiplicationResultAttemptId(), event.isCorrect());
		} catch (Exception e) {

			log.error("Error when trying to process MultiplicationSolvedEvent", e);

			// Avoids the event to be re-queued and reprocessed

			throw new AmqpRejectAndDontRequeueException(e);
		}

	}

}
