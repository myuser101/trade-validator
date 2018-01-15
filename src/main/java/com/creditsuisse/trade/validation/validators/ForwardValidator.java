package com.creditsuisse.trade.validation.validators;

import java.util.List;

import com.creditsuisse.trade.validation.model.domain.Forward;
import com.creditsuisse.trade.validation.model.domain.ValidationError;
import com.creditsuisse.trade.validation.services.FixerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForwardValidator extends TradeValidator<Forward> {
	@Autowired
	private FixerClient fixer;

	@Override
	public void check(Forward trade, List<ValidationError> errors) {
		super.check( trade, errors );
		if ( trade.getValueDate().before( trade.getTradeDate() ) ) {
			errors.add( ErrorFactory.valueDateBeforeTradeDate() );
		}
		if ( isValidCurrency( trade.getFirstCurrency() )
				&& ! trade.getValueDate().equals( fixer.getCurrencyDate( trade.getValueDate(), trade.getFirstCurrency() ) ) ) {
			errors.add( ErrorFactory.nonWorkingValueDateForCurrency( trade.getFirstCurrency() ) );
		}
		if ( isValidCurrency( trade.getSecondCurrency() )
				&& ! trade.getValueDate().equals( fixer.getCurrencyDate( trade.getValueDate(), trade.getSecondCurrency() ) ) ) {
			errors.add( ErrorFactory.nonWorkingValueDateForCurrency( trade.getSecondCurrency() ) );
		}
	}
}
