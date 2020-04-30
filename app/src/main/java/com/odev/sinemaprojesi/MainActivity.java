package com.odev.sinemaprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.odev.sinemaprojesi.Adapters.FilmsAdapter;
import com.odev.sinemaprojesi.GetDataActivities.GetBudgetInfoFromDatabase;
import com.odev.sinemaprojesi.GetDataActivities.GetEmployeeInfoFromDatabase;
import com.odev.sinemaprojesi.Models.Film;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.odev.sinemaprojesi.Models.Ticket;

import java.util.List;

import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.all_films_list;
import static com.odev.sinemaprojesi.LauncherActivity.state;
import  static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.action_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.child_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.love_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.thriller_movies;

public class MainActivity extends AppCompatActivity {

    LinearLayout filmGenresLayout;
    ScrollView films_scroll;
    RecyclerView actionMoviesRecView, kidsMoviesRecView, loveMoviesRecView;
    Button add_new_film_button;
    Button budget_button;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference reference;
    FirebaseDatabase database;
    LinearLayout admin_panel_layout;
    RecyclerView thrillerMoviesRecView;
    public static String film_selected;
    Button employees_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();





        //check();

        //1 Aylık Random Bilgiyi Üretecek Kod



/*
        for(int i = 1; i <= 31; i++){
            String date = i+" Mayıs 2020";

            for(int j = 1; j <= 5; j++){ //for all sessions
                for(int k = 1; k <= 3; k++){ //for all saloons
                    int random_int = (int)(Math.random()*all_films_list.size());
                    String film_name = all_films_list.get(random_int).getFilm_name();
                    int seat_states[] = new int[100];
                    int price = all_films_list.get(random_int).getPrice();

                    for(int l = 0; l < 100; l++){
                        int random_number = (int) (Math.random()*2);

                        seat_states[l] = random_number;

                        if(seat_states[l] == 1){
                            int row = l / 10;
                            int column = l % 10;

                            Ticket ticket = new Ticket(date, film_name, j, k, row, column, price, -1);
                            String ticket_id = "";
                            for(int t = 0; t < 8; t++){
                                ticket_id += ""+(int)(Math.random()*10);
                            }

                            reference = database.getReference("Tickets/"+date+"/"+j+"/"+k+"/"+ticket_id);
                            reference.setValue(ticket);
                        }

                    }

                }
            }

        }
*/

        define();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m1 = getMenuInflater();
        m1.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.logout_button);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, FirstLoginActivity.class);
                startActivity(intent);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void define(){
        FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        /*
        database = FirebaseDatabase.getInstance();
        Log.i("bakım", "auth uid : "+auth.getUid());
        reference = database.getReference(auth.getUid() + "/state");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null){
                    state = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.i("bakım","state2: "+state);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("bakım","Bilgi alınamadı!");
            }
        });
        Log.i("bakım","state: "+state); */

        admin_panel_layout = findViewById(R.id.admin_panel_layout);
        add_new_film_button = findViewById(R.id.add_new_film_button);
        budget_button = findViewById(R.id.budget_button);
        employees_button = findViewById(R.id.employees_button);

        if(state == 0) admin_panel_layout.setVisibility(View.GONE);
        //TODO if(state == 0)

        add_new_film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewFilmActivity.class);
                intent.putExtra("edit_or_add", "0");

                startActivity(intent);
            }
        });

        budget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetBudgetInfoFromDatabase.class);

                startActivity(intent);
            }
        });

        employees_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetEmployeeInfoFromDatabase.class);

                startActivity(intent);
            }
        });



        filmGenresLayout = findViewById(R.id.filmGenresLayout);
        actionMoviesRecView = findViewById(R.id.actionMoviesRecView);
        kidsMoviesRecView = findViewById(R.id.kidsMoviesRecView);
        loveMoviesRecView = findViewById(R.id.loveMoviesRecView);
        thrillerMoviesRecView = findViewById(R.id.thrillerMoviesRecView);
        films_scroll = findViewById(R.id.films_scroll);

        setAdapterToFilms(action_movies, actionMoviesRecView);
        setAdapterToFilms(love_movies, loveMoviesRecView);
        setAdapterToFilms(child_movies, kidsMoviesRecView);
        setAdapterToFilms(thriller_movies, thrillerMoviesRecView);


    }

    public void setAdapterToFilms(List<Film> list, RecyclerView recyclerView){
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager1);
        FilmsAdapter f1_adapter = new FilmsAdapter(list, getApplicationContext(), this);
        recyclerView.setAdapter(f1_adapter);
    }




}
