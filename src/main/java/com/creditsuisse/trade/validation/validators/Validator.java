package com.creditsuisse.trade.validation.validators;

import java.util.List;

import com.creditsuisse.trade.validation.model.domain.ValidationError;

public interface Validator<T> {
	public void check(T object, List<ValidationError> errors);
}
