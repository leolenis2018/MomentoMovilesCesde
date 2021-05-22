package com.momento2cesde.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. Aqui pongo los EditTex o Text View con sus Id que vaa llenar e usuario
        // Ejemplo:  etName = findViewById(R.id.etName);


        // Create a new user with a first and last name
        /*Map<String, Object> user = new HashMap<>();
        user.put("first", "Leo");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding document", e);
                    }
                });*/
    }

    public void RegistroActivity(View view) {
        Intent intent = new Intent(this, RegistroActivity.class);
        //Intent intent = new Intent(this, ListUsersActivity.class);
        startActivity(intent);
    }

    /*public void saverPreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        String userState = "login";
        SharedPreferences.Editor editor = preferences.edit();// Habilita la edicion.
        editor.putString("state", "userState"); //agrega los datos
        editor.commit(); // Guarda los cambios
    }*/

    /*public String loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userState = preferences.getString("state", "error");
        //Toast.makeText(this, userState, Toast.LENGTH_SHORT).show();
        return();

        if (userState.equals("login")){
             Intent intent = new Intent();
        }
    }*/

    //CODIGO PARA ELIMIAR REGISTRO POR ID

    /*public void deleteUser(View view){
        db.collection("usersApartments").document("741")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                   Toast.makeText(MainActivity.this, "Documento Eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, " Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    // Metodo para mostrar y ocultar el menu

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_about);
        item.setTitle("Hola Admin");
        return super.onPrepareOptionsMenu(menu);
    }

    //Metodo para asignar las funciones a cada item
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_logout) {
            Toast.makeText(this, "Cerrando Sesion", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item_about) {
            Toast.makeText(this, "Realizado por Leonardo", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}