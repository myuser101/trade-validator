package com.creditsuisse.trade.validation.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "ValidationError", description = "Reason of invalid trade request.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class ValidationError {
	@ApiModelProperty(notes = "Error code.", required = true)
	private String code;

	@ApiModelProperty(notes = "Human readable description send back to the caller.", required = false)
	private String message;
}