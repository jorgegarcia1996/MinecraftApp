package com.jgm.minecraftapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private final int DEL_ACC_CODE = 31;

    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private User user;

    private Button editButton, confirmButton, cancelButton, deleteAccount;

    private ImageView profileImage;
    private TextView firstNameText, lastNameText, nationalityText;
    private EditText firstNameEdit, lastNameEdit;
    private Spinner nationalitySpinner;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Instancias de firebase y las vistas
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        editButton = findViewById(R.id.profileEditButton);
        confirmButton = findViewById(R.id.profileConfirmChanges);
        cancelButton = findViewById(R.id.profileCancelChanges);
        deleteAccount = findViewById(R.id.profileDeleteAccount);

        profileImage = findViewById(R.id.profilePicture);
        nationalityText = findViewById(R.id.profileNationalityText);
        firstNameText = findViewById(R.id.profileNameText);
        lastNameText = findViewById(R.id.profileLastNameText);

        firstNameEdit = findViewById(R.id.profileNameEdit);
        lastNameEdit = findViewById(R.id.profileLastNameEdit);
        nationalitySpinner = findViewById(R.id.profileNationalitySpinner);

        //Action bar
        getSupportActionBar().setTitle(R.string.profile_activity_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon_round);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Cojer los datos del usuario
        bundle = getIntent().getExtras();
        user = (User) bundle.getSerializable("userData");
        String uid = auth.getCurrentUser().getUid();

        //Mostrar la imagen de perfil
        storage.getReference().child(user.getImage()).getDownloadUrl()
                .addOnSuccessListener(
                        uri -> Picasso.get().load(uri).into(profileImage))
                .addOnFailureListener(
                        e -> {
                            Picasso.get().load(R.drawable.profile).into(profileImage);
                            Toast.makeText(getApplicationContext(),R.string.profile_load_iamge_fail, Toast.LENGTH_SHORT).show();
                        });

        //Mostrar los datos del usuario
        updateFields();

        //Editar los campos
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEdit();
            }
        });

        //Cancelar los cambios
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEdit();
                updateFields();
                Toast.makeText(getApplicationContext(), R.string.profile_cancel_changes, Toast.LENGTH_SHORT).show();
            }
        });
        //Confirmar los cambios
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEdit();
                user.setNombre(firstNameEdit.getText().toString());
                user.setApellidos(lastNameEdit.getText().toString());
                user.setNacionalidad(nationalitySpinner.getSelectedItem().toString());

                database.getReference().child("usuarios").child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), R.string.profile_update_success, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), R.string.profile_update_fail, Toast.LENGTH_SHORT).show();
                    }
                });
                updateFields();
            }
        });
        //actualizar los datos del bundle
        bundle.putSerializable("userData", user);

        //Borrar cuenta
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crear el dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle(R.string.delete_acc_alert_title);
                builder.setMessage(R.string.delete_acc_alert_message);
                builder.setCancelable(false);
                builder.setNegativeButton(R.string.delete_acc_alert_neg_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.delete_acc_alert_pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAcc();
                    }
                });
                //Mostrar el dialog
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    //Actualizar los campos
    private void updateFields() {
        firstNameText.setText(user.getNombre());
        lastNameText.setText(user.getApellidos());
        nationalityText.setText(user.getNacionalidad());

        firstNameEdit.setText(user.getNombre());
        lastNameEdit.setText(user.getApellidos());
        nationalitySpinner.setSelection(getSpinnerIndex(nationalitySpinner, user.getNacionalidad()));
    }

    //Habilitar la edición del usuario
    private void enableEdit() {
        editButton.setVisibility(View.GONE);
        deleteAccount.setVisibility(View.GONE);
        confirmButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        firstNameText.setVisibility(View.GONE);
        firstNameEdit.setVisibility(View.VISIBLE);
        firstNameEdit.setEnabled(true);
        lastNameText.setVisibility(View.GONE);
        lastNameEdit.setVisibility(View.VISIBLE);
        lastNameEdit.setEnabled(true);
        nationalityText.setVisibility(View.GONE);
        nationalitySpinner.setVisibility(View.VISIBLE);
    }

    //Deshabilitar la edición del usuario
    private void disableEdit() {
        editButton.setVisibility(View.VISIBLE);
        deleteAccount.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        firstNameText.setVisibility(View.VISIBLE);
        firstNameEdit.setVisibility(View.GONE);
        firstNameEdit.setEnabled(false);
        lastNameText.setVisibility(View.VISIBLE);
        lastNameEdit.setVisibility(View.GONE);
        lastNameEdit.setEnabled(false);
        nationalityText.setVisibility(View.VISIBLE);
        nationalitySpinner.setVisibility(View.GONE);
    }

    //recoger el dato del spinner
    private int getSpinnerIndex(Spinner spinner, String nac) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(nac)) {
                return i;
            }
        }
        return 0;
    }

    //Volver al la actividad principal
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        return true;
    }

    //Borrar cuenta
    public void deleteAcc() {
        String uid = auth.getCurrentUser().getUid();
        auth.getCurrentUser().delete();
        database.getReference("usuarios/").child(uid).removeValue();
        Toast.makeText(getApplicationContext(), R.string.account_deleted, Toast.LENGTH_LONG).show();
        setResult(DEL_ACC_CODE);
        finish();
        return;
    }
}
