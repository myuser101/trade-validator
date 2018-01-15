package com.creditsuisse.trade.validation.model.domain.types;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Direction", description = "Trade direction.")
public enum Direction implements Serializable {
	SELL, BUY
}
