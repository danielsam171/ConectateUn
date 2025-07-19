
package com.example.estructuras;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;
    private String [] deportes_practicados;
    private String [] deportes_interesados;


    public Estudiante (String nombre, String apellido, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
    }

    public String[] getDeportesPracticados() {
        return deportes_practicados;
    }
    public void setDeportesPracticados(String[] deportes_practicados) {
        this.deportes_practicados = deportes_practicados;
    }
    public String[] getDeportesInteresados() {
        return deportes_interesados;
    }
    public void setDeportesInteresados(String[] deportes_interesados) {
        this.deportes_interesados = deportes_interesados;
    }

    public void setDeportesPracticados_Arraylist(@NonNull ArrayList<String> deportes_practicados) {
        this.deportes_practicados = deportes_practicados.toArray(new String[deportes_practicados.size()]);
    }

    public void setDeportesInteresados_Arraylist(@NonNull ArrayList<String> deportes_interesados) {
        this.deportes_interesados = deportes_interesados.toArray(new String[deportes_interesados.size()]);
    }

    public boolean equals(@NonNull Estudiante otro) {
        return this.id == otro.id;
    }

}



 */
