package com.creditsuisse.trade.validation.test.unit;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

import com.creditsuisse.trade.validation.model.domain.ValidationError;

public abstract class BaseValidatorTest extends BaseUnitTest {
	protected final List<ValidationError> errors = new LinkedList<ValidationError>();

	@Before
	public void setUp() {
		errors.clear();
	}
}
