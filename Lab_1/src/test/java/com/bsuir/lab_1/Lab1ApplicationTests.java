package com.bsuir.lab_1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.bsuir.lab_1.services.NumberService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NumberServiceTests {

	@Autowired
	private NumberService numberService;

	@Test
	void testGetMinimumNumber() throws IllegalArgumentException {
		CustomNumber firstNumber = new CustomNumber(1.0);
		CustomNumber secondNumber = new CustomNumber(2.0);

		List<CustomNumber> numberList = new ArrayList<CustomNumber>();
		numberList.add(firstNumber);
		numberList.add(secondNumber);

		Stream<Double> numberStream = numberList.stream().map(element -> element.getNumber());

		Double expectedNumber = 1.0;

		assertEquals(expectedNumber, numberService.getMinimumNumber(numberStream));
	}

}
