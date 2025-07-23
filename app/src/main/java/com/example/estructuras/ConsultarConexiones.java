/*package com.example.estructuras;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class ConsultarConexiones extends AppCompatActivity {

    //Variable para gestionar el ID
    private EditText campoID;

    private MiAplication miApp;



    //Variables para seleccionar deporte
    TextView vistaSelecDeportes;
    String deportes[] = {"Fútbol", "Voleibol", "Baloncesto", "Ping Pong","Rugbi"};
    boolean blnSeleccion[];
    ArrayList<Integer> indiceDeporSelect1 = new ArrayList<>();



    //Variables para la tabla1 que se muestra posterior a la eleccion
    ArrayList<String> ListadeportesSeleccionados1 =  new ArrayList<>();
    ArrayAdapter<String> adapterCon1;
    private ListView tablaCon1;





    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miApp = (MiAplication) getApplication();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_conexiones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });







        //Configuracion de los campos de nombre, apellidos y CC para comuncarlos con los campos en xml
        campoID = findViewById(R.id.editTextText5);


        //Configuracion del TextView para la tabla de deportes que interesan
        tablaCon1 = findViewById(R.id.DeportesConexiones);
        adapterCon1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListadeportesSeleccionados1);
        tablaCon1.setAdapter(adapterCon1);

        vistaSelecDeportes = findViewById(R.id.ConsConxDepInt);
        blnSeleccion = new boolean[deportes.length];









        vistaSelecDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creamos la comunicacion para las elecciones del ususario

                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarConexiones.this);//Crear el Constructor del Diálogo. Es como el esqueleto del diálogo.
                builder.setTitle("Que Practicas");//Asignar un título para que el usuario sepa qué hacer.
                //Metodo para pasar las opciones y los estados de selección (inicialmente vacios todos).
                builder.setMultiChoiceItems(deportes, blnSeleccion, new DialogInterface.OnMultiChoiceClickListener() {
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
                        adapterCon1.notifyDataSetChanged(); //Actulizar la tabla1 para mostrar los resultados si fueron alterados


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
}*/
package com.example.estructuras;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class ConsultarConexiones extends AppCompatActivity {

    private static final String TAG = "ConsConexiones";

    private EditText campoID;
    private TextView vistaSelecDeportes;
    private ListView listaResultados;

    // Datos dinámicos para el diálogo de selección
    private ArrayList<String> deportesDisponibles;
    private boolean[] seleccionados;
    private ArrayList<Integer> indicesSeleccionados;

    // Adapter y lista para el ListView de resultados
    private ArrayList<String> resultadosConexiones;
    private ArrayAdapter<String> adapterResultados;

    private MiAplication miApp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miApp = (MiAplication) getApplication();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_conexiones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // Referencias a vistas
        campoID        = findViewById(R.id.editTextText5);
        vistaSelecDeportes = findViewById(R.id.ConsConxDepInt);
        listaResultados = findViewById(R.id.ListConexiones);

        // Inicializar estructuras
        resultadosConexiones = new ArrayList<>();
        adapterResultados    = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                resultadosConexiones);
        listaResultados.setAdapter(adapterResultados);

        indicesSeleccionados = new ArrayList<>();

        // Al pulsar el TextView (botón) para seleccionar deportes de interés:
        vistaSelecDeportes.setOnClickListener(v -> {
            // 1) Validar y obtener ID
            String textoId = campoID.getText().toString().trim();
            if (textoId.isEmpty()) {
                campoID.setError("Ingresa tu ID");
                return;
            }
            final int idOrigen;
            try {
                idOrigen = Integer.parseInt(textoId);
            } catch (NumberFormatException e) {
                campoID.setError("ID inválido");
                return;
            }

            // 2) Obtener estudiante y validar existencia
            Estudiante est = miApp.getHashEstudiantes().get(idOrigen);
            if (est == null) {
                campoID.setError("Estudiante no encontrado");
                return;
            }

            // 3) Registrar en logs las listas guardadas
            Log.d(TAG, "Practicados: " +
                    Arrays.toString(est.getDeportesPracticados()));
            Log.d(TAG, "Interés   : " +
                    est.getDeportesInteresados_ArrayList());

            // 4) Cargar sólo los deportes de interés
            ArrayList<String> deportesInteres = est.getDeportesInteresados_ArrayList();
            if (deportesInteres == null || deportesInteres.isEmpty()) {
                Toast.makeText(this,
                        "No tienes deportes de interés registrados",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            deportesDisponibles  = new ArrayList<>(deportesInteres);
            seleccionados        = new boolean[deportesDisponibles.size()];
            indicesSeleccionados.clear();

            // 5) Construir diálogo de selección múltiple
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona tus deportes de interés");
            builder.setMultiChoiceItems(
                    deportesDisponibles.toArray(new String[0]),
                    seleccionados,
                    (dialog, which, isChecked) -> {
                        if (isChecked) {
                            indicesSeleccionados.add(which);
                        } else {
                            indicesSeleccionados.remove(Integer.valueOf(which));
                        }
                    }
            );

            // 6) Botón Aceptar
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                if (indicesSeleccionados.isEmpty()) {
                    Toast.makeText(this,
                            "No seleccionaste deportes",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 6a) Construir lista de intereses elegidos
                ArrayList<String> seleccionadosPorNombre = new ArrayList<>();
                for (int idx : indicesSeleccionados) {
                    seleccionadosPorNombre.add(deportesDisponibles.get(idx));
                }
                // Mostrar la selección en el TextView
                vistaSelecDeportes.setText(
                        String.join(", ", seleccionadosPorNombre)
                );

                // 6b) Filtrar estudiantes que PRACTIQUEN esos deportes
                ArrayList<Estudiante> matches = filtrarEstudiantesPorDeportes(
                        seleccionadosPorNombre);

                // 6c) Llenar el adapter de resultados
                resultadosConexiones.clear();
                if (matches.isEmpty()) {
                    resultadosConexiones.add("No hay coincidencias.");
                } else {
                    for (Estudiante e : matches) {
                        String linea = "ID: " + e.getId()
                                + " → " + e.getNombre()
                                + " " + e.getApellido();
                        resultadosConexiones.add(linea);
                    }
                }
                adapterResultados.notifyDataSetChanged();
            });

            // 7) Botón Cancelar
            builder.setNegativeButton("Cancelar", (d, w) -> d.dismiss());
            builder.setCancelable(false);
            builder.show();
        });
    }

    /**
     * Recorre todos los estudiantes y devuelve los que practican
     * TODOS los deportes de la lista dada.
     */
    private ArrayList<Estudiante> filtrarEstudiantesPorDeportes(
            ArrayList<String> deportesSeleccionados) {
        ArrayList<Estudiante> resultado = new ArrayList<>();
        if (deportesSeleccionados.isEmpty()) return resultado;

        // Tomar la lista de un deporte como punto de partida
        String primero = deportesSeleccionados.get(0);
        ArrayList<Estudiante> base = miApp.getHashDeportes().get(primero);
        if (base == null) return resultado;

        // Verificar para cada estudiante de la base que tenga todos
        for (Estudiante est : base) {
            boolean cumpleTodos = true;
            for (String d : deportesSeleccionados) {
                ArrayList<Estudiante> listaDeporte =
                        miApp.getHashDeportes().get(d);
                if (listaDeporte == null || !listaDeporte.contains(est)) {
                    cumpleTodos = false;
                    break;
                }
            }
            if (cumpleTodos) {
                resultado.add(est);
            }
        }
        return resultado;
    }
}


