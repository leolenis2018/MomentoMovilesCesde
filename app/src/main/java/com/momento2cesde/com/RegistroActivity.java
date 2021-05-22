package com.momento2cesde.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    EditText etName, etIdentification, etCountry, etCity, etPassword, etAnfitrion, etEmail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etName = findViewById(R.id.etName);
        etIdentification = findViewById(R.id.etIdentification);
        etCountry = findViewById(R.id.etCountry);
        etCity = findViewById(R.id.etCity);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etAnfitrion = findViewById(R.id.etAnfitrion);


    }

    //Validacion de los campos
    /*public boolean validarDatos(){
        
        boolean retorno = true;
        String name = etName.getText().toString();
        String identification = etIdentification.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();

        if(name.isEmpty())
        {
            etName.setError("Campo Obligatorio");
            retorno= false;
        }
        if (identification.isEmpty())
        {
            etIdentification.setError("Campo Obligatorio");
            retorno= false;
        }
        if (password.isEmpty())
        {
            etPassword.setError("Campo Obligatorio");
            retorno= false;
        }
        if (email.isEmpty())
        {
            etEmail.setError("Campo Obligatorio");
            retorno= false;
        }
        return retorno;
    }
*/
    //Autentificacion de Usuarios (Registro)
    public void registerUser(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistroActivity.this, "Usuario Registrado",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroActivity.this, "No se registró",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    //Autentificacion de Usuarios (Verificar)
    public void singIn(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegistroActivity.this, "Inicio de sesion correcto",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroActivity.this, "Error de Usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Conexion a BD de los datos del Usuario
    public void saveUser(View view) {
        Map<String, Object> user = new HashMap<>();
        String name = etName.getText().toString();
        String identification = etIdentification.getText().toString();
        String country = etCountry.getText().toString();
        String city = etCity.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String anfitrion = etAnfitrion.getText().toString();
        user.put("name", name);
        user.put("identification", identification);
        user.put("country", country);
        user.put("city", city);
        user.put("password", password);
        user.put("email", email);
        user.put("anfitrion", anfitrion);

       /* if(validarDatos()){
            Toast.makeText(this, "Ingresaste datos", Toast.LENGTH_SHORT).show();
        }*/

        //CODIGO PARA REGISTRAR CON ID AUTOMATICO
        /*db.collection("usersApartments")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        Log.w("firebase", "Error adding document", e);
                    }
                });*/

        //CODIGO PARA REGISTRAR CON ID QUE YO DESEE
        db.collection("usersApartments").document(identification)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegistroActivity.this, "Registro Correcto",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "No se registró",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void VerTodos(View view) {
        Intent intent = new Intent(this, ListUsersActivity.class);
        startActivity(intent);
    }

}