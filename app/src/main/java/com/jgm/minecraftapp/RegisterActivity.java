package com.jgm.minecraftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

        fbAuth = FirebaseAuth.getInstance();

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

                String ema = email.getText().toString();
                String name = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String pass = password.getText().toString();
                String repPass = repeatPassword.getText().toString();
                String nac = nacionality.getSelectedItem().toString();

                if (ema.isEmpty() || name.isEmpty() || lName.isEmpty() || pass.isEmpty() || repPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.toast_register_empty_field, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });



    }
}
