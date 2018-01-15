package com.creditsuisse.trade.validation.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.creditsuisse.trade.validation.model.domain.ValidationError;
import com.creditsuisse.trade.validation.model.domain.VanillaOption;
import org.springframework.stereotype.Component;

@Component
public class VanillaOptionValidator extends TradeValidator<VanillaOption> {
	private static final Set<String> styles = new HashSet<String>() { {
		add( "AMERICAN" ); add( "EUROPEAN" );
	} };

	@Override
	public void check(VanillaOption trade, List<ValidationError> errors) {
		super.check( trade, errors );
		if ( ! styles.contains( trade.getStyle() ) ) {
			errors.add( ErrorFactory.invalidOptionStyle() );
		}
		if ( "AMERICAN".equals( trade.getStyle() )
				&& ! ( trade.getExerciseStartDate().after( trade.getTradeDate() ) && trade.getExerciseStartDate().before( trade.getExpiryDate() ) ) ) {
			errors.add( ErrorFactory.exerciseStartDateNotBetweenTradeAndExpiry() );
		}
		if ( ! trade.getExpiryDate().before( trade.getDeliveryDate() ) || ! trade.getPremiumDate().before( trade.getDeliveryDate() ) ) {
			errors.add( ErrorFactory.expiryOrPremiumDateNotBeforeDelivery() );
		}
	}
}
