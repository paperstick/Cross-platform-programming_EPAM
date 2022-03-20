package com.bsuir.lab_1.models;

import javax.persistence.*;

@Entity
@Table(name = "number")
public class CustomNumberModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "number")
    private Double number;

    @Column(name = "is_even")
    private Boolean isEven;

    @Column(name = "is_prime")
    private Boolean isPrime;

    @Column(name = "processId")
    private Integer processId;

    public Integer getProcessId() {
        return this.processId;
    }

    public void setProcessId(Integer id) {
        this.processId = id;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Boolean getIsEven() {
        return this.isEven;
    }

    public void setIsEven(Boolean isEven) {
        this.isEven = isEven;
    }

    public Boolean getIsPrime() {
        return this.isPrime;
    }

    public void setIsPrime(Boolean isPrime) {
        this.isPrime = isPrime;
    }

    public void setNumberInfo(Double number, Boolean isEven, Boolean isPrime) {
        this.setNumber(number);
        this.setIsEven(isEven);
        this.setIsPrime(isPrime);
    }
}
