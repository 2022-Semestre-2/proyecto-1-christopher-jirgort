/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris & Jirgort
 */
public class Program {

  private int size;
  private Instruction[] instructions;
  private int currentLine;
  private final int ID;
  private static int programCount=0;
  private BCP programBCP;

  public Program(int size, Instruction[] instructions) {
    this.size = size;
    this.ID = this.programCount;
    this.programCount+=1;
    this.instructions = instructions;
    this.programBCP = new BCP(this.ID);
  }

  public BCP getProgramBCP() {
    return programBCP;
  }

  public Program() {
      this.ID = this.programCount;
      this.programCount+=1;
  }

  public int getSize() {
    return size;
  }

    public int getID() {
        return ID;
    }

  public String[] getNextInstruction() {
    //for(String val : instructions) {
    //  System.out.println("getNextInstruction: " + val);
    //}
    String[] instruction = this.instructions[currentLine].getInst();
    String[] instrRes = {"", "", ""};
    instrRes[0] = instruction[0];
    currentLine += 1;
    instrRes[1] = instruction[1];
    currentLine += 1;
    instrRes[2] = instruction[2];
    currentLine += 1;
    return instrRes;
  }

  public void setProgramBCP(BCP programBCP) {
    this.programBCP = programBCP;
  }
  
  public int getCurrentLine() {
    return currentLine;
  }

  public void parseInstruction(File file) {
    // TODO add your handling code here:
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String text = "";
      String line = "";
      ArrayList<String[]> instructionsParser = new ArrayList<>();
      int instructionWeight = 0;
      int lineNumber = 0;
      int errCount = 0;
      while (((line = br.readLine()) != null)) {
        String lineFormat = line;
        String newLine = "";
        //System.out.println("Line is: " + line);
        if(line.contains("JMP") || line.contains("JE") || line.contains("JNE")) {
          newLine = line + ", x";
          instructionsParser.add(newLine.replace(",", "").split(" "));
        }
        // Parses interruption.
        else if (line.contains("INT")) {
          newLine = line + ", x";
          instructionsParser.add(newLine.split(" "));
        } // Parses INC and DEC instructions.
        else if (line.equals("INC") || (line.equals("DEC"))) {
          newLine = line + " x, x";
          instructionsParser.add(newLine.replace(",", "").split(" "));
        } // Parses MOV and SWAP instructions.
        else if (line.contains(",")) {
          newLine = line;
          instructionsParser.add(line.replace(",", "").split(" "));
        } else {
          // Parses every other instruction.
          newLine = line + ", x";
          instructionsParser.add(newLine.replace(",", "").split(" "));
        }
        //System.out.println("NewLine: " + newLine);
        //System.out.println("New Formatted line: " + newLine.replace(",", ""));
        if (instructionFormatter(newLine.replace(",", ""))) {
          text += line + "\n";
        } else {
          JOptionPane.showMessageDialog(null, "SYNTAX ERR AT LINE " + lineNumber + ". STOPPING PROCESS.");
          errCount += 1;
        }
        lineNumber++;
      }
      int instructionsQuantity = instructionsParser.size();
      String[] programInstructions = new String[3 * instructionsQuantity];
      Instruction programInstr[] = new Instruction[instructionsQuantity];
      int i = 0;
      int index = 0;
      for (String[] val : instructionsParser) {
        Instruction instr = new Instruction(val, getInstructionWeight(val[0]));
        programInstr[index] = instr;
        index += 1;
        for (String inst : val) {
          //System.out.println("Inst: " + inst);
          programInstructions[i] = inst;
          i += 1;
        }
      }
      if (errCount > 0) {
        text = "FIX THE ERRORS AND LOAD THE PROGRAM AGAIN.";
      }
      this.instructions = programInstr;
      this.size = programInstr.length;
    } catch (FileNotFoundException ex) {
      //Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      //Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private boolean instructionFormatter(String instruction) {

    String[] instr;
    if (instruction.contains(",")) {
      instr = instruction.replace(",", "").split(" ");
    } else {
      String newLine = instruction + ", x";
      instr = newLine.replace(",", "").split(" ");
    }

    String[] jumps = {"JMP", "JE", "JNE"};
    String INT = "INT";
    String[] interruptions = {"20H", "09H", "10H"};
    String[] operations = {"MOV", "LOAD", "STORE", "ADD", "SUB", "INC", "DEC", "PUSH", "POP", "SWAP", "CMP"};
    String[] operators = {"AX", "BX", "CX", "DX", "x"};

    if(isPresent(jumps, instr[0])) {
      //System.out.println("JUMP: " + instr[0] + " " + instr[1]);
      if(instr[1].matches("-?\\d+(\\.\\d+)?")) {
        return true;
      } return false;
    } else if (instruction.contains(INT)) {
      //System.out.println("INTERRUPTION: " + instr[1]);
      if (isPresent(interruptions, instr[1])) {
        return true;
      } return false;
    } else {
      if (isPresent(operations, instr[0])) {
        if (isPresent(operators, instr[1])) {
          if (isPresent(operators, instr[2]) || (instr[2].matches("-?\\d+(\\.\\d+)?"))) {
            return true;
          } else {
            System.out.println("FAILS AT INDEX 3");
            return false;
          }
        } else {
          System.out.println("FAILS AT INDEX 2");
          return false;
        }
      } else {
        System.out.println("FAILS AT INDEX 1");
        return false;
      }
    }
  }

  public Instruction[] getInstructions() {
    return this.instructions;
  }
  private boolean isPresent(String[] values, String val) {
    for (String element : values) {
      if (val.equals(element)) {
        return true;
      }
    }
    return false;
  }
  
  public int getInstructionWeight(String instruction) {
    
    String[] weightOneOperators = {"MOV", "INC", "DEC", "SWAP", "PUSH", "POP"};
    String[] weightTwoOperators = {"LOAD", "STORE", "JMP", "JE", "JNE"};
    String[] weightThreeOperators = {"ADD", "SUB"};
    if(isPresent(weightOneOperators, instruction)) {
      return 1;
    } else if(isPresent(weightTwoOperators, instruction)) {
      return 2;
    } return 3;
  }
}
