/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Chris & Jirgort
 */
public class CPU {

  public MainMemory memory;
  private SecondaryMemory secondaryMemory;
  private Register PCRegister;
  private Register IRRegister;
  private Register ACRegister;
  private Register AXRegister;
  private Register BXRegister;
  private Register CXRegister;
  private Register DXRegister;

  private final String LOAD = "LOAD";
  private final String STORE = "STORE";
  private final String MOV = "MOV";
  private final String SUB = "SUB";
  private final String ADD = "ADD";
  private final String INC = "INC";
  private final String DEC = "DEC";
  private final String SWAP = "SWAP";
  private final String INT = "INT";
  private final String JMP = "JMP";
  private final String CMP = "CMP";
  private final String JE = "JE";
  private final String JNE = "JNE";
  private final String PUSH = "PUSH";
  private final String POP = "POP";
  Stack<Integer> cpuStack = new Stack<>();
  private int currentProgramRunning = 0;

  public CPU(MainMemory memory , SecondaryMemory newDisk) {
    this.memory = memory;
    this.secondaryMemory = newDisk;
    this.PCRegister = new Register("PC", "PC", 0);
    this.IRRegister = new Register("IR", "IR", 0);
    this.ACRegister = new Register("AC", "AC", 0);
    this.AXRegister = new Register("AX", "0001", 0);
    this.BXRegister = new Register("BX", "0010", 0);
    this.CXRegister = new Register("CX", "0011", 0);
    this.DXRegister = new Register("DX", "0100", 0);
  }

  public MainMemory getMemory() {
    return memory;
  }

  public SecondaryMemory getSecondaryMemory() {
        return secondaryMemory;
    }

  private Register getPCRegister() {
    return PCRegister;
  }

  private Register getIRRegister() {
    return IRRegister;
  }

  private Register getACRegister() {
    return ACRegister;
  }

  private Register getAXRegister() {
    return AXRegister;
  }

  private Register getBXRegister() {
    return BXRegister;
  }

  private Register getCXRegister() {
    return CXRegister;
  }

  private Register getDXRegister() {
    return DXRegister;
  }

  public void setPCRegisterVal(int val, boolean accumulative) {
    this.PCRegister.setCurrentValue(val, accumulative);
  }

  public void setIRRegisterVal(int val, boolean accumulative) {
    this.IRRegister.setCurrentValue(val, accumulative);
  }

  public void setACRegisterVal(int val, boolean accumulative) {
    this.ACRegister.setCurrentValue(val, accumulative);
  }

  public void setAXRegisterVal(int val, boolean accumulative) {
    this.AXRegister.setCurrentValue(val, accumulative);
  }

  public void setBXRegisterVal(int val, boolean accumulative) {
    this.BXRegister.setCurrentValue(val, accumulative);
  }

  public void setCXRegisterVal(int val, boolean accumulative) {
    this.CXRegister.setCurrentValue(val, accumulative);
  }

  public void setDXRegisterVal(int val, boolean accumulative) {
    this.DXRegister.setCurrentValue(val, accumulative);
  }

  public Register getRegister(String id) {

    Register register = null;
    switch (id) {
      case "PC":
        register = getPCRegister();
        break;
      case "IR":
        register = getIRRegister();
        break;
      case "AC":
        register = getACRegister();
        break;
      case "AX": // 0001
        register = getAXRegister();
        break;
      case "BX": // 0010
        register = getBXRegister();
        break;
      case "CX": // 0011
        register = getCXRegister();
        break;
      case "DX": // 0100
        register = getDXRegister();
        break;
    }
    return register;
  }

  public boolean loadProgram(Program program) {

    if (this.memory.mountProgram(program)) {

      return true;
    }
    return false;
  }

  // Returns false if instruction is finished executing (based on its weight) else returns true.
  public int executeProgram(int instrIndex) {
    //CharSequence[] instruction = this.memory.getInstructionToExecute(i);
    Instruction[] programInstruction = memory.getProgram().get(this.currentProgramRunning).getInstructions();
    int allInstructions = programInstruction.length;
    int i = 0;
    //int instrIndex = 0;
    Instruction instruction = null;
    int instrJump;
    int firstRegisterVal;
    int secondRegisterVal;
    if (instrIndex < allInstructions) {
      //System.out.println("instrIndex: " + instrIndex);
      //for (Instruction instruction : programInstruction) {
      instrJump = 1;
      instruction = programInstruction[instrIndex];
      if (instruction.getWeight() == 1) {
        //System.out.println("********** INSTRUCTION TO EXECUTE: " + instruction.getInst()[0] + " " + instruction.getInst()[1] + " " + instruction.getInst()[2] + "**********");
        CharSequence operation = instruction.getInst()[0];
        CharSequence register = instruction.getInst()[1];
        CharSequence value = instruction.getInst()[2];
        Register operatedRegister = null;
        Register secondOperatedRegister = null;
        int registerVal = 0;
        switch (operation.toString()) {
          case LOAD:
            operatedRegister = getRegister(register.toString());
            registerVal = this.ACRegister.getCurrentValue();
            operatedRegister.setCurrentValue(registerVal, false);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case STORE:
            operatedRegister = getRegister(register.toString());
            //operatedRegister.setCurrentValue(this.ACRegister.getCurrentValue(), false);
            setACRegisterVal(operatedRegister.getCurrentValue(), false);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case MOV:
            operatedRegister = getRegister(register.toString());
            secondOperatedRegister = getRegister(value.toString());
            int intUnsignedVal = 0;
            if (secondOperatedRegister != null) {
              operatedRegister.setCurrentValue(secondOperatedRegister.getCurrentValue(), false);
              setPCRegisterVal(i + 1, false);
              setIRRegisterVal(i, false);
              break;
            } else if (value.toString().charAt(0) == '-') {
              String unsignedVal = value.toString().replaceFirst("-", "");
              intUnsignedVal = Integer.parseInt(unsignedVal) * -1;
            } else {
              intUnsignedVal = Integer.parseInt(value.toString());
            }
            operatedRegister.setCurrentValue(intUnsignedVal, false);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case SUB:
            operatedRegister = getRegister(register.toString());
            registerVal = operatedRegister.getCurrentValue();
            setACRegisterVal(registerVal, true);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case ADD:
            operatedRegister = getRegister(register.toString());
            registerVal = operatedRegister.getCurrentValue();
            setACRegisterVal(registerVal, true);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case INC:
            operatedRegister = getRegister(register.toString());
            if (operatedRegister == null) {
              setACRegisterVal(1, true);
            } else {
              secondOperatedRegister = getRegister(register.toString());
              secondOperatedRegister.setCurrentValue(1, true);
            }
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case DEC:
            operatedRegister = getRegister(register.toString());
            if (operatedRegister == null) {
              setACRegisterVal(-1, true);
            } else {
              secondOperatedRegister = getRegister(register.toString());
              secondOperatedRegister.setCurrentValue(-1, true);
            }
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case SWAP:
            operatedRegister = getRegister(register.toString());
            secondOperatedRegister = getRegister(value.toString());
            firstRegisterVal = operatedRegister.getCurrentValue();
            secondRegisterVal = secondOperatedRegister.getCurrentValue();
            operatedRegister.setCurrentValue(secondRegisterVal, false);
            secondOperatedRegister.setCurrentValue(firstRegisterVal, false);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case PUSH:
            operatedRegister = getRegister(register.toString());
            cpuStack.push(operatedRegister.getCurrentValue());
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case POP:
            operatedRegister = getRegister(register.toString());
            operatedRegister.setCurrentValue(cpuStack.pop(), false);
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case CMP:
            // if true, saves value 1 on stack, else 0.
            operatedRegister = getRegister(register.toString());
            secondOperatedRegister = getRegister(value.toString());
            firstRegisterVal = operatedRegister.getCurrentValue();
            secondRegisterVal = secondOperatedRegister.getCurrentValue();
            if (firstRegisterVal == secondRegisterVal) {
              cpuStack.push(1);
            } else {
              cpuStack.push(0);
            }
            setPCRegisterVal(i + 1, false);
            setIRRegisterVal(i, false);
            break;
          case JMP:
            System.out.println("NUM TO CONVERT IS: " + instruction.getInst()[1]);
            instrJump = Integer.parseInt(instruction.getInst()[1]);
            setPCRegisterVal(i + instrJump + 1, false);
            setIRRegisterVal(i + instrJump, false);
            if (memOverflow(i + instrJump)) {
              JOptionPane.showMessageDialog(null, "MEMORY OVERFLOW. NO SUCH JUMP BOUNDARIES: " + i + instrJump);
            }
            break;
          case JE:
            if (cpuStack.pop() == 1) {
              instrJump = Integer.parseInt(instruction.getInst()[1]);
              setPCRegisterVal(i + instrJump, false);
              setIRRegisterVal(i + instrJump - 1, false);
            } else {
              setPCRegisterVal(i + 1, false);
              setIRRegisterVal(i, false);
            }
            if (memOverflow(i + instrJump)) {
              JOptionPane.showMessageDialog(null, "MEMORY OVERFLOW. NO SUCH JUMP BOUNDARIES: " + i + instrJump);
            }
            break;
          case JNE:
            if (cpuStack.pop() == 0) {
              instrJump = Integer.parseInt(instruction.getInst()[1]);
              setPCRegisterVal(i + instrJump, false);
              setIRRegisterVal(i + instrJump - 1, false);
            } else {
              setPCRegisterVal(i + 1, false);
              setIRRegisterVal(i, false);
            }
            if (memOverflow(i + instrJump)) {
              JOptionPane.showMessageDialog(null, "MEMORY OVERFLOW. NO SUCH JUMP BOUNDARIES: " + (i + instrJump));
            }
            break;
          default:
            break;
        }
      }
      //i += 1;
      //instrIndex += instrJump;
      //showRegistersStatus();
    } else {
      //JOptionPane.showMessageDialog(null, "ALL INSTRUCTIONS WERE EXECUTED");
    }
    if (instruction != null) {
      instruction.reduceWeight();
      if (instruction.getWeight() > 0) {
        setProgramBCPValues(this.memory.getProgram().get(this.currentProgramRunning));
        return 0;
      }
      return 1;
    } else {
      this.currentProgramRunning += 1;
      return -1;
    }
  }

  private void setProgramBCPValues(Program program) {

    BCP programBCP = program.getProgramBCP();
    programBCP.setPCRVal(this.PCRegister.getCurrentValue());
    programBCP.setIRRVal(this.IRRegister.getCurrentValue());
    programBCP.setAXRVal(this.AXRegister.getCurrentValue());
    programBCP.setBXRVal(this.BXRegister.getCurrentValue());
    programBCP.setCXRVal(this.CXRegister.getCurrentValue());
    programBCP.setDXRVal(this.DXRegister.getCurrentValue());
  }

  private boolean memOverflow(int instrIndex) {

    if (instrIndex >= this.memory.getProgram().size()) {
      return true;
    }
    return false;

  }

  private void showRegistersStatus() {

    System.out.println("AC\t\tIR\t\tPC\t\tAX\t\tBX\t\tCX\t\tDX\t\tStack");
    System.out.println(this.ACRegister + "\t" + this.IRRegister + "\t" + this.PCRegister + "\t"
            + this.AXRegister + "\t" + this.BXRegister + "\t" + this.CXRegister + "\t" + this.DXRegister + "\t" + this.cpuStack.toString());
  }
}
