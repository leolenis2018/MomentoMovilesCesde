package com.momento2cesde.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.UserModel;

public class ListUsersActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvFirestoreUsersList;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        rvFirestoreUsersList = findViewById(R.id.rvFirestoreUsersList);
        //loadPrefences();


        // Creamos el query a la base de datos
        Query query = db.collection("usersApartments");

        // Configurar opciones del adaptador. Este le pasa todos los elementos al recyclerView
        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<UserModel, UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserModel model) {
                //Este onBide asigna los datos a cada elemto
                //setText solo acepta datos tipo String
                holder.tvName.setText(model.getName());
                holder.tvIdentification.setText(model.getIdentification());
                holder.tvCountry.setText(model.getCountry());
                String id = getSnapshots().getSnapshot(position).getId();
                holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListUsersActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                        deleteUser(id);

                    }
                });
                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListUsersActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ListUsersActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });*/
            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                //LLenamos e recycler con datos que vienen de la base de datos
                // Al inflate le paso el nombre de la interfz grafica
                //Crea un elemto grafico pr cada item
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,
                        false);
                return new UsersViewHolder(view);
            }
        };

        //Esto es para ponerle un tamaño fijo cuando lo va a mostrar
        rvFirestoreUsersList.setHasFixedSize(true);
        rvFirestoreUsersList.setLayoutManager(new LinearLayoutManager(this));
        rvFirestoreUsersList.setAdapter(adapter);


    }

    //private void loadPrefences() {
    // }

    //Este me permite poder acceder a cada elemento de la lista y poder agragrle eventos a cada una

    private class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdentification, tvName, tvCountry;
        Button btnEditUser, btnDeleteUser;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            //ubicar los elementos en la interfaz.
            tvIdentification = itemView.findViewById(R.id.tvIdentification);
            tvName = itemView.findViewById(R.id.tvName);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
            btnEditUser = itemView.findViewById(R.id.btnEditUser);
        }
    }

    // Metodos para ejecutar cuando se inicie la aplicacion
    //onStar me ejecuta lo que hay dentro de este metodo

    //CODIGO PARA ELIMIAR REGISTRO POR ID

    public void deleteUser(String id) {
        db.collection("usersApartments").document((id))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ListUsersActivity.this, "Documento Eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListUsersActivity.this, " Error", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Despues de que ejecuta el ONcreate vay ejecuta el oNstar, para que empiece a escuhar
        adapter.startListening();
    }

    // este es para que detenga la base de datos cuando cierre la app
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}