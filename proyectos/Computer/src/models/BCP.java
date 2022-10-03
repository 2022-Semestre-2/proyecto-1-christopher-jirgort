/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Stack;

/**
 *
 * @author chris
 */
public class BCP {
  
  private final int ID;
  private String state;
  private int IRRVal;
  private int PCRVal;
  private int AXRVal;
  private int BXRVal;
  private int CXRVal;
  private int DXRVal;
  Stack<Integer> stackVals = new Stack<>();

  public BCP(int ID) {
    this.ID = ID;
  }
  
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getIRRVal() {
    return IRRVal;
  }

  public void setIRRVal(int IRRVal) {
    this.IRRVal = IRRVal;
  }

  public int getPCRVal() {
    return PCRVal;
  }

  public void setPCRVal(int PCRVal) {
    this.PCRVal = PCRVal;
  }

  public int getAXRVal() {
    return AXRVal;
  }

  public void setAXRVal(int AXRVal) {
    this.AXRVal = AXRVal;
  }

  public int getBXRVal() {
    return BXRVal;
  }

  public void setBXRVal(int BXRVal) {
    this.BXRVal = BXRVal;
  }

  public int getCXRVal() {
    return CXRVal;
  }

  public void setCXRVal(int CXRVal) {
    this.CXRVal = CXRVal;
  }

  public int getDXRVal() {
    return DXRVal;
  }

  public void setDXRVal(int DXRVal) {
    this.DXRVal = DXRVal;
  }

  public Stack<Integer> getStackVals() {
    return stackVals;
  }

  public void setStackVals(Stack<Integer> stackVals) {
    this.stackVals = stackVals;
  }

  
  
  
}
