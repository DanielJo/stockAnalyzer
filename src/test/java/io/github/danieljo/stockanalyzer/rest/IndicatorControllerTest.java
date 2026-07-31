package io.github.danieljo.stockanalyzer.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(IndicatorController.class)
class IndicatorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void listsEveryIndicator() throws Exception {
		mockMvc.perform(get("/api/indicators"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(137))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].displayName").value("Simple Moving Average"));
	}
}
