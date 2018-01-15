package com.creditsuisse.trade.validation.test.unit;

import java.util.Date;
import java.util.GregorianCalendar;

public abstract class BaseUnitTest {
	protected Date constructDate(int year, int month, int day) {
		return new GregorianCalendar( year, month, day ).getTime();
	}
}
