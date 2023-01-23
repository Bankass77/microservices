package microservices.practical.socialmultiplication.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {
	
	private RabbitTemplate rabbitTemplate;
	
	
	// The exchange to use to send anything related to Multiplication
	private String multiplicationExchange;
	
	// The routing key to use to send this particular event 
	private String multiplicationRoutingKey;

	@Autowired
	public EventDispatcher(RabbitTemplate rabbitTemplate,@Value("${multiplication.exchange}") String multiplicationExchange,@Value("${multiplication.solved.key}") String multiplicationRoutingKey) {
		super();
		this.rabbitTemplate = rabbitTemplate;
		this.multiplicationExchange = multiplicationExchange;
		this.multiplicationRoutingKey = multiplicationRoutingKey;
	}
	
	
	public void send(final MultiplicationSolvedEvent multiplicationSolvedEvent) {
		
		rabbitTemplate.convertAndSend(multiplicationExchange, multiplicationRoutingKey, multiplicationSolvedEvent);
	}

}
