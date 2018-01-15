package com.creditsuisse.trade.validation.test.integration;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.creditsuisse.trade.validation.model.domain.Spot;
import com.creditsuisse.trade.validation.model.domain.Trade;
import com.creditsuisse.trade.validation.model.messages.ErrorMessage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:timeout.properties")
public class FaultedTradeValidationControllerTest extends BaseIntegrationTest {
	@Test
	public void shouldReportGenericErrorOnFixerTimeout() {
		// given
		final Trade[] request = new Trade[] {
				new Spot( UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2017, 8, 11 ), 1.1, 2.2, 1.12, "CS Zurich", "Johny Bravo", constructDate( 2017, 8, 15 ) )
		};

		// when
		final ResponseEntity<ErrorMessage> response = restTemplate.postForEntity(
				"/v1/trade/validate", request, ErrorMessage.class
		);

		// then
		Assert.assertEquals( HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode() );
		Assert.assertNotNull( response.getBody().getId() );
		Assert.assertNotNull( response.getBody().getCode() );
		Assert.assertNotNull( response.getBody().getTimestamp() );
		Assert.assertNull( getFixerCache().get( fixerCacheKey( constructDate( 2017, 8, 15 ), "EUR" ) ) );
	}
}
