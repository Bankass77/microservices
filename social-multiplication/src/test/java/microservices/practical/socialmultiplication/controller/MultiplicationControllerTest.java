package microservices.practical.socialmultiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.practical.socialmultiplication.domain.Multiplication;
import microservices.practical.socialmultiplication.service.MultiplicationService;

//@SpringBootTest
@WebMvcTest(controllers = MultiplicationController.class)
@ActiveProfiles("dev")
class MultiplicationControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	MockMvc mvc;

	// this object will be magically initialized by the initFields
	private JacksonTester<Multiplication> jsonMultiplication;

	@BeforeEach
	void setUp() throws Exception {

		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	void getRandomMultiplicationTest() throws Exception {

		// given

		given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70, 20));
	
	   // when
		
		MockHttpServletResponse response= mvc.perform(get("/multiplications/random").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
	
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonMultiplication.write(new Multiplication(70, 20)).getJson());
	  
	}

}
