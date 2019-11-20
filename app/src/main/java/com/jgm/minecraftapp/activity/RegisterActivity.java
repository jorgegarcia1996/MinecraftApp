package com.jgm.minecraftapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb;

    private EditText email, firstName, lastName, password, repeatPassword;
    private Spinner nacionality;

    private Button btnRegister, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        email = findViewById(R.id.registerTextUsernameInput);
        firstName = findViewById(R.id.registerTextNameInput);
        lastName = findViewById(R.id.registerTextLastNameInput);
        password = findViewById(R.id.registerTextPasswordInput);
        repeatPassword = findViewById(R.id.registerTextRepeatPasswordInput);
        nacionality = findViewById(R.id.registerSpinnerNacionality);
        btnRegister = findViewById(R.id.registerRegisterButton);
        btnCancel = findViewById(R.id.registerCancelButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ema = getField(email);
                String name = getField(firstName);
                String lName = getField(lastName);
                String pass = getField(password);
                String repPass = getField(repeatPassword);
                String nac = nacionality.getSelectedItem().toString();

                if (ema.isEmpty() || name.isEmpty() || lName.isEmpty() || pass.isEmpty() || repPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.toast_register_empty_field, Toast.LENGTH_LONG).show();
                    return;
                }

                if (!pass.equals(repPass)) {
                    Toast.makeText(getApplicationContext(), R.string.toast_register_bad_password, Toast.LENGTH_LONG).show();
                    return;
                }

                fbAuth = FirebaseAuth.getInstance();
                fbDb = FirebaseDatabase.getInstance();

                fbAuth.createUserWithEmailAndPassword(ema, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            String uid = fbAuth.getCurrentUser().getUid();
                            User user = new User(name, lName, ema, nac, "users/Default-Profile.png" );

                            DatabaseReference dbRef = fbDb.getReference("usuarios");
                            dbRef.child(uid).setValue(user);

                            fbAuth.signOut();
                            setResult(RESULT_OK);
                            finish();
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

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
