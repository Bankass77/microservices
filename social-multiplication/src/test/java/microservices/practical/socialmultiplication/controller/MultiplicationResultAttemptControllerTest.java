
package microservices.practical.socialmultiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.practical.socialmultiplication.domain.Multiplication;
import microservices.practical.socialmultiplication.domain.MultiplicationResultAttempt;
import microservices.practical.socialmultiplication.domain.User;
import microservices.practical.socialmultiplication.service.MultiplicationService;

@WebMvcTest(controllers = MultiplicationResultAttemptController.class)

@ActiveProfiles("dev")
class MultiplicationResultAttemptControllerTest {

	@MockBean
	private MultiplicationService multiplicationService;

	@Autowired
	private MockMvc mvc;

	// this object will m agically initialized by the initFields method below
	private JacksonTester<MultiplicationResultAttempt> jsonMultiplicationAttempt;

	private JacksonTester<List<MultiplicationResultAttempt>> jsonMultResutAttemptList;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {

		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void postresultReturnNotCorrect() throws Exception {

		genericParameterizedTest(false);
	}

	private void genericParameterizedTest(final boolean b) throws Exception {

		// given (remember we're not testing here the service itself)

		given(multiplicationService.checkAttempt(Mockito.any(MultiplicationResultAttempt.class))).willReturn(b);
		User user = new User("Fatoumata");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, b);

		// .When
		MockHttpServletResponse response = mvc
				.perform(post("/results").contentType(MediaType.APPLICATION_JSON).content(jsonMultiplicationAttempt.write(attempt).getJson()))
				.andReturn().getResponse();

		// then

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonMultiplicationAttempt
				.write(new MultiplicationResultAttempt(attempt.getUser(), attempt.getMultiplication(), attempt.getResultAttempt(), b)).getJson());

	}

	@Test
	public void getUserStats() throws Exception { // given
		User user = new User("Fatoumata");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
		given(multiplicationService.getStatsForUser("Fatoumata")).willReturn(recentAttempts);

		// when
		MockHttpServletResponse response = mvc.perform(get("/results").param("alias", "Fatoumata")).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonMultResutAttemptList.write(recentAttempts).getJson());
	}

	@Test
	public void getResultByIdTest() throws Exception { // given
		User user = new User("Fatoumata");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, true);
		given(multiplicationService.getResultById(1L)).willReturn(attempt);

		// when
		MockHttpServletResponse response = mvc.perform(get("/results/1")).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonMultiplicationAttempt.write(attempt).getJson());
	}

}
