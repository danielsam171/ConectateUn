package com.example.estructuras;

import android.content.DialogInterface;
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

import java.util.ArrayList;

public class InfoEstudiante extends AppCompatActivity {

    //Variables para recoger la informacion de los deportes
    TextView tvSelectDeportes; //La vista que funciona como nuestro botón
    String[] deportes = {"Natación", "Fútbol", "Voleibol", "Baloncesto", "Tenis"};//La lista de todas las opciones posibles
    boolean[] seleccionados; //Un array de booleanos para rastrear qué casillas están marcadas.
    ArrayList<Integer> deportesSeleccionados = new ArrayList<>(); //Una lista dinámica para guardar los ÍNDICES de los elementos que el usuario selecciona.

    //Esto es para mostrar en una tabla
    ArrayList<String> listaDeNombres = new ArrayList<>(); ////lista de deportes ya seleccionados y que sale en la tabla
    ArrayAdapter<String> adapter; //El adaptador para conectar la lista
    private ListView listView; // La lista que voy a mostrar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_estudiante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Conexion para mostrar en tabla
        listView = findViewById(R.id.info_estudiante_deportes);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeNombres);
        listView.setAdapter(adapter);

        // Conectamos la variable Java con el elemento del layout XML usando su ID
        tvSelectDeportes = findViewById(R.id.tvSelectDeportes);

        // Inicializamos el array de booleanos con el tamaño correcto, todos en 'false'
        seleccionados = new boolean[deportes.length];

        // Establecemos el "oyente" de clics. Este código se ejecutará CADA VEZ que el usuario toque el TextView.
        tvSelectDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // --- CONSTRUCCIÓN DEL DIÁLOGO ---

                AlertDialog.Builder builder = new AlertDialog.Builder(InfoEstudiante.this);//Crear el Constructor del Diálogo. Es como el esqueleto del diálogo.
                builder.setTitle("Selecciona tus deportes favoritos");//Asignar un título para que el usuario sepa qué hacer.
                //Metodo para pasar las opciones y los estados de selección (inicialmente vacios todos).
                builder.setMultiChoiceItems(deportes, seleccionados, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Este código se ejecuta CADA VEZ que se marca o desmarca una casilla.
                        // 'which' es el índice (posición) del elemento que se tocó.
                        // 'isChecked' es el nuevo estado (true si está marcado, false si no).
                        if (isChecked) {
                            // Si el usuario lo marca, añadimos su índice a nuestra lista de seguimiento.
                            deportesSeleccionados.add(which);
                        } else {
                            // Si el usuario lo desmarca, removemos su índice de la lista.
                            // Usamos Integer.valueOf() para asegurar que borre el objeto y no la posición.
                            deportesSeleccionados.remove(Integer.valueOf(which));
                        }
                    }
                });

                // 4. Configurar el botón "Aceptar". Se ejecuta cuando el usuario termina su selección.
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Usamos StringBuilder para construir eficientemente el texto final.
                        StringBuilder stringBuilder = new StringBuilder();

                        //Para mostrar en la tabla primero la limpiamos de cualquier residuo
                        listaDeNombres.clear();

                        for (int i = 0; i < deportesSeleccionados.size(); i++) {
                            // Obtenemos el texto del deporte usando el índice que guardamos.
                            stringBuilder.append(deportes[deportesSeleccionados.get(i)]);
                            listaDeNombres.add(deportes[deportesSeleccionados.get(i)]);

                            // Si no es el último elemento de la lista, añadimos una coma y un espacio.
                            if (i != deportesSeleccionados.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        adapter.notifyDataSetChanged(); //Actulizar la tabla para mostrar los resultados si fueron alterados


                        //procesarDeportesSeleccionados(listaDeNombres);

                        // Si no se seleccionó nada, volvemos al texto original.
                        if (stringBuilder.length() == 0) {
                            tvSelectDeportes.setText("Selecciona tus deportes");
                        } else {
                            // Finalmente, actualizamos el texto de nuestro "Botón Falso".
                            tvSelectDeportes.setText(stringBuilder.toString());
                        }
                    }
                });

                // 5. Configurar el botón "Cancelar".
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Simplemente cierra el diálogo sin hacer ningún cambio.
                        dialog.dismiss();
                    }
                });

                // 6. Evita que el diálogo se cierre si el usuario toca fuera de él.
                // Esto le obliga a usar los botones "Aceptar" o "Cancelar".
                builder.setCancelable(false);

                // 7. Finalmente, creamos el diálogo con toda la configuración y lo mostramos.
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });






    }

}