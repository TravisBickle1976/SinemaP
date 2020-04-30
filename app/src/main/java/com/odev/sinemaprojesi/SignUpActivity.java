package com.odev.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText name_et, email_et2, password_et2;
    CardView signup_cv;
    FirebaseAuth mFirebaseAuth;
    CardView login_cv2;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name_et = findViewById(R.id.name_et);
        email_et2 = findViewById(R.id.email_et2);
        password_et2 = findViewById(R.id.password_et2);
        signup_cv = findViewById(R.id.signup_cv);
        login_cv2 = findViewById(R.id.login_cv_back);

        mFirebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signup_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = name_et.getText().toString();
                final String email = email_et2.getText().toString();
                String password = password_et2.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                } else{

                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                ref = database.getReference("Users/"+mFirebaseAuth.getUid()+"/name");
                                ref.setValue(name);
                                ref = database.getReference("Users/"+mFirebaseAuth.getUid()+"/state");
                                ref.setValue(0);

                                Intent intent = new Intent(SignUpActivity.this, GetInfoFromDatabaseActivity.class);

                                startActivity(intent);
                            } else{
                                Toast.makeText(SignUpActivity.this, "Kayıt başarısız!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

        login_cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, FirstLoginActivity.class);

                startActivity(intent);
            }
        });



    }
}






