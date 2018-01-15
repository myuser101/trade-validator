package com.creditsuisse.trade.validation.test.unit;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.creditsuisse.trade.validation.model.domain.Spot;
import com.creditsuisse.trade.validation.model.domain.ValidationError;
import com.creditsuisse.trade.validation.model.domain.VanillaOption;
import com.creditsuisse.trade.validation.model.domain.types.PremiumType;
import com.creditsuisse.trade.validation.model.domain.types.Strategy;
import com.creditsuisse.trade.validation.services.FixerClient;
import com.creditsuisse.trade.validation.validators.ErrorFactory;
import com.creditsuisse.trade.validation.validators.SpotValidator;
import com.creditsuisse.trade.validation.validators.VanillaOptionValidator;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VanillaOptionValidatorTest extends BaseValidatorTest {
	@InjectMocks
	private VanillaOptionValidator validator;

	@Test
	public void shouldRejectInvalidCurrency() {
		// given
		final VanillaOption option = new VanillaOption(
				UUID.randomUUID().toString(), "PLUTO2", "ABCXYZ", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich",
				"Johny Bravo", "EUROPEAN", Strategy.CALL, constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR",
				1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), null
		);

		// when
		validator.check( option, errors );

		// then
		Assert.assertEquals( Arrays.asList( ErrorFactory.invalidCurrency(), ErrorFactory.invalidCurrency() ), errors );
	}

	@Test
	public void shouldRejectInvalidCustomer() {
		// given
		final VanillaOption option = new VanillaOption(
				UUID.randomUUID().toString(), "INVALID", "USDEUR", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich",
				"Johny Bravo", "EUROPEAN", Strategy.CALL, constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR",
				1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), null
		);
		// when
		validator.check( option, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.unsupportedCustomer() ), errors );
	}

	@Test
	public void shouldRejectUnknownOptionType() {
		// given
		final VanillaOption option = new VanillaOption(
				UUID.randomUUID().toString(), "PLUTO2", "USDEUR", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich",
				"Johny Bravo", "INVALID", Strategy.CALL, constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR",
				1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), null
		);

		// when
		validator.check( option, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.invalidOptionStyle() ), errors );
	}

	@Test
	public void shouldRejectExerciseStartNotBetweenTradeAndExpiry() {
		// given
		final VanillaOption option = new VanillaOption(
				UUID.randomUUID().toString(), "PLUTO2", "EURUSD", constructDate( 2016, 8, 12 ), 20.12, 30.22, 40.42,
				"CS Zurich", "Johny Bravo", "AMERICAN", Strategy.CALL, constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ),
				"EUR", 1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 14 ), constructDate( 2016, 8, 17 )
		);

		// when
		validator.check( option, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.exerciseStartDateNotBetweenTradeAndExpiry() ), errors );
	}

	@Test
	public void shouldRejectExpiryOrPremiumNotBeforeDelivery() {
		// given
		final VanillaOption option = new VanillaOption(
				UUID.randomUUID().toString(), "PLUTO2", "USDEUR", constructDate( 2016, 8, 12 ), 20.1, 30.2, 40.12, "CS Zurich",
				"Johny Bravo", "EUROPEAN", Strategy.CALL, constructDate( 2016, 8, 15 ), constructDate( 2016, 8, 14 ), "EUR",
				1.0, "USD", PremiumType.PERCENT_USD, constructDate( 2016, 8, 16 ), null
		);

		// when
		validator.check( option, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.expiryOrPremiumDateNotBeforeDelivery() ), errors );
	}
}
