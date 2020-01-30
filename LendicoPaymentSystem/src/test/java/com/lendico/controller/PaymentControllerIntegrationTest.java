package com.lendico.controller;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.Application;

	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class PaymentControllerIntegrationTest {

		@LocalServerPort
		private int port;

		@Value("classpath:mockModel/repaymentPlan.json")
		Resource resource;

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();

		@Before
		public void setUp() {

		}

		@Test
		public void testGetRepaymentPlan() throws Exception {
			String expected = getJsonModel();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			String getUserURI = "http://localhost:" + port + "/repaymentPlan";
			ResponseEntity<String> response = restTemplate.exchange(getUserURI, HttpMethod.POST, entity, String.class);
			Assert.assertNotEquals(expected, response.getBody());
		}

		@After
		public void teardown() {

		}

		private String getJsonModel() throws IOException {
			ObjectMapper mapperObj = new ObjectMapper();
			Object readValue = mapperObj.readValue(resource.getInputStream(), Object.class);
			return mapperObj.writeValueAsString(readValue);
		}
	}


