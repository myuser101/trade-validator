package com.creditsuisse.trade.validation.model.domain;

import java.io.Serializable;
import java.util.Date;

import com.creditsuisse.trade.validation.model.domain.types.TradeType;
import com.creditsuisse.trade.validation.validators.Validator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "Trade", description = "Common trade attributes.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
		property = "type", visible = true
)
@JsonSubTypes(
		{
				@JsonSubTypes.Type( value = Spot.class, name = "Spot" ),
				@JsonSubTypes.Type( value = Forward.class, name = "Forward" ),
				@JsonSubTypes.Type( value = VanillaOption.class, name = "VanillaOption" )
		}
)
public abstract class Trade implements Serializable {
	@ApiModelProperty(notes = "Unique trade identifier.", required = true)
	@JsonProperty(required = true)
	private String id;

	@ApiModelProperty(notes = "Customer.", required = true)
	@JsonProperty(required = true)
	private String customer;

	@ApiModelProperty(notes = "Currency pair in format of 3-letter ISO code.", required = true)
	@JsonProperty(required = true)
	private String ccyPair;

	@ApiModelProperty(notes = "Trade type.", required = true)
	@JsonProperty(required = true)
	private TradeType type;

	@ApiModelProperty(notes = "Trade date.", required = true)
	@JsonProperty(required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date tradeDate;

	@ApiModelProperty(notes = "First amount.", required = true)
	@JsonProperty(required = true)
	private Double amount1;

	@ApiModelProperty(notes = "Second amount.", required = true)
	@JsonProperty(required = true)
	private Double amount2;

	@ApiModelProperty(notes = "Exchange rate.", required = true)
	@JsonProperty(required = true)
	private Double rate;

	@ApiModelProperty(notes = "Legal entity.", required = true)
	@JsonProperty(required = true)
	private String legalEntity;

	@ApiModelProperty(notes = "Trader.", required = true)
	@JsonProperty(required = true)
	private String trader;

	public String getFirstCurrency() {
		return ccyPair != null ? ccyPair.substring( 0, 3 ) : null;
	}

	public String getSecondCurrency() {
		return ccyPair != null ? ccyPair.substring( 3, 6 ) : null;
	}

	public abstract Class<? extends Validator> getValidator();
}
