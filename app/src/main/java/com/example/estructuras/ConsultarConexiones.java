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
                        seleccionadosPorNombre, idOrigen);

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
    /**
     * Devuelve todos los estudiantes del mismo subgrafo de origenId
     * que PRACTIQUEN *todos* los deportes de la lista dada.
     */
    private ArrayList<Estudiante> filtrarEstudiantesPorDeportes(
            ArrayList<String> deportesSeleccionados, int idOrigen) {

        ArrayList<Estudiante> resultado = new ArrayList<>();
        if (deportesSeleccionados.isEmpty()) return resultado;

        // 1) Obtengo la componente conexa de IDs
        ArrayList<Integer> componente =
                miApp.getGrafoEstudiantes().obtenerComponente(idOrigen);

        Log.d("DebugFiltrar", "Componente de " + idOrigen + " → " + componente);

        // 2) Para cada ID en la componente, chequeo si cumple todos los deportes
        for (Integer id : componente) {
            // salto el mismo origen si no quieres incluirlo
            if (id == idOrigen) continue;

            Estudiante est = miApp.getHashEstudiantes().get(id);
            if (est == null) continue;

            // Obtengo la lista de practicados como ArrayList
            ArrayList<String> pract = est.getDeportesPracticados_ArrayList();
            // Si contiene todos los seleccionados, lo agrego
            if (pract.containsAll(deportesSeleccionados)) {
                resultado.add(est);
                Log.d("DebugFiltrar", "  → cumple: " + est.getId() + " " + pract);
            } else {
                Log.d("DebugFiltrar", "  no cumple: " + est.getId() + " " + pract);
            }
        }

        return resultado;
    }



}


