package com.creditsuisse.trade.validation.controllers;

import java.util.LinkedList;
import java.util.List;

import com.creditsuisse.trade.validation.model.domain.Trade;
import com.creditsuisse.trade.validation.model.domain.ValidationError;
import com.creditsuisse.trade.validation.model.domain.ValidationFeedback;
import com.creditsuisse.trade.validation.model.domain.types.ValidationStatus;
import com.creditsuisse.trade.validation.model.messages.ErrorMessage;
import com.creditsuisse.trade.validation.utils.ProviderHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api( value = "trade-validator", description = "Validates correctness of trade requests of various types." )
@RestController
public class TradeValidationController {
	@Autowired
	private ProviderHelper helper;

	@ApiOperation( value = "Bulk validation of spot, forward and vanilla option trades. " +
			"Clients can submit single or multiple requests in one service call. Correlate feedback based on trade ID."
	)
	@ApiResponses(
			value = {
					@ApiResponse(
							code = 200,
							message = "Successful validation of trade requests. Inspect response to know validity of each trade.",
							response = ValidationFeedback[].class
					),
					@ApiResponse( code = 500, message = "Generic exception.", response = ErrorMessage.class )
			}
	)
	@RequestMapping(
			value = "/v1/trade/validate", method = RequestMethod.POST,
			consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE }
	)
	@ResponseBody
	@SuppressWarnings( "unchecked" )
	public ResponseEntity<?> validate(@ApiParam(value = "Array of trade requests.", required = true) @RequestBody Trade[] trades) {
		final List<ValidationFeedback> feedback = new LinkedList<ValidationFeedback>();
		for ( Trade trade : trades ) {
			final List<ValidationError> errors = new LinkedList<ValidationError>();
			helper.getBeanByType( trade.getValidator() ).check( trade, errors );
			feedback.add(
					new ValidationFeedback(
							trade.getId(), errors.isEmpty() ? ValidationStatus.OK : ValidationStatus.ERROR,
							errors.toArray( new ValidationError[ errors.size() ] )
					)
			);
		}
		return new ResponseEntity<ValidationFeedback[]>(
				feedback.toArray( new ValidationFeedback[ feedback.size() ] ),
				HttpStatus.OK
		);
	}
}
