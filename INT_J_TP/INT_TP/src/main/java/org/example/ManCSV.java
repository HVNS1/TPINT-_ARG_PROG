package org.example;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.constant.Constable;

public class ManCSV {
    private BufferedReader lector;
    private String linea;
    private String partes[];

    public void read(String fileName) {
        try{
            lector = new BufferedReader(new FileReader(fileName));
            while ((linea = lector.readLine()) != null){
                partes = linea.split(";");
            }
            lector.close();
            linea = null;
            partes = null;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
