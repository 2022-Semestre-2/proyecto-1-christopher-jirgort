/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Chris & Jirgort
 */
public class MainMemory {

  private int size=128;
  private String[] memory;
  private int unallocatedMemory;
  private ArrayList <Program> programs;
  private int programIndex=0;
  
  private final String LOAD = "LOAD";
  private final String STORE = "STORE";
  private final String MOV = "MOV";
  private final String SUB = "SUB";
  private final String ADD = "ADD";

  public MainMemory() {
    this.size = size;
    this.memory = new String[size];
    this.unallocatedMemory = this.size;
    this.programs=new ArrayList<>();
    this.programIndex = getRandomMemorySpace(10, unallocatedMemory);
    fillInstructions();
    
  }

  public int getSize() {
    return size;
  }

  public String[] getMemory() {
    return memory;
  }

  public int getUnallocatedMemory() {
    return unallocatedMemory;
  }

  public ArrayList<Program> getProgram() {
    return programs;
  }
 
  public void addProgram() {
    
  }
  
  public int getProgramIndex() {
    return programIndex;
  }
  
  public boolean mountProgram(Program program) {

    this.programs.add(program);
    System.out.println("UNALLOCATED MEMORY:" + this.unallocatedMemory + " - PROGRAM SIZE: " + program.getSize());
    if (this.unallocatedMemory > program.getSize()) {
      int instrIndex = 0;
      int j = this.programIndex;
      int limit = program.getSize() - this.programIndex;
      for (int i = 0; i < limit; i++) {
        String[] instruction = program.getInstructions()[instrIndex].getInst();
        String operation = instruction[0];
        String register = instruction[1];
        String value = instruction[2];
        String instructions = operation + register + value;
        System.out.println("j is: " + j);
        memory[j] =instructions;
        j++;
        instrIndex++;
      }
      this.unallocatedMemory = unallocatedMemory - program.getSize();
      return true;
    }
    return false;
  }

  public int getRandomMemorySpace(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
  
  private String getRegisterID(String register) {
  
    String res;
    switch(register) {
      case "AX":
        res = "0001";
        break;
      case "BX":
        res = "0010";
        break;
      case "CX":
        res = "0011";
        break;
      case "DX":
        res = "0100";
        break;
      default:
        res = "";
    }
    return res;
  }

  private String fillBits(String binaryNum, boolean sign) {

    int zeros = 8 - binaryNum.length();
    int i = 0;
    String newBinaryNum = "";
    while (i < zeros) {
      if (sign) {
        newBinaryNum += "1";
        sign = false;
      } else {
        newBinaryNum += "0";
      }
      i++;
    }
    return newBinaryNum.concat(binaryNum);
  }

  public void fillInstructions() {

    for (int i = 0; i < this.memory.length; i++) {
      this.memory[i] = "0";
    }
  }

  public CharSequence[] getInstructionToExecute(int i) {

    int limit = this.getProgramIndex() + this.programs.size();
    if (i >= limit) {
      CharSequence[] inst = {};
      return inst;
    } else {
      String instruction = this.memory[i];
      //String[] inst = instruction.split("  ");
      CharSequence operator = instruction.subSequence(0, 4);
      CharSequence register = instruction.subSequence(4, 8);
      CharSequence value = instruction.subSequence(8, 16);
      CharSequence[] inst = {operator, register, value};
      return inst;
    }
  } 
}
