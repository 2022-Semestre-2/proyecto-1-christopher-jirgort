/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Andrey McCarty
 */
public class SecondaryMemory {

  private final int size = 512;
  private int unallocatedMemory;
  private ArrayList<Program> programs;
  private int programIndex;
  private String[] memory;
  private String[] virtualMemory;
  private int vitualMemory;

  private final String LOAD = "LOAD";
  private final String STORE = "STORE";
  private final String MOV = "MOV";
  private final String SUB = "SUB";
  private final String ADD = "ADD";

  public SecondaryMemory() {
    this.programs = new ArrayList<>();
    this.memory = new String[this.size];
    this.unallocatedMemory = this.size;

  }

  public ArrayList<Program> getPrograms() {
    return programs;
  }

  public int getProgramIndex() {
    return programIndex;
  }

  public boolean mountProgram(Program program) {

    this.programs.add(program);
    if (this.unallocatedMemory > program.getSize()) {
      int instrIndex = 0;
      int j = this.programIndex;
      for (int i = 0; i < program.getSize(); i++) {
        String[] instruction = program.getInstructions()[instrIndex].getInst();
        String operation = instruction[0];
        String register = instruction[1];
        String value = instruction[2];
        String instructions = operation + register + value;
        memory[j] = instructions;
        j++;
        instrIndex++;
        unallocatedMemory -= 1;
      }
      this.unallocatedMemory = unallocatedMemory - program.getSize();
      return true;
    }
    return false;
  }
}
