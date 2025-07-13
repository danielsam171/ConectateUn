package com.example.estructuras;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class Registro extends AppCompatActivity {

    private EditText correo_campo;
    private EditText clave_campo;
    private EditText conficlave_campo;

    private static final String TAG = "RegistroActividad";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        correo_campo = findViewById(R.id.registro_Correo);
        clave_campo = findViewById(R.id.registro_Clave);
        conficlave_campo = findViewById(R.id.registroconficlave);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void registrarUsuario(View view){

        String correo = correo_campo.getText().toString().trim();
        String clave = clave_campo.getText().toString().trim();
        String confirmacionClave = conficlave_campo.getText().toString().trim();

        // 2. Validaciones básicas antes de llamar a Firebase
        if (correo.isEmpty()) {
            correo_campo.setError("El correo es obligatorio");
            return; // Detiene la ejecución si el campo está vacío
        }

        if (clave.isEmpty()) {
            clave_campo.setError("La contraseña es obligatoria");
            return;
        }

        if (clave.length() < 6) {
            clave_campo.setError("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!clave.equals(confirmacionClave)) {
            conficlave_campo.setError("Las contraseñas no coinciden");
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El registro fue exitoso
                            Log.d(TAG, "UsuarioCreado:success");
                            Toast.makeText(Registro.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            // Si el registro falla, muestra un mensaje al usuario.
                            Log.w(TAG, "UsuarioCreado:failure", task.getException());

                            // Muestra un error más específico si es posible
                            Toast.makeText(Registro.this, "Falló el registro: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}