package com.example.estructuras;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import android.widget.Toast;

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

    private TextView nombreCampo;
    private TextView apellidoCampo;

    private EditText cCampo;// Variable para gestionar el campo de CC
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

        cCampo = findViewById(R.id.agrEstCC);
        nombreCampo = findViewById(R.id.inf_txt_nombre);
        apellidoCampo = findViewById(R.id.inf_txt_apellido);

        
    }
    public void irMenuprincipal(View view){
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void buscar(View view){

        String textoId = cCampo.getText().toString();

        if (textoId.isEmpty()) {
            cCampo.setError("Por favor, ingrese un ID");
            return;
        }

        int idBuscado;
        try {
            idBuscado = Integer.parseInt(textoId);
        } catch (NumberFormatException ex) {
            cCampo.setError("El ID debe ser un número");
            return;
        }

        // 1. Intentamos obtener el estudiante del HashMap.
        Estudiante estudianteEncontrado = miApp.getHashEstudiantes().get(idBuscado);

        // 2. Verificamos explícitamente si el estudiante fue encontrado o no.
        if (estudianteEncontrado != null) {
            ListaDeportesSeleccionados.clear();
            ListaDeportesSeleccionados2.clear();

            // Rellenamos las listas con los datos del estudiante encontrado.
            String arrDS1[] = estudianteEncontrado.getDeportesPracticados();
            if (arrDS1 != null) {
                for (String deporte : arrDS1) {
                    if (deporte != null) { // Añadimos una comprobación extra por si hay nulos en el array.
                        ListaDeportesSeleccionados.add(deporte);
                    }
                }
            }

            String arrDS2[] = estudianteEncontrado.getDeportesInteresados();
            if (arrDS2 != null) {
                for (String deporte : arrDS2) {
                    if (deporte != null) {
                        ListaDeportesSeleccionados2.add(deporte);
                    }
                }
            }
            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            nombreCampo.setText(estudianteEncontrado.getNombre());
            apellidoCampo.setText(estudianteEncontrado.getApellido());
        }
        else{
            Toast.makeText(getApplicationContext(), "No existe un estudiante asociado a este ID",
                    Toast.LENGTH_SHORT).show();

            nombreCampo.setText("Estudiante no encontrado");
            apellidoCampo.setText(""); // Dejar en blanco el apellido es más limpio

            ListaDeportesSeleccionados.clear();
            ListaDeportesSeleccionados2.clear();

            adapter.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
        }
    }
}