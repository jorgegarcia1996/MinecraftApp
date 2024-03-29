package com.jgm.minecraftapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb;
    private FirebaseStorage storage;
    private StorageReference defaultProfile;

    private EditText email, firstName, lastName, password, repeatPassword;
    private Spinner nationality;

    private Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Ocultar la barra de acción
        getSupportActionBar().hide();

        //Instanciar las vistar
        email = findViewById(R.id.registerTextUsernameInput);
        firstName = findViewById(R.id.registerTextNameInput);
        lastName = findViewById(R.id.registerTextLastNameInput);
        password = findViewById(R.id.registerTextPasswordInput);
        repeatPassword = findViewById(R.id.registerTextRepeatPasswordInput);
        nationality = findViewById(R.id.registerSpinnerNationality);
        btnRegister = findViewById(R.id.registerRegisterButton);
        btnCancel = findViewById(R.id.registerCancelButton);

        //Boton de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Recoger el valor de los campos
                String ema = getField(email);
                String name = getField(firstName);
                String lName = getField(lastName);
                String pass = getField(password);
                String repPass = getField(repeatPassword);
                String nac = nationality.getSelectedItem().toString();

                //Comprobar que todos los campos tengan algún valor
                if (ema.isEmpty() || name.isEmpty() || lName.isEmpty() || pass.isEmpty() || repPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.toast_register_empty_field, Toast.LENGTH_LONG).show();
                    return;
                }

                //Comprobar la contraseña
                if (!pass.equals(repPass)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_register_bad_password, Toast.LENGTH_LONG).show();
                    return;
                }

                //Instancias de Firebase
                fbAuth = FirebaseAuth.getInstance();
                fbDb = FirebaseDatabase.getInstance();
                storage = FirebaseStorage.getInstance();

                //Proceso de registro
                fbAuth.createUserWithEmailAndPassword(ema, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Registro satisfactorio
                            String uid = fbAuth.getCurrentUser().getUid();
                            String profile = storage.getReference().child("users").child("profile.png").getPath();

                            User user = new User(name, lName, ema, nac, profile);

                            DatabaseReference dbRef = fbDb.getReference("usuarios");
                            dbRef.child(uid).setValue(user);

                            fbAuth.signOut();
                            setResult(RESULT_OK);
                            finish();
                            return;
                        } else {
                            //Fallo en el registro
                            Toast.makeText(getApplicationContext(), "Fallo al crear el usuario", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Cancelar el registro
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                return;
            }
        });

    }

    private String getField(EditText editText) {
        return editText.getText().toString().trim();
    }
}
