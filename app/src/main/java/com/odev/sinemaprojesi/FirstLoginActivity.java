package com.odev.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.google.firebase.auth.FirebaseUser;
import com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity;

import static com.odev.sinemaprojesi.LauncherActivity.state;


public class FirstLoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText email_et, password_et;
    CardView login_cv, register_cv;
    Button customer_login_button, admin_login_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        login_cv = findViewById(R.id.login_cv);
        register_cv = findViewById(R.id.register_cv);
        mAuth = FirebaseAuth.getInstance();
        customer_login_button = findViewById(R.id.customer_login_button);
        admin_login_button = findViewById(R.id.admin_login_button);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(FirstLoginActivity.this, GetInfoFromDatabaseActivity.class);

            startActivity(intent);

            finish();
        }

//todo BURADA sqlite'dan giriş bilgisi alınacak, login değişkeni.

     /*   if(login == 1){
            Intent intent = new Intent(FirstLoginActivity.this, GetInfoFromDatabaseActivity.class);
            startActivity(intent);
        } */

        customer_login_button.setBackgroundColor(getResources().getColor(R.color.Orange));
        customer_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 0;
                customer_login_button.setBackgroundColor(getResources().getColor(R.color.Orange));
                admin_login_button.setBackgroundColor(getResources().getColor(R.color.Gray));
                register_cv.setVisibility(View.VISIBLE);
            }
        });
        admin_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 1;
                register_cv.setVisibility(View.INVISIBLE);
                admin_login_button.setBackgroundColor(getResources().getColor(R.color.Orange));
                customer_login_button.setBackgroundColor(getResources().getColor(R.color.Gray));
            }
        });


        register_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLoginActivity.this, SignUpActivity.class);

                startActivity(intent);
            }
        });

        login_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(FirstLoginActivity.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show();
                } else{

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(FirstLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FirstLoginActivity.this, "Başarıyla giriş yaptınız!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(FirstLoginActivity.this, GetInfoFromDatabaseActivity.class);
                                startActivity(intent);
                                finish();
                            } else{
                                Toast.makeText(FirstLoginActivity.this, "Giriş başarısız!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });




    }

}
