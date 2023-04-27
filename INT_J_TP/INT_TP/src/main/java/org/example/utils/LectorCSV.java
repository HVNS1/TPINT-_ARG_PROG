package org.example.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParser;
import com.opencsv.exceptions.CsvValidationException;
import org.example.exemptions.FasesExemption;
import org.example.models.Equipo;
import org.example.models.Partido;
import org.example.models.Fase;
import org.example.models.Ronda;

public class LectorCSV {
    List<Fase> fases;
    List<Equipo> equipos;
    /*
    private BufferedReader lector;
    private String partes[];
     */
    private CSVReader lector;
    private String linea;
    public LectorCSV(){
        this.fases = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }
    /*
    public void reader(String fileName1) {
        try{
            lector = new BufferedReader(new FileReader(fileName1));
            while ((linea = lector.readLine()) != null){
                partes = linea.split(";");
            }
            lector.close();
            linea = null;
            //partes = null;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }*/
    public String[] leerFila(String rutaArchivo) throws IOException, CsvValidationException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader lector = new CSVReaderBuilder(new FileReader(rutaArchivo))
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();
        String[] fila = lector.readNext();
        lector.close();
        return fila;
    }
    public void resultados() {
        String[] fila = null;
        try {
            String rutaArchivo = "C:\\Users\\godoy\\Desktop\\INT_J_TP\\INT_TP\\src\\main\\resources\\results.csv";
            fila = leerFila(rutaArchivo);
            Equipo argentina = this.getTeam(fila[0]);
            Equipo polonia = this.getTeam(fila[4]);
            Equipo mexico = this.getTeam(fila[6]);
            Equipo arabia_saudita = this.getTeam(fila[2]);

            Partido p1 = new Partido(argentina, arabia_saudita, Integer.parseInt(fila[1]), Integer.parseInt(fila[3]));
            Partido p3 = new Partido(argentina, mexico, Integer.parseInt(fila[9]), Integer.parseInt(fila[11]));
            Partido p4 = new Partido(arabia_saudita, polonia, Integer.parseInt(fila[13]), Integer.parseInt(fila[15]));

            Ronda r1 = new Ronda(1);
            r1.agregarPartido(p1);
            r1.agregarPartido(p3);
            r1.agregarPartido(p4);

            Fase f1 = new Fase(1);
            f1.agregarRonda(r1);
            this.agregarFase(f1);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private void agregarFase(Fase fase) {
        for(Fase f : this.fases){
            if(f.getNumFase() == fase.getNumFase()){
                throw new FasesExemption("la fase ya existe");
            }
        }
        this.fases.add(fase);
    }

    private Equipo getTeam(String nombreEquipo){
        Equipo equipo = null;

        for(Equipo e : this.equipos){
            if(e.getNombre().equals(nombreEquipo)){
                equipo = e;
            }
        }
        if(equipo == null){
            equipo = new Equipo(nombreEquipo);
            this.equipos.add(equipo);
        }
        return equipo;
    }

    public Fase getFase(int numFase) {
        Fase fase = null;
        for(Fase f : this.fases){
            if(f.getNumFase() == numFase){
                fase = f;
            }
        }
        if(fase == null){
           throw  new FasesExemption("La fase no existe");
        }
        return fase;
    }
}
