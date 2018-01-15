package com.creditsuisse.trade.validation.model.domain.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "PremiumType", description = "Premium type.")
public enum PremiumType {
	@JsonProperty("%USD")
	PERCENT_USD,
}
