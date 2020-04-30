package com.odev.sinemaprojesi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.odev.sinemaprojesi.Models.Film;
import com.odev.sinemaprojesi.Models.MoviePlayer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.action_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.child_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.love_movies;
import static com.odev.sinemaprojesi.GetDataActivities.GetInfoFromDatabaseActivity.thriller_movies;



public class AddNewFilmActivity extends AppCompatActivity {

    Button image_upload_button;
    private static final int galleryPick = 1;
    ImageView new_film_image_view;
    private StorageReference filmImageRef;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button add_film_button;
    EditText film_name_et, film_director_et, film_writer_et, film_release_date_et;
    String downloadedUrl;
    String film_id = "";
    String name, director, image_id, writer, releaseDate;
    TextView title_add_or_edit_tv;
    String edit_or_add, empty_film;
    int screen_height, screen_width;
    Button action_film_button, love_film_button, thriller_film_button, child_film_button;
    Button buttons[];
    int button_selected;
    EditText cast_1_et, cast_2_et, cast_3_et, cast_4_et, cast_5_et, cast_6_et;
    EditText castcharacter_1_et, castcharacter_2_et, castcharacter_3_et, castcharacter_4_et,
            castcharacter_5_et, castcharacter_6_et;
    EditText castcharacter_ets[];
    EditText cast_ets[];
    String genre_str;
    int selected_genre_before;
    List<MoviePlayer> art_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_film);

        image_upload_button = findViewById(R.id.image_upload_button);
        new_film_image_view = findViewById(R.id.new_film_image_view);
        filmImageRef = FirebaseStorage.getInstance().getReference().child("Film Images");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        add_film_button = findViewById(R.id.add_film_button);
        film_name_et = findViewById(R.id.film_name_et);
        film_director_et = findViewById(R.id.film_director_et);
        film_writer_et = findViewById(R.id.film_writer_et);
        film_release_date_et = findViewById(R.id.film_release_date_et);

        title_add_or_edit_tv = findViewById(R.id.title_add_or_edit_tv);
        child_film_button = findViewById(R.id.child_film_button);
        love_film_button = findViewById(R.id.love_film_button);
        action_film_button = findViewById(R.id.action_film_button);
        thriller_film_button = findViewById(R.id.thriller_film_button);

        cast_1_et = findViewById(R.id.cast_1_Et);
        cast_2_et = findViewById(R.id.cast_2_Et);
        cast_3_et = findViewById(R.id.cast_3_Et);
        cast_4_et = findViewById(R.id.cast_4_Et);
        cast_5_et = findViewById(R.id.cast_5_Et);
        cast_6_et = findViewById(R.id.cast_6_Et);
        cast_ets = new EditText[]{cast_1_et, cast_2_et, cast_3_et, cast_4_et, cast_5_et, cast_6_et};

        castcharacter_1_et = findViewById(R.id.castcharacter_1_Et);
        castcharacter_2_et = findViewById(R.id.castcharacter_2_Et);
        castcharacter_3_et = findViewById(R.id.castcharacter_3_Et);
        castcharacter_4_et = findViewById(R.id.castcharacter_4_Et);
        castcharacter_5_et = findViewById(R.id.castcharacter_5_Et);
        castcharacter_6_et = findViewById(R.id.castcharacter_6_Et);
        castcharacter_ets = new EditText[]{castcharacter_1_et, castcharacter_2_et, castcharacter_3_et, castcharacter_4_et,
                castcharacter_5_et, castcharacter_6_et};

        empty_film = "https://firebasestorage.googleapis.com/v0/b/sinemaprojesi-8681b.appspot.com/o/Film%20Images%2Fempty_film.jpg?alt=media&token=ba9d832a-241b-422c-89b6-ef9f01dcf073";

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_height = displayMetrics.heightPixels;
        screen_width = displayMetrics.widthPixels;

        int button_width = screen_width / 4 - 8;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(button_width, (int)(button_width / 2.5));
        child_film_button.setLayoutParams(params);
        love_film_button.setLayoutParams(params);
        action_film_button.setLayoutParams(params);
        thriller_film_button.setLayoutParams(params);

        buttons = new Button[]{action_film_button, love_film_button, child_film_button, thriller_film_button};

        for(int i = 0; i < buttons.length; i++){
            final int finalI = i;
            final int finalI1 = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button_selected = finalI1;

                    buttons[finalI].setBackgroundColor(getResources().getColor(R.color.Orange));

                    for(int j = 0; j < buttons.length; j++){
                       if(j != finalI){
                           buttons[j].setBackgroundColor(getResources().getColor(R.color.Gray));
                       }
                    }
                }
            });
        }


        Bundle bundle = getIntent().getExtras();
        if(getIntent() != null && bundle != null && bundle.getString("edit_or_add") != null) {
            edit_or_add = bundle.getString("edit_or_add");

            if (edit_or_add.equals("1")) {
                title_add_or_edit_tv.setText("Film Düzenleme Ekranı");
                add_film_button.setText("Filmi Düzenle");

                name = bundle.getString("name");
                director = bundle.getString("director");
                image_id = bundle.getString("imageid");
                film_id = bundle.getString("film_id");
                writer = bundle.getString("writer");
                releaseDate = bundle.getString("release_date");
                genre_str = bundle.getString("genre");
                art_list = new ArrayList<>();

                int k = 0;
                while(bundle.getString("cast"+k+"0") != null){
                    String name = bundle.getString("cast"+k+"0");
                    String character = bundle.getString("cast"+k+"1");
                    MoviePlayer m = new MoviePlayer(name, character);
                    art_list.add(m);
                    k++;
                }


                if(genre_str.equals("Action Movies")){
                    buttons[0].setBackgroundColor(getResources().getColor(R.color.Orange));
                    button_selected = 0;  selected_genre_before = 0;
                }
                else if(genre_str.equals("Love Movies")){
                    buttons[1].setBackgroundColor(getResources().getColor(R.color.Orange));
                    button_selected = 1;  selected_genre_before = 1;
                }
                else if(genre_str.equals("Child Movies")){
                    buttons[2].setBackgroundColor(getResources().getColor(R.color.Orange));
                    button_selected = 2;  selected_genre_before = 2;
                }
                else{
                    buttons[3].setBackgroundColor(getResources().getColor(R.color.Orange));
                    button_selected = 3;  selected_genre_before = 3;
                }

                int p = 0;
                for(MoviePlayer mp : art_list){
                    Log.i("denebul",mp.toString());
                }

                for(MoviePlayer moviePlayer : art_list){
                    cast_ets[p].setText(moviePlayer.getName());
                    castcharacter_ets[p++].setText(moviePlayer.getCharacter());
                }

                film_release_date_et.setText(releaseDate);
                film_writer_et.setText(writer);
                film_name_et.setText(name);
                film_director_et.setText(director);
                Picasso.with(AddNewFilmActivity.this).load(image_id).into(new_film_image_view);

            } else {
                for(int i = 0; i < 10; i++){
                    int a = (int)(Math.random()*10);
                    film_id += String.valueOf(a);
                }

                Picasso.with(AddNewFilmActivity.this).load(empty_film).into(new_film_image_view);
            }
        }


        image_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPick);
            }
        });



        add_film_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!film_name_et.getText().toString().isEmpty() &&
                        !film_director_et.getText().toString().isEmpty() && !film_writer_et.getText().toString().isEmpty() &&
                    !film_release_date_et.getText().toString().isEmpty() && !cast_1_et.getText().toString().isEmpty()){
                        art_list.clear();
                        for(int i = 0; i < 6; i++){
                            if(!cast_ets[i].getText().toString().isEmpty() && castcharacter_ets[i].getText().toString().isEmpty()){
                                art_list.add(new MoviePlayer(cast_ets[i].getText().toString(), castcharacter_ets[i].getText().toString()));
                            }
                        }

                        String genre = "", genre_before = "";
                        if(button_selected == 0) genre = "Action Movies";
                        else if(button_selected == 1) genre = "Love Movies";
                        else if(button_selected == 2) genre = "Child Movies";
                        else genre = "Thriller Movies";

                        if(edit_or_add.equals("1")){
                            if(selected_genre_before == 0) genre_before = "Action Movies";
                            else if(selected_genre_before == 1) genre_before = "Love Movies";
                            else if(selected_genre_before == 2) genre_before = "Child Movies";
                            else genre_before = "Thriller Movies";

                            if(selected_genre_before != button_selected){
                                reference = database.getReference("Films/"+genre_before+"/"+film_id);
                                reference.removeValue();

                                if(selected_genre_before == 0)
                                    for(Film film : action_movies)
                                        if(film.getFilm_id().equals(film_id)){
                                            action_movies.remove(film);
                                            break;
                                        }
                                else if(selected_genre_before == 1)
                                            for(Film film2 : love_movies)
                                                if(film2.getFilm_id().equals(film_id)){
                                                    love_movies.remove(film2); break;
                                                }
                                else if(selected_genre_before == 2)
                                                    for(Film film3 : child_movies)
                                                        if(film3.getFilm_id().equals(film_id)){
                                                            child_movies.remove(film3); break;
                                                        }
                                else
                                    for(Film film4 : thriller_movies)
                                        if(film4.getFilm_id().equals(film_id)){
                                            thriller_movies.remove(film4); break;
                                        }
                            }
                        }


                        //create space
                        if(edit_or_add.equals("0")) reference = database.getReference("Films/"+genre+"/"+film_id);

                        reference = database.getReference("Films/"+genre+"/"+film_id+"/name");
                        reference.setValue(film_name_et.getText().toString());
                        reference = database.getReference("Films/"+genre+"/"+film_id+"/director");
                        reference.setValue(film_director_et.getText().toString());

                        reference = database.getReference("Films/"+genre+"/"+film_id+"/writer");
                        reference.setValue(film_writer_et.getText().toString());
                        reference = database.getReference("Films/"+genre+"/"+film_id+"/releaseDate");
                        reference.setValue(film_release_date_et.getText().toString());

                        int artist_id = 1;
                        for(MoviePlayer moviePlayer : art_list){
                            reference =  database.getReference("Films/"+genre+"/"+film_id+"/Cast/"+artist_id);
                            reference.setValue(moviePlayer);
                            artist_id++;
                        }

                        reference = database.getReference("Films/"+genre+"/"+film_id+"/image");

                        if(!edit_or_add.equals("1") && downloadedUrl == null) reference.setValue(empty_film);
                        else{
                            if(edit_or_add.equals("1")) reference.setValue(image_id);
                            else reference.setValue(downloadedUrl);
                        }


                        if(button_selected == 0) action_movies.clear();
                        else if(button_selected == 1) love_movies.clear();
                        else if(button_selected == 2) child_movies.clear();
                        else thriller_movies.clear();


                        Intent intent = new Intent(AddNewFilmActivity.this, MainActivity.class);

                        startActivity(intent);

                }
                else{
                    Toast.makeText(AddNewFilmActivity.this, "Tüm alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();

            final StorageReference filePath = filmImageRef.child(film_id + ".jpg");

            filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadedUrl = uri.toString();

                            Picasso.with(AddNewFilmActivity.this).load(downloadedUrl).into(new_film_image_view);
                        }
                    });

                    Toast.makeText(getApplicationContext(), "Resim başarıyla yüklendi!", Toast.LENGTH_SHORT).show();
                }
            });



        }


    }
}
