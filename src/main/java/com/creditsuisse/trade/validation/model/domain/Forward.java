package com.creditsuisse.trade.validation.model.domain;

import java.util.Date;

import com.creditsuisse.trade.validation.model.domain.types.TradeType;
import com.creditsuisse.trade.validation.validators.ForwardValidator;
import com.creditsuisse.trade.validation.validators.Validator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "VanillaOption", description = "Forward trade.")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true, callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Forward extends Trade {
	@ApiModelProperty(notes = "Value date.", required = true)
	@JsonProperty(required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date valueDate;

	public Forward(
			String id, String customer, String ccyPair, Date tradeDate, Double amount1,
			Double amount2, Double rate, String legalEntity, String trader, Date valueDate) {
		super( id, customer, ccyPair, TradeType.FORWARD, tradeDate, amount1, amount2, rate, legalEntity, trader );
		this.valueDate = valueDate;
	}

	@Override
	public Class<? extends Validator> getValidator() {
		return ForwardValidator.class;
	}
}
