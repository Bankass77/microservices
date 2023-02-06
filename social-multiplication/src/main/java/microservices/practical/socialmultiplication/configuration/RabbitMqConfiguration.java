package microservices.practical.socialmultiplication.configuration;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Configures RabbitMqConfiguration to use events in our application
 *
 */
@Configuration
@Slf4j
@EnableRabbit
public class RabbitMqConfiguration {

	@Bean
	public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {

		return new TopicExchange(exchangeName);
	}

	@Bean
	public RabbitTemplate template(final ConnectionFactory factory) {

		final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
		rabbitTemplate.setMessageConverter(producerJacksonMessageConverter());
		rabbitTemplate.setUseDirectReplyToContainer(false);

		return rabbitTemplate;

	}

	private Jackson2JsonMessageConverter producerJacksonMessageConverter() {

		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public ErrorHandler errorHandler() {
		return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
	}

	public static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

		@Override
		public boolean isFatal(Throwable t) {

			if (t instanceof ListenerExecutionFailedException) {
				ListenerExecutionFailedException e = (ListenerExecutionFailedException) t;
				log.error("Failed to process inbound message from queue " + e.getFailedMessage().getMessageProperties().getConsumerQueue()
						+ "; failed message: " + e.getFailedMessage(), t);
			}
			return super.isFatal(t);

		}
	}
}
