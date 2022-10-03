/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Chris & Jirgort
 */
public class Instruction {
  
  private String inst[];
  private int weight;

  public Instruction(String[] inst, int weight) {
    this.inst = inst;
    this.weight = weight;
  }

  public String[] getInst() {
    return inst;
  }

  public int getWeight() {
    return weight;
  }
  
  public void reduceWeight() {
    this.weight -= 1;
  }
}
