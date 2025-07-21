
package com.example.estructuras;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Estudiante {
    private final int id;
    private String nombre;
    private String apellido;
    private String[] deportes_practicados;
    private String[] deportes_interesados;


    public Estudiante(String nombre, String apellido, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.deportes_practicados = new String[5];
        this.deportes_interesados = new String[5];
    }

    //Getters y Setters
    public String[] getDeportesPracticados() {
        return deportes_practicados;
    } //devuelve el array de deportes practicados

    public void setDeportesPracticados(String[] deportes_practicados) {
        this.deportes_practicados = deportes_practicados;
    }//setea el array de deportes practicados

    public String[] getDeportesInteresados() {
        return deportes_interesados;
    }//devuelve el array de deportes interesados

    public void setDeportesInteresados(String[] deportes_interesados) {
        this.deportes_interesados = deportes_interesados;
    }//setea el array de deportes interesados

    public void setDeportesPracticados_Arraylist(@NonNull ArrayList<String> deportes_practicados) {
        this.deportes_practicados = deportes_practicados.toArray(new String[deportes_practicados.size()]);
    }//setea el array de deportes practicados por medio de un ArrayList

    public void setDeportesInteresados_Arraylist(@NonNull ArrayList<String> deportes_interesados) {
        this.deportes_interesados = deportes_interesados.toArray(new String[deportes_interesados.size()]);
    }//setea el array de deportes interesados por medio de un ArrayList

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estudiante otro = (Estudiante) o;
        return this.id == otro.id; // Compare by student ID
    }



    public void imprimir(){
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("ID: " + id);
        System.out.println("Deportes practicados: " + String.join(", ", deportes_practicados));
        System.out.println("Deportes interesados: " + String.join(", ", deportes_interesados));
    }
}

