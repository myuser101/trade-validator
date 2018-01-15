package com.creditsuisse.trade.validation.model.domain.types;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "TradeType", description = "Trade type.")
public enum TradeType implements Serializable {
	@JsonProperty("Spot")
	SPOT,
	@JsonProperty("Forward")
	FORWARD,
	@JsonProperty("VanillaOption")
	VANILLA_OPTION;
}
