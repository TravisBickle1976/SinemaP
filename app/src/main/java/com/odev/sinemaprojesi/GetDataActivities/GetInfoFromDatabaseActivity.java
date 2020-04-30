package com.odev.sinemaprojesi.GetDataActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.odev.sinemaprojesi.MainActivity;
import com.odev.sinemaprojesi.Models.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odev.sinemaprojesi.R;

import java.util.ArrayList;
import java.util.List;

import static com.odev.sinemaprojesi.LauncherActivity.state;

public class GetInfoFromDatabaseActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    public static List<Film> action_movies, love_movies, child_movies, thriller_movies;
    public static int seat_integers[] = new int[200];
    FirebaseUser user;
    FirebaseAuth auth;
    public final static List<Film> all_films_list = new ArrayList<>();
    int list_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info_from_database);

        Toast.makeText(getApplicationContext(),"Filmler yükleniyor... Lütfen bekleyiniz.", Toast.LENGTH_LONG).show();

        database = FirebaseDatabase.getInstance();
        list_id = 0;
        user = FirebaseAuth.getInstance().getCurrentUser();
        action_movies = new ArrayList<>();
        love_movies = new ArrayList<>();
        child_movies = new ArrayList<>();
        thriller_movies = new ArrayList<>();
        auth = FirebaseAuth.getInstance();


        reference = database.getReference("Users/"+auth.getUid()+"/state");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null && dataSnapshot.getValue() != null)
                    state = Integer.valueOf(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        getMovieGenresInfoFromDb("Action Movies", action_movies);
        getMovieGenresInfoFromDb("Love Movies", love_movies);
        getMovieGenresInfoFromDb("Child Movies", child_movies);
        getMovieGenresInfoFromDb("Thriller Movies", thriller_movies);



    }

    public void getMovieGenresInfoFromDb(final String genre, final List<Film> list){
        reference = database.getReference("Films/"+genre);
        list_id++;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if(childDataSnapshot.child("film_name").getValue() != null && childDataSnapshot.child("director_name").getValue() != null
                            && childDataSnapshot.child("filmImage_id").getValue() != null){

                        Film film = childDataSnapshot.getValue(Film.class);
                        all_films_list.add(film);

                        //Film film = new Film(film_id, image, name, director,writer,releaseDate, artists_list, genre, price);

                        int search = 0;
                        for(Film a_film : list){
                            if(a_film.film_name.equals(film.getFilm_name()) && a_film.director_name.equals(film.getDirector_name())){
                                search = 1;
                                break;
                            }
                        }

                        if(search == 0) list.add(film);
                    }

                }


                if(list_id == 4){
                    Intent intent = new Intent(GetInfoFromDatabaseActivity.this, MainActivity.class);

                    startActivity(intent);

                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }






}








