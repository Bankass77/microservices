package microservices.practical.socialmultiplication.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 
 *Configures RabbitMqConfiguration to use events in our application
 *
 */
@Configuration
public class RabbitMqConfiguration {
	
	@Bean
	public TopicExchange  multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {
		
		return new TopicExchange(exchangeName);
	}

	
	@Bean
	public RabbitTemplate template (final ConnectionFactory factory) {
		
		
		final RabbitTemplate rabbitTemplate= new RabbitTemplate(factory);
		rabbitTemplate.setMessageConverter(producerJacksonMessageConverter());
		
		return rabbitTemplate;
		
	}


	private Jackson2JsonMessageConverter producerJacksonMessageConverter() {
		
		return new Jackson2JsonMessageConverter() ;
	}
	
}
