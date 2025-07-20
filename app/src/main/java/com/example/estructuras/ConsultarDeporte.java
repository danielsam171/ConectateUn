package com.example.estructuras;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Arrays;

public class ConsultarDeporte extends AppCompatActivity {
    //Variable para la etiqueta del boton de seleccionar deporte
    private TextView ElegirDeporte;


    //Arreglo de deportes para poder seleccionar
    private String deportes[] = {"Fútbol", "Voleibol", "Baloncesto", "Ping Pong","Rugbi"};


    //Variable para la tabla con el deporte a seleccionar
    private ListView tabla1;


    // Variable para almacenar la opción seleccionada. Inicializada en -1 (ninguna seleccionada)
    private int indexOption = -1;


    // Lista que contendrá la única opción seleccionada para el ListView
    private ArrayList<String> listaTabla1 = new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    
    
    //Variables para la lista o tabla 2, que es la de las personas que practican estos deportes
    private ListView tabla2;
    private ArrayList<String> listaPersonas = new ArrayList<>(); //Estructura de datos que estemos usando
    private ArrayAdapter<String> adapter2;


    //Listas de personas para cada deporte, la idea es en vez de esas listas, asociar cada grafo o como se esté manejando cada deporte
    private final ArrayList<String> personasFutbol = new ArrayList<>(Arrays.asList("Juan David Garcia", "Daniel Mauricio Samaca"));
    private final ArrayList<String> personasVoleibol = new ArrayList<>(Arrays.asList("Germán Camilo Rodriguez", "Daniel Alfonso Cely"));
    private final ArrayList<String> personasBaloncesto = new ArrayList<>(Arrays.asList("Juan Luis Arteaga", "David Herrera"));
    private final ArrayList<String> personasPingPong = new ArrayList<>(Arrays.asList("Sofia Cardenas", "Paola Mora")); // Ejemplo adicional
    private final ArrayList<String> personasRugbi = new ArrayList<>(Arrays.asList("Junior Fino", "Lucia Promotores")); // Ejemplo adicional
    











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_deporte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Asignar variables con sus respectivos IDs del xml para la tabla 1
        ElegirDeporte = findViewById(R.id.agrSelectDeportes);
        tabla1 = findViewById(R.id.ListaConsDeportes);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTabla1);
        tabla1.setAdapter(adapter1);
        ElegirDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenu();
            }
        });


        //Asignar variables con sus respectivos IDs del xml para la tabla 2
        tabla2 = findViewById(R.id.ListaConsPers);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPersonas);
        tabla2.setAdapter(adapter2);
        tabla2.setVisibility(View.GONE); //Es para que la lista de personas no aparezca al inicio












    }

    private void mostrarMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un Deporte"); // Título para el diálogo

        // Configura el menú de selección única (radio buttons)
        builder.setSingleChoiceItems(deportes, indexOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 'which' es el índice de la opción que el usuario acaba de tocar
                indexOption = which; // Guarda la opción seleccionada
            }
        });

        // Configurar el botón "Aceptar"
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Limpia la lista del ListView para añadir solo la nueva selección
                listaTabla1.clear();
                listaPersonas.clear();

                // Si se ha seleccionado una opción (indexOption no es -1)
                if (indexOption != -1) {
                    listaTabla1.add(deportes[indexOption]); // Añade la opción seleccionada a la lista
                    switch (indexOption) {
                        case 0: // Índice para "Fútbol"
                            listaPersonas.addAll(personasFutbol);
                            break;
                        case 1: // Índice para "Voleibol"
                            listaPersonas.addAll(personasVoleibol);
                            break;
                        case 2: // Índice para "Baloncesto"
                            listaPersonas.addAll(personasBaloncesto);
                            break;
                        case 3: // Índice para "Ping Pong"
                            listaPersonas.addAll(personasPingPong);
                            break;
                        case 4: // Índice para "Rugbi"
                            listaPersonas.addAll(personasRugbi);
                            break;
                        default:
                            // Si por alguna razón indexOption es inválido, la lista de personas queda vacía.
                            break;
                    }
                    tabla2.setVisibility(View.VISIBLE);
                }
                else{
                    tabla2.setVisibility(View.GONE);
                }


                // Notifica al adaptador que los datos han cambiado para que el ListView se actualice
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();

                dialog.dismiss(); // Cierra el diálogo después de la selección
            }
        });

        // Configurar el botón "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Simplemente cierra el diálogo
            }
        });

        // Evita que el diálogo se cierre al tocar fuera (opcional, pero buena práctica)
        builder.setCancelable(false);

        // Crea y muestra el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}