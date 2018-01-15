package com.creditsuisse.trade.validation.model.domain;

import java.util.Date;

import com.creditsuisse.trade.validation.model.domain.types.PremiumType;
import com.creditsuisse.trade.validation.model.domain.types.Strategy;
import com.creditsuisse.trade.validation.model.domain.types.TradeType;
import com.creditsuisse.trade.validation.validators.Validator;
import com.creditsuisse.trade.validation.validators.VanillaOptionValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "VanillaOption", description = "Vanilla option trade.")
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true, callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VanillaOption extends Trade {
	@ApiModelProperty(notes = "Trade style.", required = true)
	@JsonProperty(required = true)
	private String style;

	@ApiModelProperty(notes = "Strategy.", required = true)
	@JsonProperty(required = true)
	private Strategy strategy;

	@ApiModelProperty(notes = "Delivery date.", required = true)
	@JsonProperty(required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date deliveryDate;

	@ApiModelProperty(notes = "Expiry date.", required = true)
	@JsonProperty(required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date expiryDate;

	@ApiModelProperty(notes = "Payment currency.", required = true)
	@JsonProperty(required = true)
	private String payCcy;

	@ApiModelProperty(notes = "Premium percentage.", required = true)
	@JsonProperty(required = true)
	private Double premium;

	@ApiModelProperty(notes = "Premium currency.", required = true)
	@JsonProperty(required = true)
	private String premiumCcy;

	@ApiModelProperty(notes = "Premium type.", required = true)
	@JsonProperty(required = true)
	private PremiumType premiumType;

	@ApiModelProperty(notes = "Premium date.", required = true)
	@JsonProperty(required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date premiumDate;

	// Implementation note, unfortunately Java JSON libraries do not support multiple levels of inheritance.
	@ApiModelProperty(notes = "Exercise start date. Valid only for american-style options.", required = true)
	@JsonProperty(required = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date exerciseStartDate;

	public VanillaOption(
			String id, String customer, String ccyPair, Date tradeDate, Double amount1, Double amount2, Double rate,
			String legalEntity, String trader, String style, Strategy strategy, Date deliveryDate, Date expiryDate,
			String payCcy, Double premium, String premiumCcy, PremiumType premiumType, Date premiumDate, Date exerciseStartDate) {
		super( id, customer, ccyPair, TradeType.VANILLA_OPTION, tradeDate, amount1, amount2, rate, legalEntity, trader );
		this.style = style;
		this.strategy = strategy;
		this.deliveryDate = deliveryDate;
		this.expiryDate = expiryDate;
		this.payCcy = payCcy;
		this.premium = premium;
		this.premiumCcy = premiumCcy;
		this.premiumType = premiumType;
		this.premiumDate = premiumDate;
		this.exerciseStartDate = exerciseStartDate;
	}

	@Override
	public Class<? extends Validator> getValidator() {
		return VanillaOptionValidator.class;
	}
}
