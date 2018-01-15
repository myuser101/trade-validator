package com.creditsuisse.trade.validation.test.unit;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.creditsuisse.trade.validation.model.domain.Spot;
import com.creditsuisse.trade.validation.services.FixerClient;
import com.creditsuisse.trade.validation.validators.ErrorFactory;
import com.creditsuisse.trade.validation.validators.SpotValidator;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpotValidatorTest extends BaseValidatorTest {
	@InjectMocks
	private SpotValidator validator;

	@Mock
	private FixerClient clientMock;

	@Test
	public void shouldRejectInvalidFirstCurrency() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO1", "XYZUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.any( String.class ) )
		).thenReturn( constructDate( 2016, 8, 15 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.invalidCurrency() ), errors );
	}

	@Test
	public void shouldRejectInvalidSecondCurrency() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO1", "USDABC", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.any( String.class ) )
		).thenReturn( constructDate( 2016, 8, 15 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.invalidCurrency() ), errors );
	}

	@Test
	public void shouldRejectInvalidCustomer() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO9", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.any( String.class ) )
		).thenReturn( constructDate( 2016, 8, 15 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.unsupportedCustomer() ), errors );
	}

	@Test
	public void shouldRejectValueDateBeforeTradeDate() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 10 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.any( String.class ) )
		).thenReturn( constructDate( 2016, 8, 10 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.valueDateBeforeTradeDate() ), errors );
	}

	@Test
	public void shouldRejectNonWorkingFirstCurrency() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.eq( "EUR" ) )
		).thenReturn( constructDate( 2016, 8, 10 ) );
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.eq( "USD" ) )
		).thenReturn( constructDate( 2016, 8, 15 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.nonWorkingValueDateForCurrency( "EUR" ) ), errors );
	}

	@Test
	public void shouldRejectNonWorkingSecondCurrency() {
		// given
		final Spot spot = new Spot(
				UUID.randomUUID().toString(), "PLUTO1", "EURUSD", constructDate( 2016, 8, 11 ), 1.1, 2.2, 1.12,
				"CS Zurich", "Johny Bravo", constructDate( 2016, 8, 15 )
		);
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.eq( "EUR" ) )
		).thenReturn( constructDate( 2016, 8, 15 ) );
		Mockito.when(
				clientMock.getCurrencyDate( Matchers.any( Date.class ), Matchers.eq( "USD" ) )
		).thenReturn( constructDate( 2016, 8, 10 ) );

		// when
		validator.check( spot, errors );

		// then
		Assert.assertEquals( Collections.singletonList( ErrorFactory.nonWorkingValueDateForCurrency( "USD" ) ), errors );
	}
}
