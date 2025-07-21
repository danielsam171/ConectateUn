package com.example.estructuras;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Application;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class InfoEstudiante extends AppCompatActivity {

    String[] deportes = {"Fútbol", "Voleibol", "Baloncesto", "Ping Pong","Rugbi"};//La lista de todas las opciones posibles

    //Esto es para mostrar la tabla de "Practica"
    ArrayList<String> ListaDeportesSeleccionados = new ArrayList<>(); ////lista de deportes ya seleccionados y que sale en la tabla
    ArrayAdapter<String> adapter; //El adaptador para conectar la lista
    private ListView listView; // La lista que voy a mostrar, es el widget

    //crear variable miApp de la clase MiAplication
    private MiAplication miApp;

    //Esto es para mostrar la tabla de "Interesa"
    ArrayList<String> ListaDeportesSeleccionados2 = new ArrayList<>(); ////lista de deportes ya seleccionados y que sale en la tabla
    ArrayAdapter<String> adapter2; //El adaptador para conectar la lista
    private ListView listView2; // La lista que voy a mostrar, es el widget

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miApp = (MiAplication) getApplication(); // Obtener la instancia de la aplicación
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_estudiante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Conexion para mostrar en tabla "Practica"
        listView = findViewById(R.id.infDeportePractica);
        //ListaDeportesSeleccionados es la variable los deportes que practica el estudiante
        //Datos sinteticos para mostrar en lista de "Practica"
        ListaDeportesSeleccionados.add(deportes[0]);
        ListaDeportesSeleccionados.add(deportes[1]);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaDeportesSeleccionados);
        listView.setAdapter(adapter);

        //Conexion para mostrar en tabla "Practica"
        listView2 = findViewById(R.id.infDeporteInteresan);
        //ListaDeportesSeleccionados2 es la variable para los deportes que le interesan al estudiante
        //Datos sinteticos para mostrar en lista de "Interesan"
        ListaDeportesSeleccionados2.add(deportes[2]);
        ListaDeportesSeleccionados2.add(deportes[3]);
        ListaDeportesSeleccionados2.add(deportes[4]);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaDeportesSeleccionados2);
        listView2.setAdapter(adapter2);

        miApp.getHashEstudiantes().printTable();


    }
    public void irMenuprincipal(View view){
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}