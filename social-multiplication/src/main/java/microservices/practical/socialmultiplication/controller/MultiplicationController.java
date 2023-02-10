package microservices.practical.socialmultiplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import microservices.practical.socialmultiplication.domain.Multiplication;
import microservices.practical.socialmultiplication.service.MultiplicationService;

@Slf4j
@RestController
@RequestMapping("/multiplications")
public class MultiplicationController {

	private final MultiplicationService multiplicationService;
	private final int serverPort;

	@Autowired
	public MultiplicationController(final MultiplicationService multiplicationService, @Value("${server.port}") int serverPort) {
		this.multiplicationService = multiplicationService;
		this.serverPort=serverPort;
	}

	@GetMapping("/random")
	Multiplication getRandomMultiplication() {
		
		log.info("Generating a random multiplication from server @{}", serverPort);
		return multiplicationService.createRandomMultiplication();
	}

}
