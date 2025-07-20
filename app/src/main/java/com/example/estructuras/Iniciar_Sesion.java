package com.example.estructuras;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Iniciar_Sesion extends AppCompatActivity {

    private EditText campo_usuario;
    private EditText campo_clave;
    private FirebaseAuth mAuth;

    private static final String TAG = "RegistroActividad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        campo_usuario =  findViewById(R.id.iniciar_sesionUsuario);
        campo_clave =  findViewById(R.id.iniciarSesionClave);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // El usuario ha iniciado sesión correctamente.
            // Navega a la actividad principal de tu app.
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            // Estas flags limpian el historial para que el usuario no pueda
            // volver a la pantalla de login con el botón de "atrás".
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish(); // Cierra la actividad de login
        } else {
            // El inicio de sesión falló.
            // El Toast del código de ejemplo ya informa al usuario.
            // Aquí podrías, por ejemplo, limpiar el campo de contraseña.
            Log.w(TAG, "updateUI: User is null, sign in failed.");
        }
    }
    public void iniciarSesion(View view){
        String correo = campo_usuario.getText().toString().trim();
        String clave = campo_clave.getText().toString().trim();

        if (correo.isEmpty()) {
            campo_usuario.setError("El correo es obligatorio");
            return; // Detiene la ejecución si el campo está vacío
        }

        if (clave.isEmpty()) {
            campo_clave.setError("La contraseña es obligatoria");
            return;
        }
        mAuth.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(getApplicationContext(),MenuPrincipal.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}