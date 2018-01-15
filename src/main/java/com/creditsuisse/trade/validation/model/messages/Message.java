package com.creditsuisse.trade.validation.model.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel(value = "Message", description = "Generic reply to the caller.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Message {
	@ApiModelProperty(notes = "Human readable description send back to the caller.")
	private String message;
}
