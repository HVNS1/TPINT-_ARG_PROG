package org.example.models;

public class Partido {
    private Equipo equipo_1;
    private Equipo equipo_2;
    private int cantidad_goles_1;
    private int cantidad_goles_2;
    
    public Partido(Equipo equipo_1, Equipo equipo_2, int cantidad_goles_1, int cantidad_goles_2){
        this.equipo_1 = equipo_1;
        this.equipo_2 = equipo_2;
        this.cantidad_goles_1 = cantidad_goles_1;
        this.cantidad_goles_2 = cantidad_goles_2;
    }
    public Equipo getEquipo1(){
        return equipo_1;
    }
    public Equipo getEquipo_2(){

        return equipo_2;
    }
    public int getCantidad_goles_1(){
        return cantidad_goles_1;
    }
    public int getCantidad_goles_2(){
        return cantidad_goles_2;
    }
    public boolean igual(Partido p){
        return (this.equipo_1.getNombre().equals(p.equipo_1.getNombre()) && this.equipo_2.getNombre().equals(p.equipo_2.getNombre()) )
                ||
                (this.equipo_1.getNombre().equals(p.equipo_2.getNombre()) && this.equipo_2.getNombre().equals(p.equipo_1.getNombre()));
    }

    public boolean juegan(String equipo1, String equipo2) {
        return (this.equipo_1.getNombre().equals(equipo1) && this.equipo_2.getNombre().equals(equipo2))
                ||
                (this.equipo_1.getNombre().equals(equipo2) && this.equipo_2.getNombre().equals(equipo1));
    }

    public Object getResultado() {
        if(cantidad_goles_1 > cantidad_goles_2){
            return Resultado.Gana1;
        } else if (cantidad_goles_2 > cantidad_goles_1) {
            return Resultado.Gana2;
        } else{
            return Resultado.Empate;
        }
    }
}
