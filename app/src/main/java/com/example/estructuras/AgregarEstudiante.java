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

public class AgregarEstudiante extends AppCompatActivity {

    //Variables para seleccionar los deportes en la primera vista
    TextView vistaSelecDepor1 ;
    String deportes[] = {"Fútbol", "Voleibol", "Baloncesto", "Ping Pong","Rugbi"};
    boolean blnSeleccion1[];
    ArrayList<Integer> indiceDeporSelect1 = new ArrayList<>();

    //Variables para la tabla1 que se muestra posterior a la eleccion
    ArrayList<String> ListadeportesSeleccionados1 =  new ArrayList<>();
    ArrayAdapter<String> adapter1;
    private ListView tabla1;

    //Variables para la segunda vista (pestana de seleccion de deportes

    TextView vistaSelecDepor2 ;
    boolean blnSeleccion2[];
    ArrayList<Integer> indiceDeporSelect2 = new ArrayList<>();

    //Variables para la tabla2 que se muestra posterior a la eleccion
    ArrayList<String> ListadeportesSeleccionados2 =  new ArrayList<>();
    ArrayAdapter<String> adapter2;
    private ListView tabla2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_estudiante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Configuracion del primer TextView para la tabla1 de deportes que practica
        tabla1 = findViewById(R.id.agrTablaEstudiante_deportes1);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListadeportesSeleccionados1);
        tabla1.setAdapter(adapter1);

        vistaSelecDepor1 = findViewById(R.id.agrSelectDeportes1);
        blnSeleccion1 = new boolean[deportes.length];

        vistaSelecDepor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creamos la comunicacion para las elecciones del ususario

                AlertDialog.Builder builder = new AlertDialog.Builder(AgregarEstudiante.this);//Crear el Constructor del Diálogo. Es como el esqueleto del diálogo.
                builder.setTitle("Que Practicas");//Asignar un título para que el usuario sepa qué hacer.
                //Metodo para pasar las opciones y los estados de selección (inicialmente vacios todos).
                builder.setMultiChoiceItems(deportes, blnSeleccion1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Este código se ejecuta CADA VEZ que se marca o desmarca una casilla.
                        // 'which' es el índice (posición) del elemento que se tocó.
                        // 'isChecked' es el nuevo estado (true si está marcado, false si no).
                        if (isChecked) {
                            // Si el usuario lo marca, añadimos su índice a nuestra lista de seguimiento.
                            indiceDeporSelect1.add(which);
                        } else {
                            // Si el usuario lo desmarca, removemos su índice de la lista.
                            // Usamos Integer.valueOf() para asegurar que borre el objeto y no la posición.
                            indiceDeporSelect1.remove(Integer.valueOf(which));
                        }
                    }
                });

                // 4. Configurar el botón "Aceptar". Se ejecuta cuando el usuario termina su selección.
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Usamos StringBuilder para construir eficientemente el texto final.
                        StringBuilder stringBuilder = new StringBuilder();

                        //Para mostrar en la tabla1 primero la limpiamos de cualquier residuo
                        ListadeportesSeleccionados1.clear();

                        for (int i = 0; i < indiceDeporSelect1.size(); i++) {
                            // Obtenemos el texto del deporte usando el índice que guardamos.
                            stringBuilder.append(deportes[indiceDeporSelect1.get(i)]);
                            ListadeportesSeleccionados1.add(deportes[indiceDeporSelect1.get(i)]);

                            // Si no es el último elemento de la lista, añadimos una coma y un espacio.
                            if (i != ListadeportesSeleccionados1.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        adapter1.notifyDataSetChanged(); //Actulizar la tabla1 para mostrar los resultados si fueron alterados


                        //procesarDeportesSeleccionados(ListadeportesSeleccionados11);

                        // Si no se seleccionó nada, volvemos al texto original.
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

        tabla2 = findViewById(R.id.agrTablaEstudiante_deportes2);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListadeportesSeleccionados2);
        tabla2.setAdapter(adapter2);

        vistaSelecDepor2 = findViewById(R.id.agrSelectDeportes2);
        blnSeleccion2 = new boolean[deportes.length];

        vistaSelecDepor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creamos la comunicacion para las elecciones del ususario

                AlertDialog.Builder builder = new AlertDialog.Builder(AgregarEstudiante.this);//Crear el Constructor del Diálogo. Es como el esqueleto del diálogo.
                builder.setTitle("Que te interesan");//Asignar un título para que el usuario sepa qué hacer.
                //Metodo para pasar las opciones y los estados de selección (inicialmente vacios todos).
                builder.setMultiChoiceItems(deportes, blnSeleccion2, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // Este código se ejecuta CADA VEZ que se marca o desmarca una casilla.
                        // 'which' es el índice (posición) del elemento que se tocó.
                        // 'isChecked' es el nuevo estado (true si está marcado, false si no).
                        if (isChecked) {
                            // Si el usuario lo marca, añadimos su índice a nuestra lista de seguimiento.
                            indiceDeporSelect2.add(which);
                        } else {
                            // Si el usuario lo desmarca, removemos su índice de la lista.
                            // Usamos Integer.valueOf() para asegurar que borre el objeto y no la posición.
                            indiceDeporSelect2.remove(Integer.valueOf(which));
                        }
                    }
                });

                // 4. Configurar el botón "Aceptar". Se ejecuta cuando el usuario termina su selección.
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Usamos StringBuilder para construir eficientemente el texto final.
                        StringBuilder stringBuilder = new StringBuilder();

                        //Para mostrar en la tabla1 primero la limpiamos de cualquier residuo
                        ListadeportesSeleccionados2.clear();

                        for (int i = 0; i < indiceDeporSelect2.size(); i++) {
                            // Obtenemos el texto del deporte usando el índice que guardamos.
                            stringBuilder.append(deportes[indiceDeporSelect2.get(i)]);
                            ListadeportesSeleccionados2.add(deportes[indiceDeporSelect2.get(i)]);

                            // Si no es el último elemento de la lista, añadimos una coma y un espacio.
                            if (i != ListadeportesSeleccionados2.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        adapter2.notifyDataSetChanged(); //Actulizar la tabla1 para mostrar los resultados si fueron alterados


                        //procesarDeportesSeleccionados(ListadeportesSeleccionados11);

                        // Si no se seleccionó nada, volvemos al texto original.
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