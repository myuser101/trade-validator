package com.creditsuisse.trade.validation.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.creditsuisse.trade.validation.model.domain.Trade;
import com.creditsuisse.trade.validation.model.domain.ValidationError;

public abstract class TradeValidator<T extends Trade> implements Validator<T> {
	// Implementation note: Currency codes and supported customers could be persisted in database and loaded
	// during Spring context initialization. All services could listen on JMS or Kafka topic for requests
	// to refresh list of currencies or supported customers.
	private static final Set<String> ccyIso4217Codes = new HashSet<String>() { {
		add( "AED" ); add( "AFN" ); add( "ALL" ); add( "AMD" ); add( "ANG" ); add( "AOA" ); add( "ARS" ); add( "AUD" ); add( "AWG" );
		add( "AZN" ); add( "BAM" ); add( "BBD" ); add( "BDT" ); add( "BGN" ); add( "BHD" ); add( "BIF" ); add( "BMD" ); add( "BND" );
		add( "BOB" ); add( "BRL" ); add( "BSD" ); add( "BTN" ); add( "BWP" ); add( "BYN" ); add( "BZD" ); add( "CAD" ); add( "CDF" );
		add( "CHF" ); add( "CLP" ); add( "CNY" ); add( "COP" ); add( "CRC" ); add( "CUC" ); add( "CUP" ); add( "CVE" ); add( "CZK" );
		add( "DJF" ); add( "DKK" ); add( "DOP" ); add( "DZD" ); add( "EGP" ); add( "ERN" ); add( "ETB" ); add( "EUR" ); add( "FJD" );
		add( "FKP" ); add( "GBP" ); add( "GEL" ); add( "GGP" ); add( "GHS" ); add( "GIP" ); add( "GMD" ); add( "GNF" ); add( "GTQ" );
		add( "GYD" ); add( "HKD" ); add( "HNL" ); add( "HRK" ); add( "HTG" ); add( "HUF" ); add( "IDR" ); add( "ILS" ); add( "IMP" );
		add( "INR" ); add( "IQD" ); add( "IRR" ); add( "ISK" ); add( "JEP" ); add( "JMD" ); add( "JOD" ); add( "JPY" ); add( "KES" );
		add( "KGS" ); add( "KHR" ); add( "KMF" ); add( "KPW" ); add( "KRW" ); add( "KWD" ); add( "KYD" ); add( "KZT" ); add( "LAK" );
		add( "LBP" ); add( "LKR" ); add( "LRD" ); add( "LSL" ); add( "LYD" ); add( "MAD" ); add( "MDL" ); add( "MGA" ); add( "MKD" );
		add( "MMK" ); add( "MNT" ); add( "MOP" ); add( "MRO" ); add( "MUR" ); add( "MVR" ); add( "MWK" ); add( "MXN" ); add( "MYR" );
		add( "MZN" ); add( "NAD" ); add( "NGN" ); add( "NIO" ); add( "NOK" ); add( "NPR" ); add( "NZD" ); add( "OMR" ); add( "PAB" );
		add( "PEN" ); add( "PGK" ); add( "PHP" ); add( "PKR" ); add( "PLN" ); add( "PYG" ); add( "QAR" ); add( "RON" ); add( "RSD" );
		add( "RUB" ); add( "RWF" ); add( "SAR" ); add( "SBD" ); add( "SCR" ); add( "SDG" ); add( "SEK" ); add( "SGD" ); add( "SHP" );
		add( "SLL" ); add( "SOS" ); add( "SPL" ); add( "SRD" ); add( "STD" ); add( "SVC" ); add( "SYP" ); add( "SZL" ); add( "THB" );
		add( "TJS" ); add( "TMT" ); add( "TND" ); add( "TOP" ); add( "TRY" ); add( "TTD" ); add( "TVD" ); add( "TWD" ); add( "TZS" );
		add( "UAH" ); add( "UGX" ); add( "USD" ); add( "UYU" ); add( "UZS" ); add( "VEF" ); add( "VND" ); add( "VUV" ); add( "WST" );
		add( "XAF" ); add( "XCD" ); add( "XDR" ); add( "XOF" ); add( "XPF" ); add( "YER" ); add( "ZAR" ); add( "ZMW" ); add( "ZWD" );
	} };

	private static final Set<String> customers = new HashSet<String>() { {
		add( "PLUTO1" ); add( "PLUTO2" );
	} };

	@Override
	public void check(T trade, List<ValidationError> errors) {
		if ( ! isValidCurrency( trade.getFirstCurrency() ) ) {
			errors.add( ErrorFactory.invalidCurrency() );
		}
		if ( ! isValidCurrency( trade.getSecondCurrency() ) ) {
			errors.add( ErrorFactory.invalidCurrency() );
		}
		if ( ! customers.contains( trade.getCustomer() ) ) {
			errors.add( ErrorFactory.unsupportedCustomer() );
		}
	}

	protected boolean isValidCurrency(String code) {
		return ccyIso4217Codes.contains( code );
	}
}
