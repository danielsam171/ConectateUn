package com.example.estructuras;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConsultarDeporte extends AppCompatActivity {

    private MiAplication miApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        miApp = (MiAplication) getApplication();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_deporte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    /* public consultardeporte(String deporte){
        miapp.getgrafo.consultardeporte(deporte);

    } */

}