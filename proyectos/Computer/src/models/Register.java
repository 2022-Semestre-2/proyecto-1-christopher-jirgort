/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Chris & Jirgort
 */
public class Register {

  private String name;
  private String registerId;
  private int currentValue;
  private int[][] status;
  private int statuxIndex;

  public Register(String name, String registerId, int currentValue) {
    this.name = name;
    this.registerId = registerId;
  }

  public String getName() {
    return name;
  }

  public String getRegisterId() {
    return registerId;
  }

  public int getCurrentValue() {
    return currentValue;
  }

  public int[][] getStatus() {
    return status;
  }

  public void setCurrentValue(int value, boolean accumulative) {
    if(accumulative) {
    this.currentValue += value;
    } else {
      this.currentValue = value;
    }
  }

  @Override
  public String toString() {
    return "- " + name + ": " + currentValue + " -";
  } 
}
