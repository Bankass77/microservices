package microservices.practical.gamification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration implements RabbitListenerConfigurer {

	@Bean
	public TopicExchange multiplicationExchange(@Value("${multiplication.exchange}") final String exchangeName) {

		return new TopicExchange(exchangeName);
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {

		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	public Queue gamlificationMultipicationQueue(@Value("${multiplication.queue}") final String queueName) {

		return new Queue(queueName, true);
	}

	@Bean
	public Binding binding(final Queue queue, final TopicExchange topicExchange,
			@Value("${multiplication.anything.routing-key}") final String routingKey) {

		return BindingBuilder.bind(queue).to(topicExchange).with(routingKey);
	}

	@Bean
	public MappingJackson2MessageConverter consumerjackson2MessageConverter() {
		return new MappingJackson2MessageConverter();

	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerjackson2MessageConverter());
		return factory;
	}

}
