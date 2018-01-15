package com.creditsuisse.trade.validation.test.integration;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.creditsuisse.trade.validation.model.domain.Forward;
import com.creditsuisse.trade.validation.model.domain.Spot;
import com.creditsuisse.trade.validation.model.domain.Trade;
import com.creditsuisse.trade.validation.model.domain.ValidationError;
import com.creditsuisse.trade.validation.model.domain.ValidationFeedback;
import com.creditsuisse.trade.validation.model.domain.VanillaOption;
import com.creditsuisse.trade.validation.model.domain.types.PremiumType;
import com.creditsuisse.trade.validation.model.domain.types.Strategy;
import com.creditsuisse.trade.validation.model.domain.types.ValidationStatus;
import com.creditsuisse.trade.validation.validators.ErrorFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class TradeValidationControllerTest extends BaseIntegrationTest {
	@Test
	public void shouldAllowMultipleCorrectRequests() {
		// given
		final Trade[] request = new Trade[] {
				new Spot( UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12, "CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 ) ),
				new Forward( UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 12 ), 10.1, 20.2, 10.12, "CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 ) ),
				new VanillaOption( UUID.randomUUID().toString(), "PLUTO2", "EURUSD", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich", "Johny Bravo", "EUROPEAN", Strategy.CALL,
								   constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR", 1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), null
				),
				new VanillaOption( UUID.randomUUID().toString(), "PLUTO2", "EURUSD", constructDate( 2016, 8, 12 ), 20.12, 30.22, 40.42, "CS Zurich", "Johny Bravo", "AMERICAN", Strategy.CALL,
								   constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR", 1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), constructDate( 2016, 8, 13 )
				)
		};

		// when
		final ResponseEntity<ValidationFeedback[]> response = restTemplate.postForEntity(
				"/v1/trade/validate", request, ValidationFeedback[].class
		);

		// then
		final ValidationFeedback[] validationFeedback = response.getBody();
		Assert.assertEquals( HttpStatus.OK, response.getStatusCode() );
		for ( int i = 0; i < validationFeedback.length; ++i ) {
			final ValidationFeedback feedback = validationFeedback[i];
			Assert.assertEquals( request[i].getId(), feedback.getId() );
			Assert.assertEquals( ValidationStatus.OK, feedback.getStatus() );
			Assert.assertNull( feedback.getReasons() );
		}
		Assert.assertNotNull( getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 15 ), "EUR" ) ).get() );
		Assert.assertNotNull(getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 15 ), "USD" ) ).get() );
	}

	@Test
	public void shouldReportInvalidRequests() {
		// given
		final Trade[] request = new Trade[] {
				new Spot( UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12, "CS Zurich", "Johny Bravo", constructDate( 2016, 8, 18 ) ),
				new Forward( UUID.randomUUID().toString(), "PLUTO3", "XYZUSD", constructDate( 2016, 8, 12 ), 10.1, 20.2, 10.12, "CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 ) ),
				new VanillaOption( UUID.randomUUID().toString(), "PLUTO2", "EURUSD", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich", "Johny Bravo", "INVALID", Strategy.CALL,
								   constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR", 1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), null
				),
				new VanillaOption( UUID.randomUUID().toString(), "PLUTO2", "EURUSD", constructDate( 2016, 8, 12 ), 20.12, 30.22, 40.42, "CS Zurich", "Johny Bravo", "AMERICAN", Strategy.CALL,
								   constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR", 1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), constructDate( 2016, 8, 17 )
				)
		};
		final ValidationError[][] expectedErrors = new  ValidationError[][] {
				new ValidationError[] { ErrorFactory.nonWorkingValueDateForCurrency( "EUR" ), ErrorFactory.nonWorkingValueDateForCurrency( "USD" ) },
				new ValidationError[] { ErrorFactory.invalidCurrency(), ErrorFactory.unsupportedCustomer() },
				new ValidationError[] { ErrorFactory.invalidOptionStyle() },
				new ValidationError[] { ErrorFactory.exerciseStartDateNotBetweenTradeAndExpiry() }
		};

		// when
		final ResponseEntity<ValidationFeedback[]> response = restTemplate.postForEntity(
				"/v1/trade/validate", request, ValidationFeedback[].class
		);

		// then
		final ValidationFeedback[] validationFeedback = response.getBody();
		Assert.assertEquals( HttpStatus.OK, response.getStatusCode() );
		for ( int i = 0; i < validationFeedback.length; ++i ) {
			final ValidationFeedback feedback = validationFeedback[i];
			Assert.assertEquals( ValidationStatus.ERROR, feedback.getStatus() );
			Assert.assertArrayEquals( expectedErrors[i], feedback.getReasons() );
		}
		Assert.assertNotNull( getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 18 ), "EUR" ) ).get() );
		Assert.assertNotNull( getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 18 ), "USD" ) ).get() );
		Assert.assertNotNull( getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 15 ), "USD" ) ).get() );
		Assert.assertNull( getFixerCache().get( fixerCacheKey( constructDate( 2016, 8, 15 ), "XYZ" ) ) );
	}
}
