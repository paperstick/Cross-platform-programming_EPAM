package com.bsuir.lab_1;

public class CustomNumber {
	private final Double number;

	public CustomNumber(Double number) {
		this.number = number;
	}

	public Double getNumber() {
		return this.number;
	}

	public Boolean isEven() {
		return number % 2 == 0;
	}

	public Boolean isPrime() {
		if (number <= 1) {
			return false;
		}
    
    for (int index = 2; index < number; index++) {
    	if (this.number % index == 0) {
      	return false;
			}
		}
  
    return true;
	}
}
