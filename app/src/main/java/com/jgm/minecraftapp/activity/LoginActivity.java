package com.jgm.minecraftapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private final int REG_CODE = 17;
    private final int LOGIN_CODE = 18;

    private Button btnLogin, btnRegister;
    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instancia de FB Auth
        fbAuth = FirebaseAuth.getInstance();

        //Enlazar con los elementos del Layout
        btnLogin = findViewById(R.id.loginLoginButton);
        btnRegister = findViewById(R.id.loginRegisterButton);
        email = findViewById(R.id.loginTextUsernameInput);
        password = findViewById(R.id.loginTextPasswordInput);

        //Login
        btnLogin.setOnClickListener(v -> {
            //Casting a string de email y password
            String ema = email.getText().toString();
            String pass = password.getText().toString();

            //Comprobar campos de usuario y contraseña
            if (ema.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.toast_bad_user_password, Toast.LENGTH_LONG).show();
                return;
            }

            //Comprobar usuario en FB
            fbAuth.signInWithEmailAndPassword(ema, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        //En caso de fallo se muestra un mensaje de error
                        Toast.makeText(getApplicationContext(), R.string.toast_login_error, Toast.LENGTH_LONG).show();
                        return;
                    } else {

                        //En caso de login correcto
                        String uid = fbAuth.getCurrentUser().getUid();


                        fbDb = FirebaseDatabase.getInstance();

                        DatabaseReference userRef = fbDb.getReference().child("usuarios/" + uid);

                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("userData", user);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, LOGIN_CODE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                Toast.makeText(getApplicationContext(), R.string.toast_login_error, Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                }
            });
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REG_CODE);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REG_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), R.string.toast_register_success, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.toast_register_cancel, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), R.string.toast_logout_success, Toast.LENGTH_LONG).show();
            }
        }
        email.setText("");
        password.setText("");
    }
}
