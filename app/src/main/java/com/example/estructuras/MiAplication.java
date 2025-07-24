package com.example.estructuras;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class MiAplication extends Application {

    private static final String TAG = "MiAplication";

    // Mapas globales
    private HashMap<Integer, Estudiante> hashEstudiantesApp;
    private HashMap<String, ArrayList<Estudiante>> hashDeportesApp;
    private GrafoMulticapa grafoEstudiantes;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializar mapas vacíos
        hashEstudiantesApp = new HashMap<>(10);
        hashDeportesApp    = new HashMap<>(50);

        // Crear el grafo con los mapas (vacíos inicialmente)
        grafoEstudiantes = new GrafoMulticapa(hashEstudiantesApp, hashDeportesApp);

        Log.i(TAG, "onCreate: Mapas y grafo inicializados.");
    }

    /** Retorna el mapa de estudiantes (id -> Estudiante) */
    public HashMap<Integer, Estudiante> getHashEstudiantes() {
        return hashEstudiantesApp;
    }

    /** Retorna el mapa de deportes (deporte -> lista de Estudiantes) */
    public HashMap<String, ArrayList<Estudiante>> getHashDeportes() {
        return hashDeportesApp;
    }

    /** Retorna el grafo multicapa de estudiantes */
    public GrafoMulticapa getGrafoEstudiantes() {
        return grafoEstudiantes;
    }


}

