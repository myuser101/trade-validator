package com.creditsuisse.trade.validation.validators;

import com.creditsuisse.trade.validation.model.domain.ValidationError;

public abstract class ErrorFactory {
	public static ValidationError invalidCurrency() {
		return new ValidationError( "VAL-001", "Invalid currency pair." );
	}

	public static ValidationError valueDateBeforeTradeDate() {
		return new ValidationError( "VAL-002", "Value date cannot be before trade date." );
	}

	public static ValidationError nonWorkingValueDateForCurrency(String currency) {
		return new ValidationError( "VAL-003", String.format( "Non-working value date for currency %s.", currency ) );
	}

	public static ValidationError unsupportedCustomer() {
		return new ValidationError( "VAL-004", "Unsupported customer." );
	}

	public static ValidationError invalidOptionStyle() {
		return new ValidationError( "VAL-005", "Invalid option style." );
	}

	public static ValidationError exerciseStartDateNotBetweenTradeAndExpiry() {
		return new ValidationError( "VAL-006", "Exercise start date not between trade and expiry dates." );
	}

	public static ValidationError expiryOrPremiumDateNotBeforeDelivery() {
		return new ValidationError( "VAL-007", "Expiry or Delivery date not before delivery date." );
	}
}
