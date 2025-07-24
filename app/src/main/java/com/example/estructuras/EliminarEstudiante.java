package com.example.estructuras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EliminarEstudiante extends AppCompatActivity {

    private MiAplication miApp;
    EditText campocedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miApp = (MiAplication) getApplication();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar_estudiante);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        campocedula = findViewById(R.id.EliCedula);
    }
    public void eliminarEstudiante(View view){
        String strcedula = campocedula.getText().toString().trim();
        if (strcedula.isEmpty()) {
            campocedula.setError("El ID es obligatorio");
            return;
        }
        int cedula;
        try {
            cedula = Integer.parseInt(strcedula);
        } catch (NumberFormatException e) {
            campocedula.setError("El ID debe ser un número válido");
            return;
        }

        Estudiante estudiante = miApp.getHashEstudiantes().get(cedula);
        if(estudiante == null){
            campocedula.setError("ID no encontrado");
            Toast.makeText(this,
                    "No existe un usuario registrado con este ID",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // 1) Quitarlo de cada lista de hashDeportes
        String[] deportesPracticados = estudiante.getDeportesPracticados();
        if (deportesPracticados != null) {
            for (String deporte : deportesPracticados) {
                if (deporte != null && !deporte.isEmpty()) {
                    ArrayList<Estudiante> lista = miApp.getHashDeportes().get(deporte);
                    if (lista != null) {
                        lista.remove(estudiante);
                    }
                }
            }
        }

        // 2) Quitarlo del hash de estudiantes
        miApp.getHashEstudiantes().remove(cedula);

        // 3) **Eliminarlo del grafo**
        miApp.getGrafoEstudiantes().eliminarNodo(cedula);

        // 4) Volver al menú principal
        Toast.makeText(this,
                "El estudiante se eliminó correctamente",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}