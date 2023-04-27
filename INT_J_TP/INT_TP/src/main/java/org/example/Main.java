package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.example.exemptions.SQLExcep;
import org.example.models.Persona;
import org.example.models.Pronostico;
import org.example.utils.LectorCSV;
import org.example.utils.LectorDB;
import org.example.ManCSV;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws SQLExcep, CsvValidationException, IOException {
        Integer puntajePorPartido = 2;
        Integer puntajeExtraRonda = 10;
        Integer puntajeExtraFase = 20;

        LectorCSV lectorCSV = new LectorCSV();
        lectorCSV.resultados();
        LectorDB lectorDB = new LectorDB(lectorCSV);
        lectorDB.read("C:\\Users\\godoy\\Desktop\\INT_J_TP\\INT_TP\\src\\main\\resources\\config.csv");
        lectorDB.pronosticos();
        calcularPuntos(lectorDB, puntajePorPartido, puntajeExtraRonda, puntajeExtraFase);
    }

    private static void calcularPuntos(LectorDB lectorDB, Integer puntajePorPartido, Integer puntajeExtraRonda, Integer puntajeExtraFase) {
        for(Pronostico p : lectorDB.getPronostico()){
            p.getResultado();
            p.getPartido().getResultado();
            if(p.success()){
                p.getPersona().sumarPunto(puntajePorPartido);
                p.getPersona().addAcierto();
            }
        }
        for(Persona p : lectorDB.getPersonas()){
          if(lectorDB.acertoTodosLosPronosticosRonda(p, 1)){
              p.sumarPunto(puntajeExtraRonda);
          }
            if(lectorDB.acertoTodosLosPronosticosFases(p, 1)){
                p.sumarPunto(puntajeExtraFase);
            }
        }
        
        for(Persona p : lectorDB.getPersonas()){
            System.out.println("==================================");
            System.out.println("");
            System.out.println("Usuario: " + p.getNombre());
            System.out.println("");
            System.out.println("Aciertos: " + p.getAciertos());
            System.out.println("");
            System.out.println("Puntaje: " + p.getPuntaje());
            System.out.println("");
            System.out.println("==================================");
        }
    }
}