 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package computer;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import models.CPU;
import models.MainMemory;
import models.Program;
import gui.Win;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Chris & Jirgort
 */
public class Computer {
     static DefaultTableModel modelo;
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    
    Win win=new Win();
    win.setVisible(true);
    JTable table1 =new JTable();
    JTable table2 =new JTable();
    
    table1=win.getjTable2();
    table2=win.getjTable1();
    createModel(table1, 128);
    createModel(table2, 512);
    
  }
  
  private static void createModel(JTable table, int size){
       modelo= new  DefaultTableModel(){
       @Override
       public boolean isCellEditable(int filas, int columnas) {
          if (columnas == 2) {
            return true;

          } else {
            return false;
          }
        }
      };
      modelo.addColumn("Pos");
      modelo.addColumn("Valor en memoria");
      table.setModel(modelo);
      table.getColumnModel().getColumn(0).setMinWidth(60);
      table.getColumnModel().getColumn(0).setMaxWidth(60);
      for(int i=0;i<size;i++){
          modelo.setNumRows(size);
          table.setValueAt(i, i, 0);
      }
       }
}
      

