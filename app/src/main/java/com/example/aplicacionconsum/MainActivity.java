package com.example.aplicacionconsum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etContrasena;
    private Button btnIniciar;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.emailEtLogin);
        etContrasena = findViewById(R.id.contrasenaEtLogin);
        btnIniciar = findViewById(R.id.iniciarBtnLogin);
        progressBar = findViewById(R.id.progressBarMain);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etEmail.getText().toString().isEmpty() && !etContrasena.toString().isEmpty())
                    signIn(etEmail.getText().toString(), etContrasena.getText().toString());
                else
                    Toast.makeText(MainActivity.this, "Introduce los datos", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null)
            startActivity(new Intent(this, ProductosActivity.class));
    }

    private void createAccount(String email, String contrasena){
        mAuth.createUserWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn(String email, String contrasena){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Autentificación fallida",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
        progressBar.setVisibility(View.GONE);
    }

    public void clickRegistrarse(View view){
        view = LayoutInflater.from(this).inflate(R.layout.registrarse_layout, null);
        final EditText etEmailRegistrarse = view.findViewById(R.id.emailEtRegistrarse);
        final EditText etContrasenaRegistrarse = view.findViewById(R.id.contrasenaEtRegistrarse);
        final EditText etRepetirContrasenaRegistrarse = view.findViewById(R.id.repetirContrasenaEtRegistrarse);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = etEmailRegistrarse.getText().toString();
                String contrasena = etContrasenaRegistrarse.getText().toString();
                String repetir_contrasena = etRepetirContrasenaRegistrarse.getText().toString();

                if(contrasena.equalsIgnoreCase(repetir_contrasena)){
                    createAccount(email, contrasena);
                }
                else{
                    Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.create().show();
    }

}
