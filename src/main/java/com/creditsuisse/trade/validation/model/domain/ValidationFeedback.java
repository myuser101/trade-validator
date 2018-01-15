package com.creditsuisse.trade.validation.model.domain;

import java.io.Serializable;

import com.creditsuisse.trade.validation.model.domain.types.ValidationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "ValidationFeedback", description = "Feedback encapsulating information about validity of trade request.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ValidationFeedback implements Serializable {
	@ApiModelProperty(notes = "Trade request identifier.", required = true)
	@JsonProperty(required = true)
	private String id;

	@ApiModelProperty(notes = "Validation status.", required = true)
	@JsonProperty(required = true)
	private ValidationStatus status;

	@ApiModelProperty(notes = "Rejection validation reason.", required = false)
	@JsonProperty(required = false)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private ValidationError[] reasons;
}
