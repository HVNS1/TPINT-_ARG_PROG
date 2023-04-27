package org.example.utils;
import org.example.exemptions.SQLExcep;
import org.example.exemptions.ResultadoInvalidoExemption;
import org.example.models.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class LectorDB {
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
       //     partes = null;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private List<Pronostico> pronosticos;
    private List<Persona> personas;
    private LectorCSV lectorCSV;

    public LectorDB(LectorCSV lectorCSV){
        this.lectorCSV = lectorCSV;
        this.pronosticos = new ArrayList<>();
        this.personas = new ArrayList<>();
    }
    public void pronosticos() throws SQLExcep {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+partes[0], ""+partes[1], ""+partes[2]);
            Statement stmt = con.createStatement();
            ResultSet resultado = stmt.executeQuery("select * from pronostico");
            while (resultado.next()){

                Fase fase = lectorCSV.getFase(resultado.getInt("fase"));
                Ronda ronda = fase.getRonda(resultado.getInt("ronda"));
                Persona persona = this.getPersona(resultado.getString("persona"));
                Partido partido = ronda.getPartido(
                        resultado.getString("equipo_1"),
                        resultado.getString("equipo_2")
                );
                boolean gana1 = resultado.getBoolean("gana_1");
                boolean empate = resultado.getBoolean("empate");
                boolean gana2 = resultado.getBoolean("gana_2");

                Resultado res = null;
                        if(gana1){
                            res = Resultado.Gana1;
                        } else if (gana2) {
                            res = Resultado.Gana2;
                        }else if(empate){
                            res = Resultado.Empate;
                        }else {
                            throw new ResultadoInvalidoExemption("No hay resultados");
                        }
                Pronostico pronostico = new Pronostico(fase, ronda, persona, partido, res);
                        this.setPronostico(pronostico);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException | ResultadoInvalidoExemption e){
            throw new SQLExcep("Error dedatabase");
        }
    }

    private void setPronostico(Pronostico pronostico) {
        for(Pronostico p : this.pronosticos){
            if(p.getFase().equals(pronostico.getFase())
                    && p.getPartido().equals(pronostico.getPartido())
                    && p.getRonda().equals(pronostico.getRonda())
                    && p.getPersona().equals(pronostico.getPersona())
            ){
                throw new RuntimeException("El pronostico ya existe");
            }
        }
        this.pronosticos.add(pronostico);
    }
    public boolean acertoTodosLosPronosticosRonda(Persona p, int ronda){
        List<Pronostico> pronosticos1 = this.pronosticos.stream().filter(
                pronostico ->
                    pronostico.getPersona().equals(p)
        ).toList();
        pronosticos1 = pronosticos1.stream().filter(
                pronostico -> pronostico.getRonda().getNumRonda() == ronda
        ).toList();
        return pronosticos1.stream().allMatch(
                pronostico -> pronostico.success()
        );
    }
    public boolean acertoTodosLosPronosticosFases(Persona p, int fases){
        List<Pronostico> pronosticos1 = this.pronosticos.stream().filter(
                pronostico ->
                        pronostico.getPersona().equals(p)
        ).toList();
        pronosticos1 = pronosticos1.stream().filter(
                pronostico -> pronostico.getFase().getNumFase() == fases
        ).toList();
        return pronosticos1.stream().allMatch(
                pronostico -> pronostico.success()
        );
    }

    private Persona getPersona(String numPersona) {
        Persona persona = null;
        for(Persona p : this.personas){
            if(p.getNombre().equals(numPersona)){
                persona = p;
            }
        }
        if(persona == null){
            persona = new Persona(numPersona);
            this.personas.add(persona);
        }
        return persona;
    }

    public List<Pronostico> getPronostico() {
        return pronosticos;
    }

    public List<Persona> getPersonas() {
        return personas;
    }
}
