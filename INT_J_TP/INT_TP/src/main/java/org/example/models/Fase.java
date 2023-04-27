package org.example.models;
import java.util.List;
import java.util.ArrayList;

public class Fase {
    private List<Ronda> rondas;
    private int numFase;
    public Fase(int numFase){
        this.numFase = numFase;
        this.rondas = new ArrayList<>();
    }

    public int getNumFase() {
        return numFase;
    }
    public void agregarRonda(Ronda ronda){
        for(Ronda r : this.rondas){
            if(r.getNumRonda() == ronda.getNumRonda()){
                throw new RuntimeException("La ronda ya existe");
            }
        }
        this.rondas.add(ronda);
    }

    public Ronda getRonda(int numRonda) {
        Ronda ronda = null;
        for(Ronda r : this.rondas){
            if(r.getNumRonda() == numFase){
                ronda = r;
            }
        }
        if(ronda == null){
            throw  new RuntimeException("La ronda no existe");
        }
        return ronda;
    }
}
