package org.example.models;
import java.util.List;
import java.util.ArrayList;
public class Ronda {
    private int numRonda;
    private List<Partido> partidos;
    public Ronda(int numRonda){
        this.numRonda = numRonda;
        this.partidos = new ArrayList<>();
    }
    public void agregarPartido(Partido partido){
        for(Partido p : this.partidos){
            if(p.igual(partido)){
                throw new RuntimeException("El partido ya existe");
            }
        }
        this.partidos.add(partido);
    }
    public int getNumRonda(){
        return numRonda;
    }

    public Partido getPartido(String equipo1, String equipo2) {
        Partido partido = null;
        for(Partido p : this.partidos){
            if(p.juegan(equipo1, equipo2)){
                partido = p;
            }
        }
        if(partido == null){
            throw new RuntimeException("El partido no existe");
        }
        return partido;
    }
}
