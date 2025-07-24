package com.example.estructuras;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

public class ConsultarDeporte extends AppCompatActivity {

    // Array estático de deportes (mismo orden y nombres que AgregarEstudiante)
    private static final String[] DEPORTES = {
            "Fútbol", "voleibol", "Baloncesto", "Ciclismo", "Rugby","Tenis","Ajedrez","Boxeo", "Natación","Atletismo"
    };

    private TextView vistaSelectDeporte;
    private ListView listaPersonas;
    private TextView numeroPracticantes;

    private MiAplication miApp;

    // Adapter y datos para la lista de practicantes
    private ArrayList<String> listaNombres;
    private ArrayAdapter<String> adapterPersonas;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miApp = (MiAplication) getApplication();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_deporte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sys = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return insets;
        });

        // Referencias a vistas
        vistaSelectDeporte = findViewById(R.id.agrSelectDeportes);
        listaPersonas      = findViewById(R.id.ListaConsPers);
        numeroPracticantes = findViewById(R.id.NumeroPracticantes);

        // Inicializar lista y adapter
        listaNombres   = new ArrayList<>();
        adapterPersonas = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaNombres
        );
        listaPersonas.setAdapter(adapterPersonas);

        // Al hacer click en el TextView para elegir deporte:
        vistaSelectDeporte.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona un deporte");

            // Usa el array estático DEPORTES
            builder.setItems(DEPORTES, (dialog, which) -> {
                String deporteElegido = DEPORTES[which];
                vistaSelectDeporte.setText(deporteElegido);

                // Obtiene la lista de estudiantes que practican ese deporte
                ArrayList<Estudiante> listaEst = miApp
                        .getHashDeportes()
                        .get(deporteElegido);

                listaNombres.clear();
                int count = 0;
                if (listaEst != null && !listaEst.isEmpty()) {
                    count = listaEst.size();
                    for (Estudiante e : listaEst) {
                        listaNombres.add(
                                "ID: " + e.getId()
                                        + " → " + e.getNombre()
                                        + " "  + e.getApellido()
                        );
                    }
                } else {
                    // Ningún practicante registrado
                    listaNombres.add("No hay practicantes.");
                }

                // Actualiza el número de practicantes (incluso si es 0)
                numeroPracticantes.setText(String.valueOf(count));
                adapterPersonas.notifyDataSetChanged();
            });

            builder.setCancelable(true);
            builder.show();
        });
    }
}
