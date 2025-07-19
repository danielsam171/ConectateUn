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

public class EliminarEstudiante extends AppCompatActivity {

    EditText campocedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            campocedula.setError("El correo es obligatorio");
            return; // Detiene la ejecución si el campo está vacío
        }
        long cedula = Long.parseLong(strcedula);

        Intent intent = new Intent(this, MenuPrincipal.class);
        Toast.makeText(getApplicationContext(), "El estudiante se elimino",
                Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}