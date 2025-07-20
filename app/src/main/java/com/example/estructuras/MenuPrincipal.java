package com.example.estructuras;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void irInfoEstudiante(View view){
        Intent intent = new Intent(this,InfoEstudiante.class);
        startActivity(intent);
    }
    public void irAgrEstudiante(View view){
        Intent intent = new Intent(this,AgregarEstudiante.class);
        startActivity(intent);
    }
    public void irEliEstudiante(View view){
        Intent intent = new Intent(this,EliminarEstudiante.class);
        startActivity(intent);
    }
    public void irConsultarConexiones(View view){
        Intent intent = new Intent(this,ConsultarConexiones.class);
        startActivity(intent);
    }
    public void irConsultarDeporte(View view){
        Intent intent = new Intent(this,ConsultarDeporte.class);
        startActivity(intent);
    }
}