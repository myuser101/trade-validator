package com.creditsuisse.trade.validation.model.messages;

import java.time.ZonedDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "ErrorMessage", description = "Generic error message indicating server-side error.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class ErrorMessage {
	@ApiModelProperty(notes = "Unique error identifier (stack trace visible in central logging).", required = true)
	private String id;

	@ApiModelProperty(notes = "Error timestamp.", required = true)
	private ZonedDateTime timestamp;

	@ApiModelProperty(notes = "Error code.", required = true)
	private String code;

	@ApiModelProperty(notes = "Human readable description send back to the caller.", required = false)
	private String message;
}