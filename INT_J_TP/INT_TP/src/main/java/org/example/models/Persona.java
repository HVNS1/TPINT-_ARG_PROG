package org.example.models;

public class Persona {
    private String nombre;
    private Integer puntaje;
    private int aciertos;

    public Persona(String nombre){
        this.nombre = nombre;
        this.puntaje = 0;
        this.aciertos = 0;
    }
    public String getNombre(){
        return nombre;
    }
    public Integer getPuntaje(){
        return puntaje;
    }
    public int getAciertos(){
        return aciertos;
    }

    public void sumarPunto(Integer puntajeASumar) {
        this.puntaje += puntajeASumar;
    }

    public void addAcierto() {
        this.aciertos++;
    }
}
