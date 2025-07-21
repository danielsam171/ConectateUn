package com.example.estructuras;

import android.app.Application;
import android.util.Log; // Es buena práctica usar Log en Android en lugar de System.out

import java.util.ArrayList;


public class MiAplication extends Application {

    // Tus HashMaps globales
    // Es buena práctica hacerlos privados y acceder a ellos mediante getters
    // o métodos específicos si necesitas más control, pero para empezar
    // públicos está bien para entender el concepto.

    //crear variables para Hash para acceder a ellas desde cualquier archivo
    public HashMap<Integer, Estudiante> hash_Estudiantes_app;
    public HashMap<String, ArrayList<Estudiante> > hash_deportes_app;



    //todo crear variable grafo publico para acceder a ella desde cualquier archivo


    private static final String TAG = "MiAplicacion"; // Tag para los logs

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializa tus HashMaps aquí.
        // Esto se ejecutará SÓLO UNA VEZ cuando la aplicación se inicie.
        hash_Estudiantes_app = new HashMap<>(10); // Puedes ajustar la capacidad inicial
        hash_deportes_app = new HashMap<>(10);  // Puedes ajustar la capacidad inicial

        Log.i(TAG, "onCreate: HashMaps globales inicializados.");

        // Aquí podrías, por ejemplo, pre-cargar algunos deportes si siempre deben existir
        // hash_deportes_app.put("Fútbol", new ArrayList<>());
        // hash_deportes_app.put("Baloncesto", new ArrayList<>());
        // etc.
    }

    // Opcional: Métodos getter para un acceso más controlado (recomendado a largo plazo)
    public HashMap<Integer, Estudiante> getHashEstudiantes() {
        return hash_Estudiantes_app;
    }

    public HashMap<String, ArrayList<Estudiante>> getHashDeportes() {
        return hash_deportes_app;
    }

    //todo crear metodo getter para grafo

}