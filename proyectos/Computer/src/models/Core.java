/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author chris
 */
public class Core {
  
  private Register IRRegister;
  private Program program;

  public Core() {
  }

  public Register getIRRegister() {
    return IRRegister;
  }

  public void setIRRegister(Register IRRegister) {
    this.IRRegister = IRRegister;
  }

  public Program getProgram() {
    return program;
  }

  public void setProgram(Program program) {
    this.program = program;
  }
  
}
