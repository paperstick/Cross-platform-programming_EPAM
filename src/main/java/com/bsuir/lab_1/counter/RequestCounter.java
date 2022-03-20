package com.bsuir.lab_1.counter;

public class RequestCounter {
  private int number = 0;

  public RequestCounter(int number) {
      this.number = number;
  }

  public synchronized void increment() {
      this.number++;
  }

  public synchronized void decrement() {
      this.number--;
  }

  public int getNumber() {
      return number;
  }

  public void setNumber(int number) {
      this.number = number;
  }
}