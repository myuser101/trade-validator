package com.creditsuisse.trade.validation.model.domain.types;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ValidationStatus", description = "Validation status.")
public enum ValidationStatus {
	OK, ERROR
}
